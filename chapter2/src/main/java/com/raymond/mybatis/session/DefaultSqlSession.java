package com.raymond.mybatis.session;

import com.raymond.mybatis.Executor.Executor;
import com.raymond.mybatis.Executor.MockExecutor;
import com.raymond.mybatis.binding.MapperRegistry;
import com.raymond.mybatis.testdata.CountryMapper;

public class DefaultSqlSession implements SqlSession {
    private final Configuration configuration;
    private MapperRegistry mapperRegistry;
    private Executor executor;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
        this.mapperRegistry = new MapperRegistry(configuration);
        mapperRegistry.addMapper(CountryMapper.class);
        this.executor = new MockExecutor(configuration.getEnvironment().getDataSource());
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        return mapperRegistry.getMapper(type, this);
    }

    @Override
    public Object execute() {
        return executor.execute();
    }
}
