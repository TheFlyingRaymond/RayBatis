package com.raymond.configuration;

import java.util.HashMap;
import java.util.Map;

import com.raymond.converter.CountryConverter;
import com.raymond.converter.CountryListConverter;
import com.raymond.mapper.CountryMapper;

public class ConfigurationManager {
    private Map<Class, RayBatisConfiguration> configurationMap = new HashMap<>();

    public ConfigurationManager() {
        RayBatisConfiguration countryMapConfiguration = new RayBatisConfiguration();
        countryMapConfiguration.getMethodNameToSql().put("selectAll", "SELECT id, country_name, country_code FROM Country");
        countryMapConfiguration.getMethodNameToResultConverter().put("selectAll", new CountryListConverter());

        countryMapConfiguration.getMethodNameToSql().put("selectMax", "select * from country order by id desc limit 1");
        countryMapConfiguration.getMethodNameToResultConverter().put("selectMax", new CountryConverter());

        configurationMap.put(CountryMapper.class, countryMapConfiguration);
    }


    public RayBatisConfiguration getConfiguration(Class mapperClass) {
        return configurationMap.get(mapperClass);
    }
}
