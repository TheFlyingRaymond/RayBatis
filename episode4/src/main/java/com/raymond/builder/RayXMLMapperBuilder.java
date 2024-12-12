package com.raymond.builder;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;
import org.apache.ibatis.type.JdbcType;

import com.raymond.configuration.RayBatisConfiguration;
import com.raymond.exception.RayConfigParseException;
import com.raymond.mapping.RayResultFlags;
import com.raymond.mapping.RayResultMap;
import com.raymond.mapping.RayResultMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RayXMLMapperBuilder extends BaseBuilder {
    private Reader reader;
    private RayBatisConfiguration configuration;
    private String resource;
    private XPathParser parser;
    private RayMapperBuilderAssistant mapperBuilderAssistant;

    private String namespace;

    public RayXMLMapperBuilder(Reader reader, RayBatisConfiguration configuration, String resource) {
        this.reader = reader;
        this.configuration = configuration;
        this.resource = resource;
        this.parser = new XPathParser(reader);
        this.mapperBuilderAssistant = new RayMapperBuilderAssistant(configuration, resource);
    }

    /**
     * 解析mapper的xml文件
     */
    public void parse() {
        if (configuration.isResourceLoaded(resource)) {
            log.info("该资源已经加载过了:{}", resource);
            return;
        }
        //从mapper节点开始解析
        parseMapper(parser.evalNode("/mapper"));
        //标记资源已经被加载过
        configuration.addResource(resource);
        //添加namespace和解析数据的关联关系
        bindMapper2NameSpace();
    }

    private void parseMapper(XNode context) {
        log.info("[MapperBuilder]开始解析mapper节点");
        if (null == context) {
            log.info("未找到有效的mapper节点, resource:{}", resource);
            return;
        }

        String namespace = context.getStringAttribute("namespace");
        if (null == namespace || namespace.length() == 0) {
            throw new RayConfigParseException("namespace是必须的, resource:" + resource);
        }
        this.namespace = namespace;
        mapperBuilderAssistant.setNamespace(namespace);

        parseResultMaps(context.evalNodes("resultMap"));

        parseStatement(context.evalNodes("select|delete|update|insert"));

        log.info("[MapperBuilder]解析mapper节点结束");
    }

    private void parseStatement(List<XNode> sqlList) {
        log.info("[MapperBuilder]开始解析sql节点");
        if (CollectionUtils.isEmpty(sqlList)) {
            log.info("未找到sql节点");
            return;
        }

        sqlList.forEach(x -> {
            RayXMLStatementBuilder statementParser = new RayXMLStatementBuilder(configuration, mapperBuilderAssistant, x);
            try {
                statementParser.parseStatementNode();
            } catch (Exception e) {
                log.error("解析sql节点异常,node:{}",x.getName(),e);
                throw new RuntimeException(e);
            }
        });

        log.info("[MapperBuilder]解析sql节点结束");
    }

    private void parseResultMaps(List<XNode> resultMapList) {
        log.info("[MapperBuilder]开始解析resultMap节点");
        if (CollectionUtils.isEmpty(resultMapList)) {
            log.info("未找到resultMap节点");
            return;
        }
        resultMapList.forEach(this::parseResultMap);
        log.info("[MapperBuilder]解析resultMap节点结束");
    }

    private void parseResultMap(XNode node) {
        try {
            String id = node.getStringAttribute("id");
            Class<?> typeClass = resolveClass(node.getStringAttribute("type"));
            List<RayResultMapping> resultMappings = new ArrayList<>();

            List<XNode> children = node.getChildren();
            for (XNode child : children) {
                List<RayResultFlags> flags = new ArrayList<>();
                if ("id".equals(child.getName())) {
                    flags.add(RayResultFlags.ID);
                }
                resultMappings.add(buildResultMapping(child, typeClass, flags));
            }
            id = namespace + "." + id;
            RayResultMap resultMap = new RayResultMap(configuration, id, typeClass, resultMappings, false);
            configuration.addResultMap(resultMap);
        } catch (Exception e) {
            log.error("parseResultMap error, id:{}", node.getName(), e);
        }
    }

    private RayResultMapping buildResultMapping(XNode child, Class<?> resultType, List<RayResultFlags> flags) {
        String property = child.getStringAttribute("property");
        String column = child.getStringAttribute("column");
        String javaType = child.getStringAttribute("javaType");
        Class<?> javaTypeClass = resolveClass(javaType);
        String jdbcType = child.getStringAttribute("jdbcType");
        JdbcType jdbcTypeEnum = resolveJdbcType(jdbcType);
        return mapperBuilderAssistant.buildResultMapping(resultType, property, column, javaTypeClass, jdbcTypeEnum,
                flags);
    }

    private void bindMapper2NameSpace() {
        //resultmap  sql


    }
}
