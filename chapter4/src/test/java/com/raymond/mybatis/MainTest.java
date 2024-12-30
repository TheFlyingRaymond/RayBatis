package com.raymond.mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.parsing.XPathParser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.raymond.mybatis.builder.XMLConfigBuilder;
import com.raymond.mybatis.session.Configuration;
import com.raymond.mybatis.session.DefaultSqlSessionFactory;
import com.raymond.mybatis.testdata.CountryMapper;
import com.raymond.mybatis.testdata.dao.Country;

import lombok.extern.slf4j.Slf4j;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@Slf4j
public class MainTest {
    Configuration configuration;
    CountryMapper mapper;

    @Before
    public void before() throws Exception {
        configuration =
                new XMLConfigBuilder(new XPathParser(Resources.getResourceAsReader("batis-config.xml"))).parse();
        mapper = new DefaultSqlSessionFactory(configuration).openSession().getMapper(CountryMapper.class);
    }

    @Test
    public void test() throws Exception {
        Country country = mapper.selectById(1L);
        System.out.println(country);
        assertNotNull(country);
        Assert.assertTrue(1L == country.getId());

        Country country2 = mapper.selectById(2L);
        System.out.println(country2);
        assertNotNull(country2);
        Assert.assertTrue(2L == country2.getId());
    }

    @Test
    public void test_select_name() throws Exception {
        Country country = mapper.selectByName("Canada");
        System.out.println(country);
        assertNotNull(country);
        Assert.assertTrue(2L == country.getId());

        Country country2 = mapper.selectByName("United States");
        System.out.println(country2);
        assertNotNull(country2);
        Assert.assertTrue(1L == country2.getId());
    }

    @Test
    public void test_select_id_name() throws Exception {
        Country country = mapper.selectByIdAndName(1L,"Canada");
        System.out.println(country);
        assertNull(country);

        Country country2 = mapper.selectByIdAndName(2L,"Canada");
        System.out.println(country2);
        assertNotNull(country2);
        Assert.assertTrue(2L == country2.getId());
    }
}
