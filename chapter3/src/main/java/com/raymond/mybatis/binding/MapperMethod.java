package com.raymond.mybatis.binding;

import java.lang.reflect.Method;
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
        Map<String, Object> paramNameAndObjMap = method.convertArgsToSqlCommandParam(args);
        switch (command.getType()) {
            case SELECT:
                return sqlSession.selectOne(command.getName(), paramNameAndObjMap);
            default:
                throw new RuntimeException("Unsupported SQL command type: " + command.getType());
        }
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

        public MethodSignature(Configuration config, Class<?> mapperInterface, Method method) {
            this.paramNameResolver = new ParamNameResolver(config, method);
        }

        public Map<String, Object> convertArgsToSqlCommandParam(Object[] args) {
            return paramNameResolver.resolveNamedParams(args);
        }
    }
}
