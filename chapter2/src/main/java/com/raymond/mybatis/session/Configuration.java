package com.raymond.mybatis.session;

import com.raymond.mybatis.mapping.Environment;

import lombok.Data;

@Data
public class Configuration {
    private Environment environment;
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
