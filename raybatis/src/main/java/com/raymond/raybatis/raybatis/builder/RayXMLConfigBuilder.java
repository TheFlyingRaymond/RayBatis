package com.raymond.raybatis.raybatis.builder;

import java.io.Reader;
import java.util.Objects;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.parsing.XNode;

import com.raymond.raybatis.raybatis.configuration.RayBatisConfiguration;
import com.raymond.raybatis.raybatis.configuration.RayEnvironment;
import com.raymond.raybatis.raybatis.configuration.RaySimpleDataSource;
import com.raymond.raybatis.raybatis.exception.RayConfigParseException;
import com.raymond.raybatis.raybatis.parsing.RayXPathParser;
import com.zaxxer.hikari.HikariConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RayXMLConfigBuilder {
    private RayXPathParser parser;
    private RayBatisConfiguration configuration;

    private String environment;

    public RayXMLConfigBuilder(RayXPathParser parser) {
        this.parser = parser;
        this.configuration = new RayBatisConfiguration();
    }

    public RayBatisConfiguration parse() {
        log.info("开始解析配置文件");
        parseConfiguration(parser.evalNode("/configuration"));
        return configuration;
    }

    private void parseConfiguration(XNode xNode) {
        //environments：用户名密码等
        parseEnvironments(xNode.evalNode("environments"));
        //mappers：映射器
        parseMappers(xNode.evalNode("mappers"));
        //解析别名
        parseAlias(xNode.evalNode("typeAliases"));

    }

    public void parseAlias(XNode node) {
        if (node == null) {
            return;
        }

        for (XNode child : node.getChildren()) {
            if ("package".equals(child.getName())) {
                configuration.getTypeAliasRegistry().registerPackageAliases(child.getStringAttribute("name"));
                continue;
            }

            String alias = child.getStringAttribute("alias");
            String type = child.getStringAttribute("type");

            try {
                Class<?> clazz = Resources.classForName(type);
                if (StringUtils.isEmpty(alias)) {
                    alias = clazz.getSimpleName();
                }

                configuration.getTypeAliasRegistry().registerAlias(alias, clazz);
            } catch (Exception e) {
                log.error("解析别名失败", e);
            }
        }
    }

    private void parseMappers(XNode context) {
        if (Objects.isNull(context)) {
            log.warn("未找到mappers节点");
            return;
        }

        for (XNode node : context.getChildren()) {
            if (node.getName().equals("package")) {
                log.info("暂时没有能力处理package数据:{}", node.getStringAttribute("name"));
                continue;
            }

            String resource = node.getStringAttribute("resource");
            if (null != resource) {
                addResourceMapper(resource);
                continue;
            }

            String url = node.getStringAttribute("url");
            String clazz = node.getStringAttribute("class");
            if (url != null | clazz != null) {
                log.info("暂时没有能力处理url或clazz的能力, url:{}, clazz:{}", url, clazz);
                continue;
            }

        }

    }

    //解析一个mapper的xml文件
    private void addResourceMapper(String resource) {
        Reader reader = null;
        try {
            reader = Resources.getResourceAsReader(resource);
            new RayXMLMapperBuilder(reader, configuration,resource).parse();
        } catch (Exception e) {
            throw new RayConfigParseException(e);
        }
    }

    public void parseEnvironments(XNode context) {
        new RayEnvironmentBuilder(configuration, context, environment).parse();
    }

    public RayBatisConfiguration getConfiguration() {
        return configuration;
    }
}
