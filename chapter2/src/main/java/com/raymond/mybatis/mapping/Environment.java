package com.raymond.mybatis.mapping;

import javax.sql.DataSource;

import lombok.Data;

@Data
public class Environment {
    private DataSource dataSource;
}
