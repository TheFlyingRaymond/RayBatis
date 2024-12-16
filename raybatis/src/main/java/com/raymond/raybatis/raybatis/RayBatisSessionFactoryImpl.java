package com.raymond.raybatis.raybatis;

import com.raymond.raybatis.raybatis.configuration.RayBatisConfiguration;

import lombok.Data;

@Data
public class RayBatisSessionFactoryImpl implements RayBatisSessionFactory {
    private RayBatisConfiguration configuration;

    public RayBatisSessionFactoryImpl(RayBatisConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public RaySqlSession openSession() {
        return new RayDefaultSession(configuration);
    }
}
