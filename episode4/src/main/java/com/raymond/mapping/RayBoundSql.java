package com.raymond.mapping;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class RayBoundSql {
    private String sql;
    private List<RayParameterMapping> parameterMappings;
    private Map<String, Object> parameters;
}
