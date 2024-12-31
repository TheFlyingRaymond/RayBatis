package com.raymond.mybatis.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.reflection.ParamNameUtil;

import com.raymond.mybatis.annotation.Param;
import com.raymond.mybatis.session.Configuration;


public class ParamNameResolver {
    // 方法形参的索引和参数名称的映射，如果有param修饰则使用其值。这个数据是为了配合sqlsource中的sql执行
    private final SortedMap<Integer, String> names;

    /**
     * 参数名解析器的构造方法
     *
     * @param config 配置信息
     * @param method 要被分析的方法
     */
    public ParamNameResolver(Configuration config, Method method) {
        // 获取参数注解
        final Annotation[][] paramAnnotations = method.getParameterAnnotations();
        final SortedMap<Integer, String> map = new TreeMap<>();
        int paramCount = paramAnnotations.length;
        // 循环处理各个参数
        for (int idx = 0; idx < paramCount; idx++) {
            // 参数名称
            String name = null;
            for (Annotation annotation : paramAnnotations[idx]) {
                // 找出参数的注解
                if (annotation instanceof Param) {
                    name = ((Param) annotation).value();
                    break;
                }
            }

            if (StringUtils.isEmpty(name)) {
                //如果没有注解，则使用默认的参数名称
                name = ParamNameUtil.getParamNames(method).get(idx);
            }
            map.put(idx, name);
        }
        names = Collections.unmodifiableSortedMap(map);
    }


    public Map<String, Object> resolveNamedParams(Object[] args) {
        final Map<String, Object> param = new MapperMethod.ParamMap<>();
        for (Map.Entry<Integer, String> entry : names.entrySet()) {
            //根据之前记录的形参名称和id的映射，构建形参名称--实参的映射
            param.put(entry.getValue(), args[entry.getKey()]);
        }
        return param;
    }
}
