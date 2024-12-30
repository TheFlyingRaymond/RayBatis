package com.raymond.mybatis.session;

import java.util.Map;

public interface SqlSession {
    <T> T getMapper(Class<T> type);

    <T> T selectOne(String statement, Map<String,Object> parameter);

    Configuration getConfiguration();
}
