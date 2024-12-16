package com.raymond.raybatis.raybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.raymond.raybatis.raybatis.dao.Country;

public interface CountryMapper {
    List<Country> selectAll();

    Country selectMax();

    Country selectById(@Param("id") Long id);

    Long count();
}
