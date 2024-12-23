package com.raymond.mybatis.builder;


import com.raymond.mybatis.mapping.MappedStatement;
import com.raymond.mybatis.mapping.SqlCommandType;
import com.raymond.mybatis.script.SimpleSqlSource;
import com.raymond.mybatis.session.Configuration;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class MapperBuilderAssistant extends BaseBuilder {
    private String currentNamespace;

    public MapperBuilderAssistant(Configuration configuration) {
        super(configuration);
    }

    public String applyCurrentNamespace(String id) {
        return currentNamespace + "." + id;
    }

    public void addMapperStatement(String keyStatementId, SqlCommandType sqlCommandType, SimpleSqlSource sqlSource) {
        MappedStatement statement = new MappedStatement(keyStatementId, sqlCommandType, sqlSource, configuration);
        configuration.adMappedStatement(statement);
    }
}
