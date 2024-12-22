package com.raymond.mybatis.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.raymond.mybatis.session.SqlSession;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MapperProxy<T> implements InvocationHandler {
    private SqlSession sqlSession;
    private Class<?> mapperInterface;

    public MapperProxy(SqlSession sqlSession, Class<?> mapperInterface) {
        this.sqlSession = sqlSession;
        this.mapperInterface = mapperInterface;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("MapperProxy代理执行方法:{}, 交由Executor执行", method.getName());
        return sqlSession.execute();
    }
}
