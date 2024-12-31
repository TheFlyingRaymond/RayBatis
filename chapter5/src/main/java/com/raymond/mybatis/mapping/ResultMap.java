package com.raymond.mybatis.mapping;

import java.util.List;
import java.util.Set;

import com.raymond.mybatis.session.Configuration;

import lombok.Data;

@Data
public class ResultMap {
    // 全局配置信息
    private Configuration configuration;
    // resultMap的编号
    private String id;
    // 最终输出结果对应的Java类
    private Class<?> type;
    // XML中的<result>的列表，即ResultMapping列表
    private List<ResultMapping> resultMappings;
}
