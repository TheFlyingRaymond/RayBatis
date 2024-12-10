package com.raymond;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.raymond.dao.Country;
import com.raymond.mapper.CountryMapper;

public class RayBatisSession {
    <T> T getMapper(Class<T> clazz) throws Exception {
        // 创建目标对象的实际实现
        CountryMapper target = new CountryMapper() {
            @Override
            public List<Country> selectAll() {
                // 模拟数据库操作，返回空列表
                return new ArrayList<>();
            }
        };

        // 创建并返回动态代理对象
        return (T) Proxy.newProxyInstance(
                clazz.getClassLoader(),
                new Class<?>[]{clazz},
                new ServiceInvocationHandler(target)
        );
    }

    class ServiceInvocationHandler implements InvocationHandler {
        private final Object target;

        public ServiceInvocationHandler(Object target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            // 在方法调用之前执行的逻辑
            System.out.println("在这里拦截了方法，之后会进行JDBC操作");

            return mockFromDB();
        }

        private Object mockFromDB() {
            // 数据库连接信息
            String url = "jdbc:mysql://localhost:3306/raybatis"; // 替换为你的数据库URL
            String user = "root"; // 替换为你的数据库用户名
            String password = ""; // 替换为你的数据库密码

            Connection connection = null;
            Statement statement = null;
            ResultSet resultSet = null;

            List<Country> ret = new ArrayList<>();

            try {
                // 1. 加载数据库驱动（对于现代JDBC驱动，可以省略此步骤）
                Class.forName("com.mysql.cj.jdbc.Driver");

                // 2. 建立数据库连接
                connection = DriverManager.getConnection(url, user, password);

                // 3. 创建Statement对象
                statement = connection.createStatement();

                // 4. 执行SQL查询
                String sql = "SELECT id, country_name, country_code FROM Country";
                resultSet = statement.executeQuery(sql);

                // 5. 处理结果集
                while (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    String countryName = resultSet.getString("country_name");
                    String countryCode = resultSet.getString("country_code");
                    ret.add(new Country(id, countryName, countryCode));
                    System.out.println("ID: " + id + ", Country Name: " + countryName + ", Country Code: " + countryCode);
                }
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

            return ret;
        }
    }
}
