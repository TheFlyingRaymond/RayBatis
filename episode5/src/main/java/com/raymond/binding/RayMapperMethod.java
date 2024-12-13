package com.raymond.binding;

import java.lang.reflect.Method;
import java.util.Map;

import com.raymond.RaySqlSession;
import com.raymond.configuration.RayBatisConfiguration;
import com.raymond.enums.RaySqlCommandType;
import com.raymond.mapping.RayMappedStatement;

import lombok.Data;

@Data
public class RayMapperMethod {
    // 记录了sql的名称和类型
    private final RaySqlCommand command;
    // 对应的方法签名
//    private final RayMethodSignature method;

    public RayMapperMethod(Method method, Class<?> mapperInterface, RayBatisConfiguration config) {
        this.command = new RaySqlCommand(config, mapperInterface, method);
//        this.method = new RayMethodSignature(config, mapperInterface, method);
    }

    public Object execute(RaySqlSession sqlSession, Object[] args) {
        return null;
    }


    public static class RaySqlCommand {
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
    }
}