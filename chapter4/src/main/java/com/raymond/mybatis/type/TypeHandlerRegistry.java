package com.raymond.mybatis.type;

import java.util.HashMap;
import java.util.Map;


public class TypeHandlerRegistry {
    private final Map<Class<?>, TypeHandler<?>> jdbcTypeHandlerMap = new HashMap<>();

    public TypeHandler<?> getTypeHandler(Class<?> type) {
        return jdbcTypeHandlerMap.get(type);
    }

    public void register(Class<?> type, TypeHandler<?> typeHandler) {
        jdbcTypeHandlerMap.put(type, typeHandler);
    }

    public TypeHandlerRegistry() {
        register(Integer.class, new IntegerTypeHandler());
        register(int.class, new IntegerTypeHandler());
        register(Long.class, new LongTypeHandler());
        register(long.class, new LongTypeHandler());
        register(String.class, new StringTypeHandler());
    }
}
