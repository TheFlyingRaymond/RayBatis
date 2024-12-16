package com.raymond.raybatis.raybatis.builder;

import org.apache.ibatis.builder.BuilderException;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeAliasRegistry;

import com.raymond.raybatis.raybatis.configuration.RayBatisConfiguration;
import com.raymond.raybatis.raybatis.type.RayTypeAliasRegistry;

import lombok.Data;

@Data
public class BaseBuilder {
    protected RayBatisConfiguration configuration;
    protected RayTypeAliasRegistry typeAliasRegistry;

    public BaseBuilder() {
    }

    public BaseBuilder(RayBatisConfiguration configuration) {
        this.configuration = configuration;
        this.typeAliasRegistry = this.configuration.getTypeAliasRegistry();
    }

    public Class<?> resolveClass(String typeName) {
        if(null == typeName || "".equals(typeName.trim())) {
            return null;
        }
        RayTypeAliasRegistry typeAliasRegistry1 = getTypeAliasRegistry();
        return typeAliasRegistry1.resolveAlias(typeName);
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
