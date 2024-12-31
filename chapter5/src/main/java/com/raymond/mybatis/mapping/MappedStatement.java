package com.raymond.mybatis.mapping;

import com.raymond.mybatis.script.SimpleSqlSource;
import com.raymond.mybatis.session.Configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MappedStatement {
    private String keyStatementId;
    private SqlCommandType sqlCommandType;
    private SimpleSqlSource sqlSource;
    private Configuration configuration;
    private ResultMap resultMap;
}
