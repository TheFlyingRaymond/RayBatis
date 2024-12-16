package com.raymond.raybatis.raybatis.builder;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;
import org.apache.ibatis.type.JdbcType;

import com.raymond.raybatis.raybatis.configuration.RayBatisConfiguration;
import com.raymond.raybatis.raybatis.exception.RayConfigParseException;
import com.raymond.raybatis.raybatis.mapping.RayResultFlags;
import com.raymond.raybatis.raybatis.mapping.RayResultMap;
import com.raymond.raybatis.raybatis.mapping.RayResultMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RayXMLMapperBuilder extends BaseBuilder {
    private Reader reader;
    private String resource;
    private XPathParser parser;
    private RayMapperBuilderAssistant mapperBuilderAssistant;

    private String namespace;

    public RayXMLMapperBuilder(Reader reader, RayBatisConfiguration configuration, String resource) {
        super(configuration);
        this.reader = reader;
        this.resource = resource;
        this.parser = new XPathParser(reader);
        this.mapperBuilderAssistant = new RayMapperBuilderAssistant(configuration, resource);
    }

    /**
     * 解析mapper的xml文件
     */
    public void parse() throws ClassNotFoundException {
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

    private void parseMapper(XNode context) throws ClassNotFoundException {
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
        configuration.addMapper(Resources.classForName(namespace));

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
        resultMapList.forEach(node->{
            new RayResultMapBuilder(configuration, namespace, node).parse();
        });
        log.info("[MapperBuilder]解析resultMap节点结束");
    }

    private void bindMapper2NameSpace() {
        //resultmap  sql


    }
}
