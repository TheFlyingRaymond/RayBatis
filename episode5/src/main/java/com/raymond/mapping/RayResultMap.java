package com.raymond.mapping;

import java.util.List;
import java.util.Map;

import com.raymond.configuration.RayBatisConfiguration;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RayResultMap {
    //全局配置
    private RayBatisConfiguration configuration;
    //id
    private String id;
    //最终映射的类型
    private Class<?> type;
    //记录所有的result节点
    private List<RayResultMapping> resultMappings;
    //是否启动自动映射
    private boolean autoMapping;
}
