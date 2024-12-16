package com.raymond.raybatis.type;

import java.io.Reader;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.ibatis.io.Resources;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.raymond.raybatis.raybatis.builder.RayXMLConfigBuilder;
import com.raymond.raybatis.raybatis.configuration.RayBatisConfiguration;
import com.raymond.raybatis.raybatis.parsing.RayXPathParser;

public class RayTypeAliasRegistryTest {
    private RayXMLConfigBuilder builder;

    @Before
    public void before() throws Exception {
        Reader reader = Resources.getResourceAsReader("batis-config.xml");
        builder = new RayXMLConfigBuilder(new RayXPathParser(reader));
    }

    @Test
    public void defaultAlias_test() throws Exception {
        RayBatisConfiguration configuration = new RayBatisConfiguration();
        Map<String, Class<?>> typeAliasMap = configuration.getTypeAliasRegistry().getTypeAliasMap();
        Assert.assertTrue(MapUtils.isNotEmpty(typeAliasMap));
        Assert.assertEquals(typeAliasMap.get("long"), Long.class);
    }
}
