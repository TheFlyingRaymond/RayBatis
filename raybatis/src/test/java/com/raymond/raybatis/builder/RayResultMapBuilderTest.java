package com.raymond.raybatis.builder;

import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.parsing.XNode;
import org.junit.Assert;
import org.junit.Test;

import com.raymond.raybatis.raybatis.builder.RayResultMapBuilder;
import com.raymond.raybatis.raybatis.configuration.RayBatisConfiguration;
import com.raymond.raybatis.raybatis.dao.Country;
import com.raymond.raybatis.raybatis.mapping.RayResultMap;
import com.raymond.raybatis.raybatis.parsing.RayXPathParser;

public class RayResultMapBuilderTest {

    @Test
    public void test() throws Exception {
        RayXPathParser parser = new RayXPathParser(Resources.getResourceAsReader("mapper/CountryMapper.xml"));
        XNode xNode = parser.evalNode("/mapper/resultMap");

        RayBatisConfiguration configuration = new RayBatisConfiguration();
        String namespace = "namespaceForTest";
        RayResultMapBuilder builder = new RayResultMapBuilder(configuration, namespace, xNode);
        builder.parse();

        Map<String, RayResultMap> resultMaps = builder.getConfiguration().getResultMaps();
        System.out.println(resultMaps.keySet());
        Assert.assertTrue(MapUtils.isNotEmpty(resultMaps));
        String key = "namespaceForTest.resultMap";
        Assert.assertTrue(resultMaps.containsKey(key));
        RayResultMap contryResultMap = resultMaps.get(key);
        Assert.assertTrue(contryResultMap.getType().equals(Country.class));
        resultMaps.values().forEach(x->{
            System.out.println(x);
        });
    }
}
