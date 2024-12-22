package com.raymond.mybatis.builder;

import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;

import com.raymond.mybatis.session.Configuration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class XMLConfigBuilder {
    private XPathParser parser;
    private Configuration configuration;

    private String environment;

    public XMLConfigBuilder(XPathParser parser) {
        this.parser = parser;
        this.configuration = new Configuration();
    }

    public Configuration parse() {
        log.info("开始解析配置文件");
        parseConfiguration(parser.evalNode("/configuration"));
        return configuration;
    }

    private void parseConfiguration(XNode xNode) {
        //environments：用户名密码等
        parseEnvironments(xNode.evalNode("environments"));
    }

    public void parseEnvironments(XNode context) {
        new EnvironmentBuilder(configuration, context, environment).parse();
    }
}
