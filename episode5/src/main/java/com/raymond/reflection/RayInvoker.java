package com.raymond.reflection;

import java.lang.reflect.InvocationTargetException;

public interface RayInvoker {
    // 方法执行调用器
    Object invoke(Object target, Object[] args) throws IllegalAccessException, InvocationTargetException;
    // 传入参数或者传出参数的类型（如有一个入参就是入参，否则是出参）
    Class<?> getType();
}
