package com.raymond.mapping;

import com.raymond.configuration.RayBatisConfiguration;

import lombok.Data;

@Data
public class RayParameterMapping {
    private RayBatisConfiguration configuration;

    private String property;

    private Class<?> javaType;
}
