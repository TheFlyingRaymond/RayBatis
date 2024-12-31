package com.raymond.mybatis.Executor;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.raymond.mybatis.mapping.MappedStatement;

public interface Executor {
    <E> List<E> query(MappedStatement mappedStatement, Map<String, Object> parameter);

    int update(org.apache.ibatis.mapping.MappedStatement ms, Map<String, Object> parameter) throws SQLException;
}
