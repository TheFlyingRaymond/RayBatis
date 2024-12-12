package com.raymond.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class RaySimpleDataSource extends HikariDataSource {
    public RaySimpleDataSource(HikariConfig config) {
        super(config);
    }
}
