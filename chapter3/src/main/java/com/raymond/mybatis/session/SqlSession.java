package com.raymond.mybatis.session;

public interface SqlSession {
    <T> T getMapper(Class<T> type);

    Object execute();
}
