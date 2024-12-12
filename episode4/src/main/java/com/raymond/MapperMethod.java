package com.raymond;

import lombok.Data;

@Data
public class MapperMethod {
    String sql;
    Class<?> returnType;

    public MapperMethod(String sql, Class<?> returnType) {
        this.sql = sql;
        this.returnType = returnType;
    }
}
