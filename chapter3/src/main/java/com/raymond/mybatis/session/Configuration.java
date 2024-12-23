package com.raymond.mybatis.session;

import org.apache.ibatis.type.TypeAliasRegistry;

import com.raymond.mybatis.mapping.Environment;

import lombok.Data;

@Data
public class Configuration {
    private Environment environment;
    private TypeAliasRegistry typeAliasRegistry = new TypeAliasRegistry();

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
