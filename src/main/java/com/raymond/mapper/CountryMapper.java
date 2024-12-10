package com.raymond.mapper;

import java.util.List;

import com.raymond.dao.Country;

public interface CountryMapper {
    List<Country> selectAll();

    Country selectMax();
}
