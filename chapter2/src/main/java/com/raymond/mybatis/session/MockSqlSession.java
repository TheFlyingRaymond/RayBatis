package com.raymond.mybatis.session;

import com.raymond.mybatis.binding.MapperRegistry;
import com.raymond.mybatis.testdata.CountryMapper;

public class MockSqlSession implements SqlSession {
    private final Configuration configuration;
    private MapperRegistry mapperRegistry;

    public MockSqlSession(Configuration configuration) {
        this.configuration = configuration;
        this.mapperRegistry = new MapperRegistry(configuration);
        mapperRegistry.addMapper(CountryMapper.class);
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        return mapperRegistry.getMapper(type, this);
    }
}
