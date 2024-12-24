package com.raymond.mybatis.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;


import com.raymond.mybatis.binding.MapperMethod;
import com.raymond.mybatis.session.SqlSession;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MapperProxy<T> implements InvocationHandler {
    private SqlSession sqlSession;
    private Class<?> mapperInterface;
    private final Map<Method, MapperMethod> methodCache;

    public MapperProxy(SqlSession sqlSession, Class<?> mapperInterface, Map<Method, MapperMethod> methodCache) {
        this.sqlSession = sqlSession;
        this.mapperInterface = mapperInterface;
        this.methodCache = methodCache;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("MapperProxy代理执行方法:{}, 交由Executor执行", method.getName());

        final MapperMethod mapperMethod = cachedMapperMethod(method);

        return mapperMethod.execute(sqlSession, args);
    }

    private MapperMethod cachedMapperMethod(Method method) {
        return methodCache.computeIfAbsent(method, k -> new MapperMethod(mapperInterface, method, sqlSession.getConfiguration()));
    }
}
