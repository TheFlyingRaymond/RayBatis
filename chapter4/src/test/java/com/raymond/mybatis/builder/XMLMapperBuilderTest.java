package com.raymond.mybatis.builder;

import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.parsing.XPathParser;
import org.junit.Assert;
import org.junit.Test;

import com.raymond.mybatis.mapping.MappedStatement;
import com.raymond.mybatis.session.Configuration;

public class XMLMapperBuilderTest {
    @Test
    public void parseTest() throws Exception{
        Configuration configuration = new XMLConfigBuilder(new XPathParser(Resources.getResourceAsReader("batis-config.xml"))).parse();
        Map<String, MappedStatement> mappedStatements = configuration.getMappedStatements();
        System.out.println(mappedStatements);
        Assert.assertNotNull(mappedStatements);
    }
}
