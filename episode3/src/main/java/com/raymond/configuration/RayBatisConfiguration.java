package com.raymond.configuration;

import java.util.HashMap;
import java.util.Map;

import com.raymond.MapperMethod;
import com.raymond.converter.ResultConverter;

import lombok.Data;

@Data
public class RayBatisConfiguration { // 数据库连接信息
    private RayEnvironment environment;

    private Map<String, MapperMethod> methodMap = new HashMap<>();

    private Map<String, ResultConverter> methodNameToResultConverter = new HashMap<>();


}
