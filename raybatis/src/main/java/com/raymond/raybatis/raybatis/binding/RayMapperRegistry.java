package com.raymond.raybatis.raybatis.binding;

import java.util.HashMap;
import java.util.Map;

import com.raymond.raybatis.raybatis.RaySqlSession;
import com.raymond.raybatis.raybatis.configuration.RayBatisConfiguration;
import com.raymond.raybatis.raybatis.exception.RayConfigBindingException;

import lombok.Data;

@Data
public class RayMapperRegistry {
    private RayBatisConfiguration configuration;
    private Map<Class<?>, RayMapperProxyFactory<?>> knownMappers = new HashMap<>();

    public RayMapperRegistry(RayBatisConfiguration configuration) {
        this.configuration = configuration;
    }

    public <T> T getMapper(Class<T> type, RaySqlSession sqlSession) {
        RayMapperProxyFactory<?> mapperProxyFactory = knownMappers.get(type);

        if (mapperProxyFactory == null) {
            throw new RayConfigBindingException("unknown type:" + type.getName());
        }

        return mapperProxyFactory.newInstance(sqlSession);
    }

    public <T> void addMapper(Class<T> type) {
        knownMappers.putIfAbsent(type, new RayMapperProxyFactory<>(type));
    }
}
