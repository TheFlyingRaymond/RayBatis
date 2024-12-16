package com.raymond.raybatis.raybatis.configuration;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.type.TypeAliasRegistry;

import com.raymond.raybatis.raybatis.binding.RayMapperMethod;
import com.raymond.raybatis.raybatis.RaySqlSession;
import com.raymond.raybatis.raybatis.binding.RayMapperRegistry;
import com.raymond.raybatis.raybatis.mapping.RayMappedStatement;
import com.raymond.raybatis.raybatis.mapping.RayResultMap;
import com.raymond.raybatis.raybatis.reflection.RayReflectorFactory;
import com.raymond.raybatis.raybatis.type.RayTypeAliasRegistry;

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

    //别名注册
    private RayTypeAliasRegistry typeAliasRegistry = new RayTypeAliasRegistry();

    protected ObjectFactory objectFactory = new DefaultObjectFactory();


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

    @Override
    public String toString() {
        return "RayBatisConfiguration{}";
    }
}
