package com.raymond.raybatis.builder;

import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.parsing.XNode;
import org.junit.Assert;
import org.junit.Test;

import com.raymond.raybatis.RayBaseTest;
import com.raymond.raybatis.raybatis.builder.RayXMLStatementBuilder;
import com.raymond.raybatis.raybatis.configuration.RayBatisConfiguration;
import com.raymond.raybatis.raybatis.mapping.RayMappedStatement;
import com.raymond.raybatis.raybatis.parsing.RayXPathParser;

public class RayXMLStatementBuilderTest extends RayBaseTest {

    @Test
    public void simple_test() throws Exception {
        RayXPathParser parser = new RayXPathParser(Resources.getResourceAsReader("mapper/CountryMapper.xml"));
        XNode xNode = parser.evalNode("/mapper/select");

        RayBatisConfiguration configuration = new RayBatisConfiguration();
        String namespace = "namespaceForTest";
        RayXMLStatementBuilder builder = new RayXMLStatementBuilder(configuration, namespace, xNode);
        builder.parse();

        Map<String, RayMappedStatement> statementMap = builder.getConfiguration().getStatementMap();
        System.out.println(statementMap.keySet());
        Assert.assertTrue(MapUtils.isNotEmpty(statementMap));
        String key = "namespaceForTest.selectAll";
        Assert.assertTrue(statementMap.containsKey(key));
        RayMappedStatement statement = statementMap.get(key);
        Assert.assertNotNull(statement);
        System.out.println(statement);
    }
}
