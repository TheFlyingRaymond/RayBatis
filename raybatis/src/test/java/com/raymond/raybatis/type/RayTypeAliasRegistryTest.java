package com.raymond.raybatis.type;

import java.io.Reader;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.apache.ibatis.io.Resources;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.raymond.raybatis.raybatis.builder.RayXMLConfigBuilder;
import com.raymond.raybatis.raybatis.configuration.RayBatisConfiguration;
import com.raymond.raybatis.raybatis.dao.Country;
import com.raymond.raybatis.raybatis.dao.User;
import com.raymond.raybatis.raybatis.dao.dao2.Country2;
import com.raymond.raybatis.raybatis.dao.dao2.User2;
import com.raymond.raybatis.raybatis.parsing.RayXPathParser;

public class RayTypeAliasRegistryTest {
    private RayXMLConfigBuilder builder;
    private Reader reader;
    private RayXPathParser parser;

    @Before
    public void before() throws Exception {
        builder = new RayXMLConfigBuilder(parser = new RayXPathParser(reader = Resources.getResourceAsReader("batis-config.xml")));
    }

    @Test
    public void defaultAlias_test() throws Exception {
        RayBatisConfiguration configuration = new RayBatisConfiguration();
        Map<String, Class<?>> typeAliasMap = configuration.getTypeAliasRegistry().getTypeAliasMap();
        Assert.assertTrue(MapUtils.isNotEmpty(typeAliasMap));
        Assert.assertEquals(typeAliasMap.get("long"), Long.class);
    }


    @Test
    public void config_alias_test() throws Exception {
        builder.parseAlias(parser.evalNode("/configuration/typeAliases"));

        Map<String, Class<?>> typeAliasMap = builder.getConfiguration().getTypeAliasRegistry().getTypeAliasMap();

        Assert.assertTrue(MapUtils.isNotEmpty(typeAliasMap));
        Assert.assertEquals(typeAliasMap.get("Country"), Country.class);
        Assert.assertEquals(typeAliasMap.get("User"), User.class);
    }


    @Test
    public void config_package_alias_test() throws Exception {
        builder.parseAlias(parser.evalNode("/configuration/typeAliases"));

        Map<String, Class<?>> typeAliasMap = builder.getConfiguration().getTypeAliasRegistry().getTypeAliasMap();

        Assert.assertTrue(MapUtils.isNotEmpty(typeAliasMap));
        Assert.assertEquals(typeAliasMap.get("Country2"), Country2.class);
        Assert.assertEquals(typeAliasMap.get("user2Alias"), User2.class);
    }
}
