package com.raymond.mybatis.binding;

import java.util.HashMap;
import java.util.Map;

import com.raymond.mybatis.proxy.MapperProxyFactory;
import com.raymond.mybatis.session.Configuration;
import com.raymond.mybatis.session.SqlSession;

public class MapperRegistry {
    private Configuration configuration;

    private Map<Class<?>, MapperProxyFactory<?>> knownMappers = new HashMap<>();

    public MapperRegistry(Configuration configuration) {
        this.configuration = configuration;
    }

    public void addMapper(Class<?> type) {
        MapperProxyFactory<?> mapperProxyFactory = new MapperProxyFactory<>(type);
        knownMappers.put(type, mapperProxyFactory);
    }

    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        MapperProxyFactory<T> mapperProxyFactory = (MapperProxyFactory<T>) knownMappers.get(type);
        return mapperProxyFactory.newInstance(sqlSession);
    }

}
