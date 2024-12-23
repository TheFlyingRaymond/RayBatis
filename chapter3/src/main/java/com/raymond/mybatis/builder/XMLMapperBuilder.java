package com.raymond.mybatis.builder;

import java.io.Reader;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;

import com.raymond.mybatis.session.Configuration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class XMLMapperBuilder extends BaseBuilder {
    private Reader reader;
    private String resource;
    private XPathParser parser;
    private MapperBuilderAssistant mapperBuilderAssistant;

    public XMLMapperBuilder(Reader reader, Configuration configuration, String resource) {
        super(configuration);
        this.reader = reader;
        this.resource = resource;
        this.parser = new XPathParser(reader);
        this.mapperBuilderAssistant = new MapperBuilderAssistant(configuration);
    }

    /**
     * 解析mapper的xml文件
     */
    public void parse() throws ClassNotFoundException {
        //从mapper节点开始解析
        parseMapper(parser.evalNode("/mapper"));
    }

    private void parseMapper(XNode context) {
        String namespace = context.getStringAttribute("namespace");
        mapperBuilderAssistant.setCurrentNamespace(namespace);

        buildStatementFromContext(context.evalNodes("select|insert|update|delete"));

    }

    private void buildStatementFromContext(List<XNode> nodes) {
        if (CollectionUtils.isEmpty(nodes)) {
            return;
        }

        for (XNode node : nodes) {
            new XMLStatementBuilder(configuration, mapperBuilderAssistant, node).parse();
        }
    }
}
