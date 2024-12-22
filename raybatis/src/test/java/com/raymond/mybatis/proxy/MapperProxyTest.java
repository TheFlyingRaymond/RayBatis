package com.raymond.mybatis.proxy;

import org.junit.Assert;
import org.junit.Test;

import com.raymond.mybatis.session.MockSqlSessionFactory;
import com.raymond.mybatis.testdata.CountryMapper;
import com.raymond.mybatis.testdata.dao.Country;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MapperProxyTest {
    @Test
    public void test() {
        CountryMapper mapper = new MockSqlSessionFactory().openSession().getMapper(CountryMapper.class);
        Country country = mapper.selectTestCountry();
        log.info("country:{}", country);
        Assert.assertNotNull(country);
    }
}
