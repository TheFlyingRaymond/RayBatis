package com.raymond;

import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import com.raymond.dao.Country;
import com.raymond.mapper.CountryMapper;

public class MainTest {
    private static SqlSessionFactory sqlSessionFactory;

    @Before
    public  void init(){
        try {
            Reader reader = Resources.getResourceAsReader("batis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testSelectAll_mapper(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            CountryMapper mapper = sqlSession.getMapper(CountryMapper.class);
            List<Country> countries = mapper.selectAll();
            countries.forEach(System.out::println);
        }finally {
            sqlSession.close();
        }
    }

    @Test
    public void testSelectAll(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            List<Country> countryList = sqlSession.selectList("selectAll");
            System.out.println(countryList);
        }finally {
            sqlSession.close();
        }
    }
}
