package com.raymond.mybatis.builder;

import java.util.Properties;

import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;

import com.raymond.mybatis.datasource.DataSourceFactory;
import com.raymond.mybatis.mapping.Environment;
import com.raymond.mybatis.session.Configuration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class XMLConfigBuilder extends BaseBuilder{
    private XPathParser parser;

    private String environment;

    public XMLConfigBuilder(XPathParser parser) {
        super(new Configuration());
        this.parser = parser;
    }

    public Configuration parse() {
        log.info("开始解析配置文件");
        try {
            parseConfiguration(parser.evalNode("/configuration"));
            return configuration;
        } catch (Exception e) {
            log.error("解析配置文件失败", e);
            throw new RuntimeException(e);
        }
    }

    private void parseConfiguration(XNode node) throws Exception {
        parseAliases(node.evalNode("typeAliases"));

        //environments：用户名密码等
        parseEnvironments(node.evalNode("environments"));
    }

    private void parseAliases(XNode typeAliases) {
        for (XNode node : typeAliases.getChildren()) {
            String alias = node.getStringAttribute("alias");
            String type = node.getStringAttribute("type");
            configuration.getTypeAliasRegistry().registerAlias(alias, type);
        }
    }

    public void parseEnvironments(XNode context) throws Exception {
        if (null == environment) {
            environment = context.getStringAttribute("default");
        }

        for (XNode node : context.getChildren()) {
            String id = node.getStringAttribute("id");
            if (!environment.equals(id)) {
                log.info("未采用的环境配置:{}", id);
                continue;
            }

            DataSourceFactory datasourceFactory = dataSourceElement(node.evalNode("dataSource"));
            Environment environment = new Environment();
            environment.setDataSource(datasourceFactory.getDataSource());
            configuration.setEnvironment(environment);
        }
    }

    private DataSourceFactory dataSourceElement(XNode context) throws Exception {
        if (context != null) {
            // 我们在type中指定数据源工厂的全类目，通过反射获取到实例
            String type = context.getStringAttribute("type");
            DataSourceFactory factory = (DataSourceFactory) resolveClass(type).newInstance();

            // 获取username等信息,将信息传递给数据源工厂
            Properties props = context.getChildrenAsProperties();
            factory.setProperties(props);
            return factory;
        }
        throw new RuntimeException("获取数据源工厂失败");
    }
}
