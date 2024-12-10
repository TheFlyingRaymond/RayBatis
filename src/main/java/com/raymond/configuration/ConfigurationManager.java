package com.raymond.configuration;

import com.raymond.converter.CountryConverter;
import com.raymond.converter.CountryListConverter;

public class ConfigurationManager {
    RayBatisConfiguration countryMapConfiguration;

    public ConfigurationManager() {
        countryMapConfiguration = new RayBatisConfiguration();
        countryMapConfiguration.getMethodNameToSql().put("selectAll", "SELECT id, country_name, country_code FROM Country");
        countryMapConfiguration.getMethodNameToResultConverter().put("selectAll", new CountryListConverter());

        countryMapConfiguration.getMethodNameToSql().put("selectMax", "select * from country order by id desc limit 1");
        countryMapConfiguration.getMethodNameToResultConverter().put("selectMax", new CountryConverter());
    }

    public RayBatisConfiguration getConfiguration() {
        return countryMapConfiguration;
    }
}
