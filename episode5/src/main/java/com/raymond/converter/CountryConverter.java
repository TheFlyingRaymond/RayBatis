package com.raymond.converter;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.raymond.dao.Country;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CountryConverter implements ResultConverter<Country> {
    @Override
    public Country convert(ResultSet resultSet) {
        log.info("CountryConverter 开始执行转化");
        List<Country> ret = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String countryName = resultSet.getString("country_name");
                String countryCode = resultSet.getString("country_code");
                ret.add(new Country(id, countryName, countryCode));
                System.out.println("ID: " + id + ", Country Name: " + countryName + ", Country Code: " + countryCode);
            }
        } catch (Exception e) {
            log.error("转换异常", e);
        }
        if(ret.size() > 0) {
            return ret.get(0);
        }
        return null;
    }
}
