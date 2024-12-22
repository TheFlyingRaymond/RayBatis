package com.raymond.mybatis.builder;

import com.raymond.mybatis.session.Configuration;

import lombok.Data;

@Data
public class BaseBuilder {
    protected Configuration configuration;

    public BaseBuilder() {
    }

    public BaseBuilder(Configuration configuration) {
        this.configuration = configuration;
    }
}
