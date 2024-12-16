package com.raymond.raybatis;

import java.io.Reader;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.io.Resources;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.raymond.raybatis.raybatis.RayBatisSessionFactory;
import com.raymond.raybatis.raybatis.RayBatisSessionFactoryImpl;
import com.raymond.raybatis.raybatis.builder.RayXMLConfigBuilder;
import com.raymond.raybatis.raybatis.configuration.RayBatisConfiguration;
import com.raymond.raybatis.raybatis.dao.Country;
import com.raymond.raybatis.raybatis.dao.User;
import com.raymond.raybatis.raybatis.mapper.CountryMapper;
import com.raymond.raybatis.raybatis.mapper.UserMapper;
import com.raymond.raybatis.raybatis.parsing.RayXPathParser;

public class RayBatisTest {
    private RayBatisSessionFactory sessionFactory;

    @Before
    public void before() throws Exception {
        Reader reader = Resources.getResourceAsReader("batis-config.xml");

        RayBatisConfiguration configuration = new RayXMLConfigBuilder(new RayXPathParser(reader)).parse();

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


    @Test
    public void testSelectMax_People() throws Exception {
        User People = sessionFactory.openSession().getMapper(UserMapper.class).selectMax();
        System.out.println(People);
        Assert.assertNotNull(People);
    }
}
