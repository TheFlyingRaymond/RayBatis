package com.raymond.configuration;

import java.util.List;

import com.raymond.MapperMethod;
import com.raymond.converter.CountConverter;
import com.raymond.converter.CountryConverter;
import com.raymond.converter.CountryListConverter;
import com.raymond.dao.Country;


public class ConfigurationManager {
    RayBatisConfiguration countryMapConfiguration;

    public ConfigurationManager() {
        countryMapConfiguration = new RayBatisConfiguration();
        MapperMethod selectAll = new MapperMethod("SELECT id, country_name, country_code FROM Country", List.class);
        countryMapConfiguration.getMethodMap().put("selectAll",selectAll );
        countryMapConfiguration.getMethodNameToResultConverter().put("selectAll", new CountryListConverter());

        MapperMethod selectMax = new MapperMethod("select * from country order by id desc limit 1", Country.class);
        countryMapConfiguration.getMethodMap().put("selectMax", selectMax);
        countryMapConfiguration.getMethodNameToResultConverter().put("selectMax", new CountryConverter());

        MapperMethod count = new MapperMethod("select count(*) from country", Long.class);
        countryMapConfiguration.getMethodMap().put("count", count);
        countryMapConfiguration.getMethodNameToResultConverter().put("count", new CountConverter());
    }

    public RayBatisConfiguration getConfiguration() {
        return countryMapConfiguration;
    }
}
