package com.raymond.mybatis.binding;

import java.lang.reflect.Field;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TypeRealParam implements IRealParam {
    private Object obj;

    public TypeRealParam(Object type) {
        this.obj = type;
    }

    @Override
    public Object getParam(String name) {
        return getFieldValueByReflect(obj, name);
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
