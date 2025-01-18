package com.raymond.mybatis.binding;

import java.lang.reflect.Field;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TypeRealParam implements IRealParam {
    private Object obj;
    private Class<?> objClass;

    public TypeRealParam(Object obj) {
        this.obj = obj;
        this.objClass = obj.getClass();
    }

    @Override
    public Object getParam(String name) {
        return getFieldValueByReflect(obj, name);
    }

    @Override
    public void setParam(String name, Object value) {
        try {
            Field field = objClass.getDeclaredField(name);
            field.setAccessible(true);
            field.set(obj, value);
        } catch (Exception e) {
            log.error("TypeRealParam setParam error, name:{}, value:{}", name, value, e);
            throw new RuntimeException(e);
        }
    }

    private Object getFieldValueByReflect(Object obj, String name) {
        try {
            Class<?> countryClass = obj.getClass();
            Field field = countryClass.getDeclaredField(name);
            field.setAccessible(true);
            Object ret = field.get(obj);
            log.info("getFieldValueByReflect success, obj:{}, name:{}, ret:{}", obj.getClass().getSimpleName(), name,
                    ret);
            return ret;
        } catch (Exception e) {
            log.error("getFieldValueByReflect error, obj:{}, name:{}", obj.getClass().getSimpleName(), name, e);
        }
        return null;
    }
}
