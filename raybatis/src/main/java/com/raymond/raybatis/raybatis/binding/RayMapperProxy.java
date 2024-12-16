package com.raymond.raybatis.raybatis.binding;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.raymond.raybatis.raybatis.RaySqlSession;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
        log.info("代理到了方法:{}", method.getName());
        RayMapperMethod mapperMethod = methodCache.computeIfAbsent(
                method, k -> new RayMapperMethod(method, mapperInterface, sqlSession.getConfiguration()));
        return mapperMethod.execute(sqlSession, args);
    }


}
