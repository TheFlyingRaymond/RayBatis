package com.raymond;

import com.raymond.configuration.RayBatisConfiguration;

import lombok.Data;

@Data
public class RaySqlSessionManager implements RaySqlSession {
    private RayBatisConfiguration configuration;

    public RaySqlSessionManager(RayBatisConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        return getConfiguration().getMapper(type, this);
    }
}
