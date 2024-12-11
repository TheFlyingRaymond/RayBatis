package com.raymond.builder;

import java.util.Objects;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.parsing.XNode;

import com.raymond.configuration.RayBatisConfiguration;
import com.raymond.configuration.RayEnvironment;
import com.raymond.configuration.RaySimpleDataSource;
import com.raymond.exception.RayConfigParseException;
import com.raymond.parsing.RayXPathParser;
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
    }

    private void parseEnvironments(XNode context) {
        if (Objects.isNull(context)) {
            throw new RayConfigParseException("未找到environments节点");
        }

        if (null == environment) {
            environment = context.getStringAttribute("default");
        }

        for (XNode node : context.getChildren()) {
            String id = node.getStringAttribute("id");
            if (!targetEnvironment(id)) {
                log.info("未采用的环境配置:{}", id);
                continue;
            }

            RayEnvironment environment = new RayEnvironment();
            environment.setDataSource(parseDataSources(node.evalNode("dataSource")));
            configuration.setEnvironment(environment);
        }
    }

    private DataSource parseDataSources(XNode context) {
        if (null == context) {
            throw new RayConfigParseException("未找有效的DataSource配置");
        }
        HikariConfig config = new HikariConfig();
        Properties prop = context.getChildrenAsProperties();
        config.setDriverClassName(prop.getProperty("driver"));
        config.setJdbcUrl(prop.getProperty("url"));
        config.setUsername(prop.getProperty("username"));
        config.setPassword(prop.getProperty("password"));
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        return new RaySimpleDataSource(config);
    }

    private boolean targetEnvironment(String id) {
        if (null == environment || environment.length() == 0) {
            throw new RayConfigParseException("未找到有效的默认环境ID");
        }

        if (null == id || id.length() == 0) {
            throw new RayConfigParseException("环境配置项ID非法");
        }

        return environment.equals(id);
    }
}
