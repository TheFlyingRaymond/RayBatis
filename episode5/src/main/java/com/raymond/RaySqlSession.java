package com.raymond;

import com.raymond.configuration.RayBatisConfiguration;

public interface RaySqlSession {
    <T> T getMapper(Class<T> type);

    RayBatisConfiguration getConfiguration();

}
