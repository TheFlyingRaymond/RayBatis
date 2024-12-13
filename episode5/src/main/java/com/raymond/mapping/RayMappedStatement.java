package com.raymond.mapping;

import com.raymond.configuration.RayBatisConfiguration;
import com.raymond.enums.RaySqlCommandType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RayMappedStatement {
    private RayBatisConfiguration configuration;
    //mapper文件的路径
    private String source;
    //接口的全类名+方法名, 比如xxx.xxx.xxMapper.selectAll
    private String id;

    private RayBoundSql boundSQL;

    private RayResultMap resultMap;

    private RaySqlCommandType sqlCommandType;

}
