package com.raymond.mybatis.testdata;

import org.apache.ibatis.annotations.Param;

import com.raymond.mybatis.testdata.dao.Country;

public interface CountryMapper {
    Country selectTestCountry();

    Country selectById(@Param("id") Long id);
}
