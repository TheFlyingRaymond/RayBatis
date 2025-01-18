package com.raymond.mybatis.session;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;

import com.raymond.mybatis.Executor.Executor;
import com.raymond.mybatis.Executor.SimpleExecutor;
import com.raymond.mybatis.binding.MapperRegistry;
import com.raymond.mybatis.mapping.MappedStatement;
import com.raymond.mybatis.testdata.CountryMapper;
import com.raymond.mybatis.testdata.PeopleMapper;

public class DefaultSqlSession implements SqlSession {
    private final Configuration configuration;
    private Executor executor;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
        this.executor = new SimpleExecutor(configuration.getEnvironment().getDataSource());
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        return configuration.getMapper(type, this);
    }

    @Override
    public <T> T selectOne(String statement, Map<String, Object> parameter) {
        List<T> list = selectList(statement, parameter);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        if (list.size() > 1) {
            throw new RuntimeException("返回结果过多");
        }
        return list.get(0);
    }

    @Override
    public <T> List<T> selectList(String statement, Map<String, Object> parameter) {
        MappedStatement mappedStatement = configuration.getMappedStatement(statement);
        return executor.query(mappedStatement, parameter);
    }

    @Override
    public int delete(String statement, Map<String, Object> parameter) {
        return update(statement, parameter);
    }

    @Override
    public int insert(String statement, Map<String, Object> parameter) {
        return update(statement, parameter);
    }

    @Override
    public int update(String statement, Map<String, Object> parameter) {
        MappedStatement mappedStatement = configuration.getMappedStatement(statement);
        return executor.update(mappedStatement, parameter);
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }
}
