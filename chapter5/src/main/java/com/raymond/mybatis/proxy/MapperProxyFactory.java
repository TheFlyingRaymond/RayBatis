package com.raymond.mybatis.proxy;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.raymond.mybatis.binding.MapperMethod;
import com.raymond.mybatis.session.SqlSession;

public class MapperProxyFactory<T> {
    private Class<T> mapperInterface;
    private final Map<Method, MapperMethod> methodCache;

    public MapperProxyFactory(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
        this.methodCache = new ConcurrentHashMap<>();
    }

    public <T> T newInstance(SqlSession sqlSession) {
        MapperProxy<T> mapperProxy = new MapperProxy<>(sqlSession, mapperInterface, methodCache);
        return newInstance(mapperProxy);
    }

    private <T> T newInstance(MapperProxy<T> mapperProxy) {
        return (T) Proxy.newProxyInstance(mapperInterface.getClassLoader(), new Class[]{mapperInterface}, mapperProxy);
    }
}
