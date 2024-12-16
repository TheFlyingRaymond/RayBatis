package com.raymond.raybatis.builder;

import org.junit.Assert;
import org.junit.Test;

import com.raymond.raybatis.RayBaseTest;
import com.raymond.raybatis.raybatis.configuration.RayEnvironment;

public class RayEnvironmentBuilderTest extends RayBaseTest {

    @Test
    public void environments_parse_test() {
        builder.parseEnvironments(parser.evalNode("/configuration/environments"));
        RayEnvironment environment = builder.getConfiguration().getEnvironment();
        System.out.println(environment);
        Assert.assertNotNull(environment);
    }

}
