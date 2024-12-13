package com.raymond.reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.raymond.exception.RayReflectionException;

public class RayMethodInvoker implements RayInvoker {
    private Method method;
    private Class<?> type;

    public RayMethodInvoker(Method method) {
        this.method = method;
        if (method.getParameterCount() == 0) {
            //get方法，无参，取返回类型
            type = method.getReturnType();
        } else {
            //set方法，一个参数，取参数类型
            type = method.getParameterTypes()[0];
        }
    }

    @Override
    public Object invoke(Object target, Object[] args) throws IllegalAccessException, InvocationTargetException {
        try {
            return method.invoke(target, args);
        } catch (Exception e) {
            //不处理访问权限的问题
            throw new RayReflectionException(e);
        }
    }

    @Override
    public Class<?> getType() {
        return type;
    }
}
