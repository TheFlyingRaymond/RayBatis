package com.raymond.raybatis;

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

public class RayBaseTest {
    protected RayXMLConfigBuilder builder;
    protected Reader reader;
    protected RayXPathParser parser;

    @Before
    public void before() throws Exception {
        builder = new RayXMLConfigBuilder(parser = new RayXPathParser(reader = Resources.getResourceAsReader("batis-config.xml")));
    }
}
