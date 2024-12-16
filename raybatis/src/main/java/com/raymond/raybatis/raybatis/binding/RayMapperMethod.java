package com.raymond.raybatis.raybatis.binding;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

import org.apache.ibatis.reflection.TypeParameterResolver;

import com.raymond.raybatis.raybatis.RaySqlSession;
import com.raymond.raybatis.raybatis.configuration.RayBatisConfiguration;
import com.raymond.raybatis.raybatis.enums.RaySqlCommandType;
import com.raymond.raybatis.raybatis.mapping.RayMappedStatement;
import com.raymond.raybatis.raybatis.reflection.RayParamNameResolver;

import lombok.Data;

@Data
public class RayMapperMethod {
    // 记录了sql的名称和类型
    private final RaySqlCommand command;
    // 对应的方法签名
    private final RayMethodSignature method;

    public RayMapperMethod(Method method, Class<?> mapperInterface, RayBatisConfiguration config) {
        this.command = new RaySqlCommand(config, mapperInterface, method);
        this.method = new RayMethodSignature(config, mapperInterface, method);
    }

    public Object execute(RaySqlSession sqlSession, Object[] args) {
        Object result = null;
        switch (command.type) {
            case select:
                if (method.returnsMany) {
                    result = sqlSession.selectList(command.getName(), method.convertArgsToSqlCommandParam(args));
                } else {
                    result = sqlSession.selectOne(command.getName(), method.convertArgsToSqlCommandParam(args));
                }
            default:
                ;
        }

        return result;
    }


    public  class RayMethodSignature {
        // 返回类型是否为集合类型
        private final boolean returnsMany;
        // 返回类型是否是map
//        private final boolean returnsMap;
        // 返回类型是否是空
        private final boolean returnsVoid;
        // 返回类型
        private final Class<?> returnType;

        // 引用参数名称解析器
        private final RayParamNameResolver paramNameResolver;

        public RayMethodSignature(RayBatisConfiguration configuration, Class<?> mapperInterface, Method method) {
            Type resolvedReturnType = TypeParameterResolver.resolveReturnType(method, mapperInterface);
            if (resolvedReturnType instanceof Class<?>) {
                this.returnType = (Class<?>) resolvedReturnType;
            } else if (resolvedReturnType instanceof ParameterizedType) {
                this.returnType = (Class<?>) ((ParameterizedType) resolvedReturnType).getRawType();
            } else {
                this.returnType = method.getReturnType();
            }

            this.returnsVoid = void.class.equals(this.returnType);
            this.returnsMany = configuration.getObjectFactory().isCollection(this.returnType) || this.returnType.isArray();
            this.paramNameResolver = new RayParamNameResolver(configuration, method);



        }

        public Object convertArgsToSqlCommandParam(Object[] args) {
            return paramNameResolver.getNamedParams(args);
        }
    }


    public class RaySqlCommand {
        // SQL语句的名称
        private final String name;
        //只支持增删改查
        private final RaySqlCommandType type;

        public RaySqlCommand(RayBatisConfiguration configuration, Class<?> mapperInterface, Method method) {
            // 方法名称
            final String methodName = method.getName();

            Map<String, RayMappedStatement> statementMap = configuration.getStatementMap();
            String s = mapperInterface.getCanonicalName() + "." + methodName;
            RayMappedStatement statement = statementMap.get(s);
            this.name = statement.getId();
            this.type = statement.getSqlCommandType();
        }

        public String getName() {
            return name;
        }

        public RaySqlCommandType getType() {
            return type;
        }
    }
}