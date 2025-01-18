package com.raymond.mybatis.Executor;

import java.util.List;
import java.util.Map;

import com.raymond.mybatis.mapping.MappedStatement;

public interface Executor {
    <E> List<E> query(MappedStatement mappedStatement, Map<String, Object> parameter);

    int update(MappedStatement ms, Map<String, Object> parameter);

}
