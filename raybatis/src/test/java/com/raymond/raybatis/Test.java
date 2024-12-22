package com.raymond.raybatis;

import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.raymond.raybatis.raybatis.dao.Country;
import com.raymond.raybatis.raybatis.mapper.CountryMapper;

public class Test {
    @org.junit.Test
    public void test() throws Exception {
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsReader("batis-config.xml"));
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            CountryMapper mapper = sqlSession.getMapper(CountryMapper.class);
            List<Country> countries = mapper.selectAll();
            countries.forEach(System.out::println);
        } finally {
            sqlSession.close();
        }
    }
}
