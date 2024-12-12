package com.raymond.builder;

import org.apache.ibatis.parsing.XNode;

import com.raymond.configuration.RayBatisConfiguration;
import com.raymond.enums.RaySqlCommandType;

public class RayXMLStatementBuilder {
    private RayBatisConfiguration configuration;
    private XNode context;

    public RayXMLStatementBuilder(RayBatisConfiguration configuration, XNode context) {
        this.configuration = configuration;
        this.context = context;
    }

    public void parseStatementNode(){
        String id = context.getStringAttribute("id");

        String nodeName = context.getNode().getNodeName();
        RaySqlCommandType commandType = RaySqlCommandType.valueOf(nodeName.toLowerCase());

        String resultType = context.getStringAttribute("resultType");
        String resultMap = context.getStringAttribute("resultMap");

    }
}
