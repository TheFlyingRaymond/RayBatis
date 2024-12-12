package com.raymond.configuration;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.raymond.MapperMethod;
import com.raymond.mapping.RayMappedStatement;
import com.raymond.mapping.RayResultMap;
import com.raymond.converter.ResultConverter;

import lombok.Data;

@Data
public class RayBatisConfiguration { // 数据库连接信息
    private RayEnvironment environment;

    private Map<String, MapperMethod> methodMap = new HashMap<>();

    private Map<String, ResultConverter> methodNameToResultConverter = new HashMap<>();

    private Set<String> loadedResources = new HashSet<>();

    private Map<String, Object> nameToMapper = new HashMap<>();

    protected final Map<String, RayResultMap> resultMaps = new HashMap<>();
    protected final Map<String, RayMappedStatement> statementMap = new HashMap<>();

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
}
