package com.raymond.configuration;

import javax.sql.DataSource;

import lombok.Data;

@Data
public class RayEnvironment {
    private DataSource dataSource;
}
