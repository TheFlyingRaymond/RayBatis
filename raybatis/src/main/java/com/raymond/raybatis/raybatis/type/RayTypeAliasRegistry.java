package com.raymond.raybatis.raybatis.type;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.type.TypeException;
import org.reflections.Reflections;

import com.raymond.raybatis.raybatis.annotation.RayAlias;
import com.raymond.raybatis.raybatis.exception.RayConfigParseException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RayTypeAliasRegistry {
    private final Map<String, Class<?>> typeAliasMap = new HashMap<>();

    public RayTypeAliasRegistry() {
        registerDefaultAlias();
    }

    public <T> Class<T> resolveAlias(String string) {
        try {
            if (string == null) {
                return null;
            }
            //以字符I为例，在英语中对应的小写是i，在别的语言中对应的可能是另一个字符
            String key = string.toLowerCase(Locale.ENGLISH);
            Class<T> value;
            if (typeAliasMap.containsKey(key)) {
                value = (Class<T>) typeAliasMap.get(key);
            } else {
                value = (Class<T>) Resources.classForName(string);
            }
            return value;
        } catch (ClassNotFoundException e) {
            throw new TypeException("Could not resolve type alias '" + string + "'.  Cause: " + e, e);
        }
    }

    public void registerAlias(String alias, Class<?> target) {
        log.debug("开始注册别名, 别名:{}, 目标类:{}", alias, target.getName());
        if (alias == null || target == null) {
            throw new RayConfigParseException("Error registering typeAlias.  TypeAlias or JavaType already registered.");
        }

        if (typeAliasMap.containsKey(alias) && !typeAliasMap.get(alias).equals(target)) {
            throw new RayConfigParseException("Error registering typeAlias.  TypeAlias already registered.");
        }

        typeAliasMap.put(alias, target);
        log.debug("注册别名成功, 别名:{}, 目标类:{}", alias, target.getName());
    }

    private void registerDefaultAlias() {
        registerAlias("string", String.class);

        registerAlias("byte", Byte.class);
        registerAlias("long", Long.class);
        registerAlias("short", Short.class);
        registerAlias("int", Integer.class);
        registerAlias("integer", Integer.class);
        registerAlias("double", Double.class);
        registerAlias("float", Float.class);
        registerAlias("boolean", Boolean.class);

        registerAlias("byte[]", Byte[].class);
        registerAlias("long[]", Long[].class);
        registerAlias("short[]", Short[].class);
        registerAlias("int[]", Integer[].class);
        registerAlias("integer[]", Integer[].class);
        registerAlias("double[]", Double[].class);
        registerAlias("float[]", Float[].class);
        registerAlias("boolean[]", Boolean[].class);

        registerAlias("_byte", byte.class);
        registerAlias("_long", long.class);
        registerAlias("_short", short.class);
        registerAlias("_int", int.class);
        registerAlias("_integer", int.class);
        registerAlias("_double", double.class);
        registerAlias("_float", float.class);
        registerAlias("_boolean", boolean.class);

        registerAlias("_byte[]", byte[].class);
        registerAlias("_long[]", long[].class);
        registerAlias("_short[]", short[].class);
        registerAlias("_int[]", int[].class);
        registerAlias("_integer[]", int[].class);
        registerAlias("_double[]", double[].class);
        registerAlias("_float[]", float[].class);
        registerAlias("_boolean[]", boolean[].class);

        registerAlias("date", Date.class);
        registerAlias("decimal", BigDecimal.class);
        registerAlias("bigdecimal", BigDecimal.class);
        registerAlias("biginteger", BigInteger.class);
        registerAlias("object", Object.class);

        registerAlias("date[]", Date[].class);
        registerAlias("decimal[]", BigDecimal[].class);
        registerAlias("bigdecimal[]", BigDecimal[].class);
        registerAlias("biginteger[]", BigInteger[].class);
        registerAlias("object[]", Object[].class);

        registerAlias("map", Map.class);
        registerAlias("hashmap", HashMap.class);
        registerAlias("list", List.class);
        registerAlias("arraylist", ArrayList.class);
        registerAlias("collection", Collection.class);
        registerAlias("iterator", Iterator.class);

        registerAlias("ResultSet", ResultSet.class);
    }

    public Map<String, Class<?>> getTypeAliasMap() {
        return typeAliasMap;
    }

    public void registerPackageAliases(String packageName) {
        log.info("开始注册包别名, 包名:{}", packageName);

        Set<Class<?>> classes =  new Reflections(packageName).getTypesAnnotatedWith(RayAlias.class);
        for (Class<?> clazz : classes) {
            RayAlias aliasAnnotation = clazz.getAnnotation(RayAlias.class);
            String alias = aliasAnnotation.value();
            if (StringUtils.isEmpty(alias)) {
                alias = clazz.getSimpleName();
            }
            log.info("包扫描注册目标类, alias:{}, class:{}", alias, clazz.getCanonicalName());
            typeAliasMap.put(alias, clazz);
        }
    }
}
