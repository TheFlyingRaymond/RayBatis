package com.raymond.builder;

import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;

import com.raymond.RayMappedStatement;
import com.raymond.RayResultMap;
import com.raymond.configuration.RayBatisConfiguration;
import com.raymond.exception.RayConfigParseException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RayXMLMapperBuilder {
    private Reader reader;
    private RayBatisConfiguration configuration;
    private String resource;
    private XPathParser parser;

    private String namespace;

    public RayXMLMapperBuilder(Reader reader, RayBatisConfiguration configuration, String resource) {
        this.reader = reader;
        this.configuration = configuration;
        this.resource = resource;
        this.parser = new XPathParser(reader);
    }

    /**
     * 解析mapper的xml文件
     */
    public void parse() {
        if (configuration.isResourceLoaded(resource)) {
            log.info("该资源已经加载过了:{}", resource);
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

        parseResultMap(context.evalNode("resultMap"));

        parseStatement(context.evalNodes("select|delete|update|insert"));

        log.info("[MapperBuilder]解析mapper节点结束");
    }

    private void parseStatement(List<XNode> sqlList) {
        log.info("[MapperBuilder]开始解析sql节点");
        if (CollectionUtils.isEmpty(sqlList)) {
            log.info("未找到sql节点");
            return;
        }

        sqlList.forEach(this::parseStatementNode);

        log.info("[MapperBuilder]解析sql节点结束");
    }

    private void parseStatementNode(XNode node) {

        String id = node.getStringAttribute("id");
        String resultType = node.getStringAttribute("resultType");

        String resultMap = node.getStringAttribute("resultMap");
        String sql = node.getStringBody();
        try {
            Class<?> resultTypeClass = StringUtils.isEmpty(resultType) ? null : Class.forName(resultType);
            RayMappedStatement statement = new RayMappedStatement(id, sql, resultTypeClass, resultMap, namespace);
            configuration.addStatement(statement);
        } catch (Exception e) {
            log.error("parseStatementNode error, id:{}", id, e);
        }

        log.info("parseStatementNode:{}", node.getStringAttribute("id"));
    }

    private void parseResultMap(List<XNode> resultMapList) {
        log.info("[MapperBuilder]开始解析resultMap节点");
        if (CollectionUtils.isEmpty(resultMapList)) {
            log.info("未找到resultMap节点");
            return;
        }
        resultMapList.forEach(this::parseResultMap);
        log.info("[MapperBuilder]解析resultMap节点结束");
    }

    private void parseResultMap(XNode resultMapNode) {
        try {
            String id = resultMapNode.getStringAttribute("id");
            Class<?> typeClass = Class.forName(resultMapNode.getStringAttribute("type"));

            Map<String, String> map = new HashMap<>();
            for (XNode node : resultMapNode.getChildren()) {
                if (node.getName().equals("id")) {
                    map.put("id", node.getStringAttribute("column"));
                } else if (node.getName().equals("result")) {
                    map.put(node.getStringAttribute("property"), node.getStringAttribute("column"));
                } else {
                    log.error("暂不支持的resultMap子元素类型:{}", node.getName());
                }
            }
            configuration.addResultMap(new RayResultMap(id, typeClass, map, namespace));
        } catch (Exception e) {
            log.error("parseResultMap error, id:{}", resultMapNode.getName(), e);
        }
    }

    private void bindMapper2NameSpace() {
        //resultmap  sql


    }
}
