package com.raymond.mybatis.session;

import java.util.List;

import com.raymond.mybatis.binding.IRealParam;

public interface SqlSession {
    <T> T getMapper(Class<T> type);

    <T> T selectOne(String statement, IRealParam paramObj);

    <T> List<T> selectList(String statement, IRealParam paramObj);

    int delete(String statement, IRealParam paramObj);

    int update(String statement, IRealParam paramObj);

    Configuration getConfiguration();

    int insert(String name, IRealParam paramObj);
}
