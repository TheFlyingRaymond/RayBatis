package com.raymond.mybatis.binding;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import com.raymond.mybatis.mapping.MappedStatement;
import com.raymond.mybatis.mapping.SqlCommandType;
import com.raymond.mybatis.reflection.ParamNameResolver;
import com.raymond.mybatis.session.Configuration;
import com.raymond.mybatis.session.SqlSession;

import lombok.Data;

public class MapperMethod {
    // 记录了sql的名称和类型
    private final SqlCommand command;
    // 对应的方法签名
    private final MethodSignature method;

    public MapperMethod(Class<?> mapperInterface, Method method, Configuration config) {
        this.command = new SqlCommand(config, mapperInterface, method);
        this.method = new MethodSignature(config, mapperInterface, method);
    }

    public Object execute(SqlSession sqlSession, Object[] args) {
        IRealParam paramNameAndObjMap = method.convertArgsToSqlCommandParam(args);
        switch (command.getType()) {
            case SELECT:
                return doSelect(sqlSession, command.getName(), paramNameAndObjMap);
            case DELETE:
                return doDelete(sqlSession, command.getName(), paramNameAndObjMap);
            case UPDATE:
                return doUpdate(sqlSession, command.getName(), paramNameAndObjMap);
            case INSERT:
                return doInsert(sqlSession, command.getName(), paramNameAndObjMap);
            default:
                throw new RuntimeException("Unsupported SQL command type: " + command.getType());
        }
    }

    private Object doInsert(SqlSession sqlSession, String name,IRealParam paramObj) {
        return sqlSession.insert(name, paramObj);
    }

    private Object doUpdate(SqlSession sqlSession, String name,IRealParam paramObj) {
        return sqlSession.update(name, paramObj);
    }

    private Object doDelete(SqlSession sqlSession, String name, IRealParam paramObj) {
        return sqlSession.delete(name, paramObj);
    }

    private Object doSelect(SqlSession sqlSession, String name,IRealParam paramObj) {
        if (method.isReturnList()) {
            return sqlSession.selectList(name, paramObj);
        }
        return sqlSession.selectOne(name, paramObj);
    }

    @Data
    public static class SqlCommand {
        // 对应到之前mappedStatement的id
        private final String name;

        private final SqlCommandType type;

        public SqlCommand(Configuration config, Class<?> mapperInterface, Method method) {
            String statementId = mapperInterface.getName() + "." + method.getName();
            MappedStatement mappedStatement = config.getMappedStatement(statementId);
            if (mappedStatement == null) {
                throw new RuntimeException("No such mapped statement: '" + statementId + "'");
            }
            this.name = mappedStatement.getKeyStatementId();
            this.type = mappedStatement.getSqlCommandType();
        }
    }

    @Data
    public static class MethodSignature {
        // 引用参数名称解析器
        private final ParamNameResolver paramNameResolver;
        private final boolean returnList;

        public MethodSignature(Configuration config, Class<?> mapperInterface, Method method) {
            this.paramNameResolver = new ParamNameResolver(config, method);
            this.returnList = List.class.isAssignableFrom(method.getReturnType());
        }

        public IRealParam convertArgsToSqlCommandParam(Object[] args) {
            return paramNameResolver.resolveNamedParams(args);
        }
    }
}
