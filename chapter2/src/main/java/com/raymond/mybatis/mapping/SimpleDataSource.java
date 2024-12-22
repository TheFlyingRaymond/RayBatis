package com.raymond.mybatis.mapping;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class SimpleDataSource extends HikariDataSource {
    public SimpleDataSource(HikariConfig config) {
        super(config);
    }
}
