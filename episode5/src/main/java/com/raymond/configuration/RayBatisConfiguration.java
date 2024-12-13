package com.raymond.configuration;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.type.TypeAliasRegistry;

import com.raymond.binding.RayMapperMethod;
import com.raymond.RaySqlSession;
import com.raymond.binding.RayMapperRegistry;
import com.raymond.mapping.RayMappedStatement;
import com.raymond.mapping.RayResultMap;
import com.raymond.reflection.RayReflectorFactory;

import lombok.Data;

@Data
public class RayBatisConfiguration { // 数据库连接信息
    private RayEnvironment environment;

    private Map<String, RayMapperMethod> methodMap = new HashMap<>();

    private RayMapperRegistry mapperRegistry = new RayMapperRegistry(this);

    private Set<String> loadedResources = new HashSet<>();

    private RayReflectorFactory reflectorFactory = new RayReflectorFactory();

    protected final Map<String, RayResultMap> resultMaps = new HashMap<>();

    protected final Map<String, RayMappedStatement> statementMap = new HashMap<>();


    private TypeAliasRegistry typeAliasRegistry = new TypeAliasRegistry();


    public <T> void addMapper(Class<T> type) {
        mapperRegistry.addMapper(type);
    }

    public void addResource(String resource) {
        loadedResources.add(resource);
    }

    public boolean isResourceLoaded(String resource) {
        return loadedResources.contains(resource);
    }

    public void addResultMap(RayResultMap rayResultMap) {
        resultMaps.put(rayResultMap.getId(), rayResultMap);
    }

    public void addStatement(RayMappedStatement rayMappedStatement) {
        statementMap.put(rayMappedStatement.getId(), rayMappedStatement);
    }

    public <T> T getMapper(Class<T> type, RaySqlSession sqlSession) {
        return mapperRegistry.getMapper(type, sqlSession);
    }
}
