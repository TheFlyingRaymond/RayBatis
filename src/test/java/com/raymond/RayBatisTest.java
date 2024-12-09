package com.raymond;

import java.util.List;

import org.junit.Test;

import com.raymond.dao.Country;
import com.raymond.mapper.CountryMapper;

public class RayBatisTest {
    @Test
    public void testSelectAll() throws Exception {
        List<Country> countries = new RayBatisSession().getMapper(CountryMapper.class).selectAll();
        System.out.println(countries);
    }
}
