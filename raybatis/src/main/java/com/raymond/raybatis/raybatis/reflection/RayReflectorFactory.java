package com.raymond.raybatis.raybatis.reflection;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RayReflectorFactory {
    private static final Map<Class<?>, RayReflector> reflectorMap = new ConcurrentHashMap<>();

    public static RayReflector getReflector(Class<?> type) {
        return reflectorMap.computeIfAbsent(type, RayReflector::new);
    }
}
