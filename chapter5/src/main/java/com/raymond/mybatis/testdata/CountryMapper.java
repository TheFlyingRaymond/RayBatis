package com.raymond.mybatis.testdata;


import java.util.List;

import com.raymond.mybatis.annotation.Param;
import com.raymond.mybatis.testdata.dao.Country;

public interface CountryMapper {
    Country selectById(@Param("id") Long id);

    int deleteById(@Param("id") Long id);

    List<Country> selectByName(@Param("name") String name);

    int updateNameById(@Param("id") Long id, @Param("name") String name);

    int insertCountry(Country country);

    Country selectByIdAndName(@Param("id") Long id, @Param("name") String name);

    List<Country> selectByExample(Country country);
}
