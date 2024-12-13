package com.raymond;

public interface RaySqlSession {
    <T> T getMapper(Class<T> type);
}
