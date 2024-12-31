package com.raymond.mybatis.session;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.type.TypeAliasRegistry;

import com.raymond.mybatis.binding.MapperRegistry;
import com.raymond.mybatis.mapping.Environment;
import com.raymond.mybatis.mapping.MappedStatement;
import com.raymond.mybatis.mapping.ResultMap;
import com.raymond.mybatis.type.TypeHandlerRegistry;

import lombok.Data;

@Data
public class Configuration {
    private Environment environment;
    private TypeAliasRegistry typeAliasRegistry = new TypeAliasRegistry();
    private TypeHandlerRegistry typeHandlerRegistry = new TypeHandlerRegistry();
    protected final Map<String, MappedStatement> mappedStatements = new HashMap<>();
    protected ObjectFactory objectFactory = new DefaultObjectFactory();
    protected final Map<String, ResultMap> resultMaps = new HashMap<>();
    protected final MapperRegistry mapperRegistry = new MapperRegistry(this);

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public void adMappedStatement(MappedStatement statement) {
        mappedStatements.put(statement.getKeyStatementId(), statement);
    }

    public MappedStatement getMappedStatement(String id) {
        return mappedStatements.get(id);
    }

    public void addResultMap(ResultMap resultMap) {
        resultMaps.put(resultMap.getId(), resultMap);
    }

    public ResultMap getResultMap(String id) {
        return resultMaps.get(id);
    }

    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        return mapperRegistry.getMapper(type, sqlSession);
    }

    public <T> void addMapper(Class<T> type) {
        mapperRegistry.addMapper(type);
    }

    @Override
    public String toString() {
        return "Configuration{}";
    }
}
