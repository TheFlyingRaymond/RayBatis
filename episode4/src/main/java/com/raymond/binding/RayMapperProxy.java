package com.raymond.binding;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.raymond.RaySqlSession;

public class RayMapperProxy<T> implements InvocationHandler {
    private RaySqlSession sqlSession;
    private Class<T> mapperInterface;
    private Map<Method, RayMapperMethod> methodCache = new HashMap<>();

    public RayMapperProxy(RaySqlSession sqlSession, Class<T> mapperInterface,
                          Map<Method, RayMapperMethod> methodCache) {
        this.sqlSession = sqlSession;
        this.mapperInterface = mapperInterface;
        this.methodCache = methodCache;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return null;
    }
}
