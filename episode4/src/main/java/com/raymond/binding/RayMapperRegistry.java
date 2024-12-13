package com.raymond.binding;

import java.util.HashMap;
import java.util.Map;

import com.raymond.RaySqlSession;
import com.raymond.configuration.RayBatisConfiguration;
import com.raymond.exception.RayConfigBindingException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
