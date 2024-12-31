package com.raymond.mybatis.builder;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;

import com.raymond.mybatis.mapping.ResultMap;
import com.raymond.mybatis.mapping.ResultMapping;
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

    private void parseResultMap(List<XNode> xNodes) {
        if (CollectionUtils.isEmpty(xNodes)) {
            return;
        }
        for (XNode node : xNodes) {
            resultMapElement(node);
        }
    }

    private void resultMapElement(XNode resultMapNode) {
        String id = resultMapNode.getStringAttribute("id");

        String type = resultMapNode.getStringAttribute("type",
                resultMapNode.getStringAttribute("ofType",
                        resultMapNode.getStringAttribute("resultType",
                                resultMapNode.getStringAttribute("javaType"))));
        Class<?> typeClass = resolveClass(type);

        ResultMap resultMap = new ResultMap();
        resultMap.setId(mapperBuilderAssistant.applyCurrentNamespace(id));
        resultMap.setType(typeClass);
        resultMap.setConfiguration(configuration);
        List<ResultMapping> resultMappings = new ArrayList<>();
        resultMap.setResultMappings(resultMappings);

        for (XNode child : resultMapNode.getChildren()) {
            String nodeName = child.getName();
            if(!nodeName.equals("id") && !nodeName.equals("result")){
                log.info("暂时支持id和result类型配置，不支持:{}",nodeName);
                continue;
            }

            ResultMapping item = new ResultMapping();
            item.setColumn(child.getStringAttribute("column"));
            item.setProperty(child.getStringAttribute("property"));
            resultMappings.add(item);
        }

        configuration.addResultMap(resultMap);
    }

    private void parseMapper(XNode context) {
        String namespace = context.getStringAttribute("namespace");
        mapperBuilderAssistant.setCurrentNamespace(namespace);

        parseResultMap(context.evalNodes("resultMap"));

        buildStatementFromContext(context.evalNodes("select|insert|update|delete"));

        bindMapperForNamespace();
    }

    private void bindMapperForNamespace() {
        try {
            String namespace = mapperBuilderAssistant.getCurrentNamespace();
            Class<?> boundType = Resources.classForName(namespace);
            configuration.addMapper(boundType);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
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
