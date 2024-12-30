package com.raymond.mybatis.Executor;

import java.util.Map;

import com.raymond.mybatis.mapping.MappedStatement;

public interface Executor {
    <T> T query(MappedStatement mappedStatement, Map<String, Object> parameter);
}
