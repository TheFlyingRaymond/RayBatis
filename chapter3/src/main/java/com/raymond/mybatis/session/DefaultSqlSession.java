package com.raymond.mybatis.session;

import java.util.Map;

import com.raymond.mybatis.Executor.Executor;
import com.raymond.mybatis.Executor.SimpleExecutor;
import com.raymond.mybatis.binding.MapperRegistry;
import com.raymond.mybatis.mapping.MappedStatement;
import com.raymond.mybatis.testdata.CountryMapper;

public class DefaultSqlSession implements SqlSession {
    private final Configuration configuration;
    private MapperRegistry mapperRegistry;
    private Executor executor;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
        this.mapperRegistry = new MapperRegistry(configuration);
        mapperRegistry.addMapper(CountryMapper.class);
        this.executor = new SimpleExecutor(configuration.getEnvironment().getDataSource());
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        return mapperRegistry.getMapper(type, this);
    }

    @Override
    public <T> T selectOne(String statement, Map<String, Object> parameter) {
        MappedStatement mappedStatement = configuration.getMappedStatement(statement);

        return executor.query(mappedStatement, parameter);
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }
}
