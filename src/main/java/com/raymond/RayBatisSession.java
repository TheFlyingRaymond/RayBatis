package com.raymond;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
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

            return null;
        }
    }
}
