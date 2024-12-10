package com.raymond;

import com.raymond.configuration.RayBatisConfiguration;

import lombok.Data;

@Data
public class RayBatisSessionFactoryImpl implements RayBatisSessionFactory {
    private RayBatisConfiguration configuration;

    public RayBatisSessionFactoryImpl(RayBatisConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public RayBatisSession openSession() {
        return new RayBatisSession(configuration);
    }
}
