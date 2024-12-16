package com.raymond.cglibtest;

import java.lang.reflect.Method;

import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

@Slf4j
public class ProxyHandler<T> implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        log.info("before I say hello");

        //method.invoke(o, objects);//调用这个会死循环
        Object ret = methodProxy.invokeSuper(o, objects);
        log.info("after I say hello");
        return ret;
    }
}
