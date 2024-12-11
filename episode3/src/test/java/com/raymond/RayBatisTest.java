package com.raymond;

import java.io.Reader;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.io.Resources;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.raymond.builder.RayXMLConfigBuilder;
import com.raymond.configuration.ConfigurationManager;
import com.raymond.configuration.RayBatisConfiguration;
import com.raymond.dao.Country;
import com.raymond.mapper.CountryMapper;
import com.raymond.parsing.RayXPathParser;

public class RayBatisTest {
    private RayBatisSessionFactory sessionFactory;

    @Before
    public void before() throws Exception {
        Reader reader = Resources.getResourceAsReader("batis-config.xml");

        RayBatisConfiguration configuration = new RayXMLConfigBuilder(new RayXPathParser(reader)).parse();

        ConfigurationManager tempManager = new ConfigurationManager();
        configuration.setMethodMap(tempManager.getConfiguration().getMethodMap());
        configuration.setMethodNameToResultConverter(tempManager.getConfiguration().getMethodNameToResultConverter());

        sessionFactory = new RayBatisSessionFactoryImpl(configuration);
    }

    @Test
    public void testSelectAll() throws Exception {
        List<Country> countries = sessionFactory.openSession().getMapper(CountryMapper.class).selectAll();
        System.out.println(countries);
        Assert.assertTrue(CollectionUtils.isNotEmpty(countries));
    }

    @Test
    public void testSelectMax() throws Exception {
        Country country = sessionFactory.openSession().getMapper(CountryMapper.class).selectMax();
        System.out.println(country);
        Assert.assertNotNull(country);
    }

    @Test
    public void testCount() throws Exception {
        Long result = sessionFactory.openSession().getMapper(CountryMapper.class).count();
        System.out.println(result);
        Assert.assertNotNull(result);
    }
}
