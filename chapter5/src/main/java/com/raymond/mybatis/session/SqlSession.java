package com.raymond.mybatis.session;

import java.util.List;
import java.util.Map;

public interface SqlSession {
    <T> T getMapper(Class<T> type);

    <T> T selectOne(String statement, Map<String,Object> parameter);

    <T> List<T> selectList(String statement, Map<String,Object> parameter);

    int delete(String statement, Map<String,Object> parameter);

    int update(String statement, Map<String,Object> parameter);

    Configuration getConfiguration();

    int insert(String name, Map<String, Object> paramNameAndObjMap);
}
