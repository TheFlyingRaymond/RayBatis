package com.raymond.cglibtest;

import net.sf.cglib.proxy.Enhancer;

public class Test {
    @org.junit.Test
    public void test(){
        Enhancer enhancer = new Enhancer();
        enhancer.setCallback(new ProxyHandler<>());
        enhancer.setSuperclass(User.class);

        User user = (User) enhancer.create();

        user.hello("raymond");
    }
}
