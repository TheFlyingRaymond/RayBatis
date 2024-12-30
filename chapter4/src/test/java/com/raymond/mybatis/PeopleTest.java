package com.raymond.mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.parsing.XPathParser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.raymond.mybatis.builder.XMLConfigBuilder;
import com.raymond.mybatis.session.Configuration;
import com.raymond.mybatis.session.DefaultSqlSessionFactory;
import com.raymond.mybatis.testdata.PeopleMapper;
import com.raymond.mybatis.testdata.dao.People;

import static org.junit.Assert.assertNotNull;

public class PeopleTest {
    Configuration configuration;
    PeopleMapper mapper;

    @Before
    public void before() throws Exception {
        configuration =
                new XMLConfigBuilder(new XPathParser(Resources.getResourceAsReader("batis-config.xml"))).parse();
        mapper = new DefaultSqlSessionFactory(configuration).openSession().getMapper(PeopleMapper.class);
    }

    @Test
    public void test() throws Exception {
        People people = mapper.selectById(1L);
        System.out.println(people);
        assertNotNull(people);
        Assert.assertTrue(1L == people.getId());
    }
}
