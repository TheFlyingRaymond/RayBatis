package com.raymond.raybatis.raybatis.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.binding.MapperMethod;

import com.raymond.raybatis.raybatis.annotation.RayParam;
import com.raymond.raybatis.raybatis.configuration.RayBatisConfiguration;

public class RayParamNameResolver {
    private static final String GENERIC_NAME_PREFIX = "param";
    private final SortedMap<Integer, String> names;
    private boolean hasParamAnnotation;

    public RayParamNameResolver(RayBatisConfiguration config, Method method) {
        Class<?>[] paramTypes = method.getParameterTypes();
        Annotation[][] paramAnnotations = method.getParameterAnnotations();
        SortedMap<Integer, String> map = new TreeMap();
        int paramCount = paramAnnotations.length;

        for (int paramIndex = 0; paramIndex < paramCount; ++paramIndex) {
            String name = null;
            Annotation[] annotations = paramAnnotations[paramIndex];

            for (int i = 0; i < annotations.length; i++) {
                Annotation item = annotations[i];
                if (item instanceof RayParam) {
                    name = ((RayParam) item).value();
                    this.hasParamAnnotation = true;
                    break;
                }
            }

            if (StringUtils.isEmpty(name)) {
                name = method.getParameters()[paramIndex].getName();
            }

            map.put(paramIndex, name);
        }

        this.names = Collections.unmodifiableSortedMap(map);
    }

    public Object getNamedParams(Object[] args) {
        final int paramCount = names.size();
        if (args == null || paramCount == 0) {
            return null;
        } else if (!hasParamAnnotation && paramCount == 1) {
            return args[names.firstKey()];
        } else {
            final Map<String, Object> param = new MapperMethod.ParamMap<>();
            int i = 0;
            for (Map.Entry<Integer, String> entry : names.entrySet()) {
                // 首先按照类注释中提供的key,存入一遍   【参数的@Param名称 或者 参数排序：实参值】
                // 注意，key和value交换了位置
                param.put(entry.getValue(), args[entry.getKey()]);
                // add generic param names (param1, param2, ...)
                final String genericParamName = GENERIC_NAME_PREFIX + String.valueOf(i + 1);
                // ensure not to overwrite parameter named with @Param
                // 再按照param1, param2, ...的命名方式存入一遍
                if (!names.containsValue(genericParamName)) {
                    param.put(genericParamName, args[entry.getKey()]);
                }
                i++;
            }
            return param;
        }
    }
}
