package com.raymond.builder;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.raymond.configuration.RayBatisConfiguration;
import com.raymond.parsing.RayXPathParser;

public class XMLConfigBuilderTest {
    Reader reader;

    @Before
    public void before() throws IOException {
        reader = Resources.getResourceAsReader("batis-config.xml");
    }

    @Test
    public void test() {
        RayBatisConfiguration config = new RayXMLConfigBuilder(new RayXPathParser(reader)).parse();
        Assert.assertNotNull(config);
    }
}
