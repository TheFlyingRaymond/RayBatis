package com.raymond.raybatis;

import java.util.Set;

import org.junit.Test;
import org.reflections.Reflections;

import com.raymond.raybatis.raybatis.annotation.RayAlias;

public class ReflectionTest {
    @Test
    public void test() {
        String packageName = "com.raymond.raybatis";
        // 配置 Reflections
        Reflections reflections = new Reflections(packageName);
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(RayAlias.class);
        System.out.println(classes);
    }
}
