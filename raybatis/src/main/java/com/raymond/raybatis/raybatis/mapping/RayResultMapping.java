package com.raymond.raybatis.raybatis.mapping;


import org.apache.ibatis.type.JdbcType;

import com.raymond.raybatis.raybatis.configuration.RayBatisConfiguration;

import lombok.Data;

@Data
public class RayResultMapping {
    private RayBatisConfiguration configuration;

    private String id;

    private String property;
    private String column;

    private Class<?> javaType;
    private JdbcType jdbcType;
}

