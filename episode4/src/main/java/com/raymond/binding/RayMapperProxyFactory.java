package com.raymond.binding;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.raymond.RaySqlSession;

public class RayMapperProxyFactory<T> {
    private final Class<T> mapperInterface;
    private final Map<Method, RayMapperMethod> methodCache = new ConcurrentHashMap<>();

    public RayMapperProxyFactory(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    public <T> T newInstance(RaySqlSession sqlSession) {
        RayMapperProxy<T> proxy = (RayMapperProxy<T>) new RayMapperProxy<>(sqlSession, mapperInterface, methodCache);
        return newInstance(proxy);
    }

    protected <T> T newInstance(RayMapperProxy<T> mapperProxy) {
        return (T) Proxy.newProxyInstance(mapperInterface.getClassLoader(), new Class[] { mapperInterface }, mapperProxy);
    }
}
