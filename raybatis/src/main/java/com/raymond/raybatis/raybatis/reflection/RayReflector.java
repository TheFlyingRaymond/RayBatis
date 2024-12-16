package com.raymond.raybatis.raybatis.reflection;


import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.raymond.raybatis.raybatis.reflection.property.RayPropertyNamer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RayReflector {
    //需要被处理的类
    private final Class<?> type;

    //key为属性名称，value为对应的set方法
    private final Map<String, RayInvoker> setMethods = new HashMap<>();
    //key为属性名，value为set方法参数的类型
    private final Map<String, Class<?>> setType = new HashMap<>();

    private final Map<String, RayInvoker> getMethods = new HashMap<>();
    //key为属性名，value为ge方法返回的类型，其实就是字段类型
    private final Map<String, Class<?>> getType = new HashMap<>();

    private Constructor<?> defaultConstructor;


    public RayReflector(Class<?> type) {
        this.type = type;

        //解释设置默认构造器
        addDefaultConstructor();

        //扫描get和set方法，包装并记录对应的invoker和type
        addGetMethods();
        addSetMethods();
    }

    private void addGetMethods() {
        Arrays.stream(type.getMethods())
                .filter(x -> x.getParameterCount() == 0 && RayPropertyNamer.isGetter(x.getName()))
                .forEach(x -> {
                    setMethods.put(x.getName(), new RayMethodInvoker(x));
                    setType.put(x.getName(), x.getReturnType());
                });
    }

    private void addSetMethods() {
        Arrays.stream(type.getMethods())
                .filter(x -> x.getParameterCount() == 1 && RayPropertyNamer.isSetter(x.getName()))
                .forEach(x -> {
                    setMethods.put(x.getName(), new RayMethodInvoker(x));
                    setType.put(x.getName(), x.getParameterTypes()[0]);
                });
    }

    private void addDefaultConstructor() {
        for (Constructor<?> item : type.getConstructors()) {
            if (item.getParameterCount() == 0) {
                defaultConstructor = item;
                return;
            }
        }
        log.info("未发现无参构造器,class:{}", type.getName());
    }
}
