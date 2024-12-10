package com.raymond;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.raymond.configuration.ConfigurationManager;
import com.raymond.configuration.RayBatisConfiguration;
import com.raymond.converter.ResultConverter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class RayBatisSession {
    private ConfigurationManager configurationManager = new ConfigurationManager();

    <T> T getMapper(Class<T> clazz) throws Exception {
        // 创建并返回动态代理对象
        RayBatisConfiguration configuration = configurationManager.getConfiguration(clazz);
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
            return getJdbcResult(
                    configuration,
                    configuration.getMethodNameToSql().get(method.getName()),
                    configuration.getMethodNameToResultConverter().get(method.getName())
            );
        }
    }

    private Object getJdbcResult(RayBatisConfiguration configuration, String sql,
                                 ResultConverter resultConverter) throws Exception {
        log.info("getJdbcResult执行sql:{}", sql);
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // 1. 加载数据库驱动（对于现代JDBC驱动，可以省略此步骤）
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2. 建立数据库连接
            connection = DriverManager.getConnection(configuration.getUrl(), configuration.getUser(), configuration.getPassword());

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
