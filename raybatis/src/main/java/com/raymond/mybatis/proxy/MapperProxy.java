package com.raymond.mybatis.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.raymond.mybatis.session.SqlSession;
import com.raymond.mybatis.testdata.dao.Country;

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
        log.info("MapperProxy代理执行方法:{}", method.getName());
        Country mock = new Country();
        mock.setId(110L);
        mock.setCountryCode("MOCK");
        mock.setCountryName("这是一个假的数据");
        return mock;
    }
}
