package com.raymond.builder;

import org.apache.ibatis.builder.BuilderException;
import org.apache.ibatis.type.JdbcType;

import com.raymond.configuration.RayBatisConfiguration;
import com.raymond.exception.RayConfigParseException;

import lombok.Data;

@Data
public class BaseBuilder {
    protected RayBatisConfiguration configuration;

    public BaseBuilder() {
    }

    public BaseBuilder(RayBatisConfiguration configuration) {
        this.configuration = configuration;
    }

    public Class<?> resolveClass(String typeName) {
        if(null == typeName || "".equals(typeName.trim())) {
            return null;
        }
        try {
            return Class.forName(typeName);
        } catch (ClassNotFoundException e) {
            throw new RayConfigParseException(e);
        }
    }

    protected JdbcType resolveJdbcType(String alias) {
        if (alias == null) {
            return null;
        }
        try {
            return JdbcType.valueOf(alias);
        } catch (IllegalArgumentException e) {
            throw new BuilderException("Error resolving JdbcType. Cause: " + e, e);
        }
    }
}
