package com.raymond;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.raymond.configuration.ConfigurationManager;
import com.raymond.dao.Country;
import com.raymond.mapper.CountryMapper;

public class RayBatisTest {
    private RayBatisSessionFactory sessionFactory;

    @Before
    public void before(){
        sessionFactory = new RayBatisSessionFactoryImpl(new ConfigurationManager().getConfiguration());
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
