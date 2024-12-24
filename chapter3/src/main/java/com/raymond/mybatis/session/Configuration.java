package com.raymond.mybatis.session;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.type.TypeAliasRegistry;

import com.raymond.mybatis.mapping.Environment;
import com.raymond.mybatis.mapping.MappedStatement;

import lombok.Data;

@Data
public class Configuration {
    private Environment environment;
    private TypeAliasRegistry typeAliasRegistry = new TypeAliasRegistry();
    protected final Map<String, MappedStatement> mappedStatements = new HashMap<>();
    protected ObjectFactory objectFactory = new DefaultObjectFactory();

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public void adMappedStatement(MappedStatement statement) {
        mappedStatements.put(statement.getKeyStatementId(), statement);
    }

    public MappedStatement getMappedStatement(String id) {
        return mappedStatements.get(id);
    }


    @Override
    public String toString() {
        return "Configuration{}";
    }
}
