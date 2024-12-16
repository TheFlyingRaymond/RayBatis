package com.raymond.raybatis.builder;

import org.junit.Assert;

import com.raymond.raybatis.RayBaseTest;
import com.raymond.raybatis.raybatis.configuration.RayEnvironment;

public class RayXMLMapperBuilderTest extends RayBaseTest {

    public void test(){
        builder.parseMappers(parser.evalNode("/configuration/mappers"));
        RayEnvironment environment = builder.getConfiguration().getEnvironment();
        System.out.println(environment);
        Assert.assertNotNull(environment);
    }
}
