package com.raymond.mybatis.Executor;

import java.util.List;
import java.util.Map;

import com.raymond.mybatis.binding.IRealParam;
import com.raymond.mybatis.mapping.MappedStatement;

public interface Executor {
    <E> List<E> query(MappedStatement mappedStatement, IRealParam parameter);

    int update(MappedStatement ms, IRealParam parameter);

}
