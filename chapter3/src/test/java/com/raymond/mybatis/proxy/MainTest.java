package com.raymond.mybatis.proxy;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.parsing.XPathParser;
import org.junit.Assert;
import org.junit.Test;

import com.raymond.mybatis.builder.XMLConfigBuilder;
import com.raymond.mybatis.session.Configuration;
import com.raymond.mybatis.session.DefaultSqlSessionFactory;
import com.raymond.mybatis.testdata.CountryMapper;
import com.raymond.mybatis.testdata.dao.Country;

import lombok.extern.slf4j.Slf4j;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Slf4j
public class MainTest {
    @Test
    public void test() throws Exception {
        Configuration configuration = new XMLConfigBuilder(new XPathParser(Resources.getResourceAsReader("batis-config.xml"))).parse();
        CountryMapper mapper = new DefaultSqlSessionFactory(configuration).openSession().getMapper(CountryMapper.class);

        Country country = mapper.selectById(1L);
        System.out.println(country);
        assertNotNull(country);
        Assert.assertTrue(1L == country.getId());

        Country country2 = mapper.selectById(2L);
        System.out.println(country2);
        assertNotNull(country2);
        Assert.assertTrue(2L == country2.getId());
    }
}
