package com.raymond;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.raymond.binding.RayMapperMethod;
import com.raymond.configuration.RayBatisConfiguration;
import com.raymond.converter.ResultConverter;
import com.raymond.mapping.RayMappedStatement;
import com.raymond.mapping.RayResultMap;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import sun.reflect.misc.ReflectUtil;

@Data
@Slf4j
public class RayBatisSession {
    private RayBatisConfiguration configuration;

    public RayBatisSession(RayBatisConfiguration configuration) {
        this.configuration = configuration;
    }

    <T> T getMapper(Class<T> clazz) throws Exception {
        // 创建并返回动态代理对象
        if (null == configuration) {
            log.error("未找到该Mapper的配置信息");
            throw new Exception("未找到该Mapper的配置信息");
        }
        return (T) Proxy.newProxyInstance(
                clazz.getClassLoader(),
                new Class<?>[]{clazz},
                new ServiceInvocationHandler(configuration)
        );
    }

    class ServiceInvocationHandler implements InvocationHandler {
        private RayBatisConfiguration configuration;

        public ServiceInvocationHandler(RayBatisConfiguration configuration) {
            this.configuration = configuration;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            // 在方法调用之前执行的逻辑
            log.info("ServiceInvocationHandler执行方法拦截，method:{}", method.getName());

            String methodKey = method.getDeclaringClass().getCanonicalName() + ":" + method.getName();
            RayMappedStatement statement = configuration.getStatementMap().get(methodKey);
            log.info("mapperMethod:{}",statement);

            return getResult(configuration, statement);
        }
    }

    private Object getResult(RayBatisConfiguration configuration, RayMappedStatement mappedStatement) {
        String sql = mappedStatement.getBoundSQL().getSql();

        log.info("getJdbcResult执行sql:{}", sql);
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // 1. 加载数据库驱动（对于现代JDBC驱动，可以省略此步骤）
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2. 建立数据库连接
            connection = configuration.getEnvironment().getDataSource().getConnection();

            // 3. 创建Statement对象
            statement = connection.createStatement();

            // 4. 执行SQL查询
            resultSet = statement.executeQuery(sql);

            return convert(mappedStatement,resultSet);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            // 6. 关闭资源
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    private Object convert(RayMappedStatement mappedStatement, ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }
        if (mappedStatement.getResultMap() != null) {
            log.info("暂不支持非resultMap配置");
            return null;
        }

        RayResultMap resultMap = configuration.getResultMaps().get(mappedStatement.getId());
        if (resultMap.getType() == null) {
            log.error("未找到该resultMap的配置信息");
            throw new RuntimeException("未找到该resultMap的配置信息");
        }

        try {
            Class<?> retClass = resultMap.getType();
            ReflectUtil.newInstance(retClass);
            Object ret = retClass.getConstructor().newInstance();
            for (Field item : retClass.getDeclaredFields()) {
                item.setAccessible(true);
                String fieldName = item.getName();
                String columnName = null;
                Object value = resultSet.getObject(columnName);
                item.set(ret, value);
            }

            return ret;
        } catch (Exception e) {
            log.error("convert error", e);
        }
        return null;
    }

    private Object getJdbcResult(RayBatisConfiguration configuration, RayMapperMethod method,
                                 ResultConverter resultConverter) throws Exception {

        String sql = method.getSql();

        log.info("getJdbcResult执行sql:{}", sql);
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // 1. 加载数据库驱动（对于现代JDBC驱动，可以省略此步骤）
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2. 建立数据库连接
            connection = configuration.getEnvironment().getDataSource().getConnection();

            // 3. 创建Statement对象
            statement = connection.createStatement();

            // 4. 执行SQL查询
            resultSet = statement.executeQuery(sql);

            return resultConverter.convert(resultSet);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            // 6. 关闭资源
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

}
