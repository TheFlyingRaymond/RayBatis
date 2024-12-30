package com.raymond.mybatis.testdata;


import com.raymond.mybatis.annotation.Param;
import com.raymond.mybatis.testdata.dao.Country;

public interface CountryMapper {
    Country selectById(@Param("id") Long id);

    Country selectByName(@Param("name") String name);

    Country selectByIdAndName(@Param("id") Long id, @Param("name") String name);
}
