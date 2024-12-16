package com.raymond.raybatis.raybatis.builder;

import org.apache.ibatis.parsing.XNode;

import com.raymond.raybatis.raybatis.configuration.RayBatisConfiguration;
import com.raymond.raybatis.raybatis.enums.RaySqlCommandType;
import com.raymond.raybatis.raybatis.mapping.RayBoundSql;
import com.raymond.raybatis.raybatis.mapping.RayMappedStatement;
import com.raymond.raybatis.raybatis.mapping.RayResultMap;
import com.raymond.raybatis.raybatis.mapping.RaySimpleSqlSource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RayXMLStatementBuilder extends RayBaseBuilder {
    private XNode context;
    private String namespace;

    public RayXMLStatementBuilder(RayBatisConfiguration configuration,
                                  String namespace, XNode context) {
        super(configuration);
        this.context = context;
        this.namespace = namespace;
    }

    public void parse() {
        String id = context.getStringAttribute("id");

        String nodeName = context.getNode().getNodeName();
        RaySqlCommandType commandType = RaySqlCommandType.valueOf(nodeName.toLowerCase());

        String resultType = context.getStringAttribute("resultType");
        Class<?> resultTypeClass = resolveClass(resultType);

        RaySimpleSqlSource sqlSource = new RaySimpleSqlSource();

        String resultMap = context.getStringAttribute("resultMap");

        configuration.addStatement(addMappedStatement(configuration, id, sqlSource, commandType
                , resultMap,
                resultTypeClass));

        log.info("parseStatementNode:{}", context.getStringAttribute("id"));
    }

    public RayMappedStatement addMappedStatement(RayBatisConfiguration configuration, String id,
                                                 RaySimpleSqlSource sqlSource, RaySqlCommandType commandType,
                                                 String resultMap, Class<?> resultTypeClass) {
        RayMappedStatement statement = new RayMappedStatement();
        statement.setConfiguration(configuration);
        statement.setId(namespace + "." + id);
        RayResultMap rayResultMap = configuration.getResultMaps().get(namespace + "." + resultMap);
        statement.setResultMap(rayResultMap);
        statement.setSqlCommandType(commandType);
        RayBoundSql rayBoundSql = new RayBoundSql();
        rayBoundSql.setSql(context.getStringBody());
        statement.setBoundSQL(rayBoundSql);
        return statement;
    }
}
