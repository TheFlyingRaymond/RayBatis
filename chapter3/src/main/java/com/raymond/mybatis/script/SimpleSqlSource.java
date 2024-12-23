package com.raymond.mybatis.script;

import java.util.List;

import com.raymond.mybatis.session.Configuration;

import lombok.Data;

@Data
public class SimpleSqlSource{
    private final String sql;
    private final List<String> parameterMappings;
    private final Configuration configuration;


    public SimpleSqlSource(Configuration configuration, String sql, List<String> parameterMappings) {
        this.sql = sql;
        this.parameterMappings = parameterMappings;
        this.configuration = configuration;
    }
}
