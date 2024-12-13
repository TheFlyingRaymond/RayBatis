package com.raymond.builder;

import org.apache.ibatis.parsing.XNode;

import com.raymond.configuration.RayBatisConfiguration;
import com.raymond.enums.RaySqlCommandType;
import com.raymond.mapping.RaySimpleSqlSource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RayXMLStatementBuilder extends BaseBuilder {
    private XNode context;
    private RayMapperBuilderAssistant mapperBuilderAssistant;

    public RayXMLStatementBuilder(RayBatisConfiguration configuration,
                                  RayMapperBuilderAssistant mapperBuilderAssistant, XNode context) {
        super(configuration);
        this.context = context;
        this.mapperBuilderAssistant = mapperBuilderAssistant;
    }

    public void parseStatementNode() {
        String id = context.getStringAttribute("id");

        String nodeName = context.getNode().getNodeName();
        RaySqlCommandType commandType = RaySqlCommandType.valueOf(nodeName.toLowerCase());

        String resultType = context.getStringAttribute("resultType");
        Class<?> resultTypeClass = resolveClass(resultType);

        RaySimpleSqlSource sqlSource = new RaySimpleSqlSource();

        String resultMap = context.getStringAttribute("resultMap");

        configuration.addStatement(mapperBuilderAssistant.addMappedStatement(configuration,id, sqlSource, commandType
                , resultMap,
                resultTypeClass));

        log.info("parseStatementNode:{}", context.getStringAttribute("id"));
    }
}
