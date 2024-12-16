package com.raymond.raybatis.raybatis.builder;

import java.util.Objects;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.parsing.XNode;

import com.raymond.raybatis.raybatis.configuration.RayBatisConfiguration;
import com.raymond.raybatis.raybatis.configuration.RayEnvironment;
import com.raymond.raybatis.raybatis.configuration.RaySimpleDataSource;
import com.raymond.raybatis.raybatis.exception.RayConfigParseException;
import com.zaxxer.hikari.HikariConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RayEnvironmentBuilder extends BaseBuilder {
    private XNode context;
    private String environment;

    public RayEnvironmentBuilder(RayBatisConfiguration configuration,
                                 XNode context, String environment) {
        super(configuration);
        this.context = context;
        this.environment = environment;
    }


    public void parse() {
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

    private boolean targetEnvironment(String id) {
        if (null == environment || environment.length() == 0) {
            throw new RayConfigParseException("未找到有效的默认环境ID");
        }

        if (null == id || id.length() == 0) {
            throw new RayConfigParseException("环境配置项ID非法");
        }

        return environment.equals(id);
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

}
