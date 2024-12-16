package com.raymond.raybatis.raybatis.mapping;

import com.raymond.raybatis.raybatis.configuration.RayBatisConfiguration;

import lombok.Data;

@Data
public class RayParameterMapping {
    private RayBatisConfiguration configuration;

    private String property;

    private Class<?> javaType;
}
