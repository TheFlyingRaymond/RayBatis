package com.raymond.mybatis;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.parsing.XPathParser;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.raymond.mybatis.testdata.CountryMapper;
import com.raymond.mybatis.testdata.dao.Country;

import lombok.extern.slf4j.Slf4j;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@Slf4j
public class MyBatisTest {
    Configuration configuration;
    CountryMapper mapper;

    @Before
    public void before() throws Exception {
        configuration =
                new XMLConfigBuilder(Resources.getResourceAsReader("batis-config.xml")).parse();
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
    public void test_select_id_name() throws Exception {
        Country country = mapper.selectByIdAndName(1L, "Canada");
        System.out.println(country);
        assertNull(country);

        Country country2 = mapper.selectByIdAndName(2L, "Canada");
        System.out.println(country2);
        assertNotNull(country2);
        Assert.assertTrue(2L == country2.getId());
    }

    @Test
    public void test_select_name_list() throws Exception {
        List<Country> countries = mapper.selectByName("Germany");
        Assert.assertTrue(CollectionUtils.isNotEmpty(countries));
        countries.forEach(System.out::println);
    }

    @Test
    public void test_delete_id() throws Exception {
        int cnt = mapper.deleteById(1L);
        Assert.assertTrue(1 == cnt);
    }


    @Test
    public void test_update() throws Exception {
        int cnt = mapper.updateNameById(1L, "nothing");
        Assert.assertTrue(0 == cnt);

        int cnt2 = mapper.updateNameById(2L, "changeName2");
        Assert.assertTrue(1 == cnt2);

        Country country = mapper.selectById(2L);
        Assert.assertTrue("changeName2".equals(country.getCountryName()));
    }

    @Test
    public void test_insert2() throws Exception {
        Country country = new Country();
        country.setCountryCode("test_insert2_code");
        country.setCountryName("test_insert2_name");
        int cnt = mapper.insertCountry(country);

        Assert.assertTrue(1 == cnt);
        Assert.assertNotNull(country.getId());
        System.out.println(country);
    }
}
