package com.raymond.raybatis.raybatis.configuration;

import javax.sql.DataSource;

import lombok.Data;

@Data
public class RayEnvironment {
    private DataSource dataSource;
}
