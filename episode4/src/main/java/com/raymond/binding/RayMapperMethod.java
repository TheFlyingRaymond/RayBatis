package com.raymond.binding;

import lombok.Data;

@Data
public class RayMapperMethod {
    String sql;
    Class<?> returnType;

    public RayMapperMethod(String sql, Class<?> returnType) {
        this.sql = sql;
        this.returnType = returnType;
    }
}
