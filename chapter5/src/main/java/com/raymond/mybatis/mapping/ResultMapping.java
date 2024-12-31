package com.raymond.mybatis.mapping;

import org.apache.ibatis.type.JdbcType;

import com.raymond.mybatis.session.Configuration;
import com.raymond.mybatis.type.TypeHandler;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ResultMapping {
    private Configuration configuration;
    private String property;
    private String column;
}
