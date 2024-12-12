package com.raymond.builder;

import java.util.List;

import org.apache.ibatis.type.JdbcType;

import com.raymond.configuration.RayBatisConfiguration;
import com.raymond.mapping.RayResultFlags;
import com.raymond.mapping.RayResultMapping;

import lombok.Data;

@Data
public class RayMapperBuilderAssistant extends BaseBuilder {
    private String namespace;

    private String source;

    public RayMapperBuilderAssistant(RayBatisConfiguration configuration, String source) {
        super(configuration);
        this.source = source;
    }

    public RayResultMapping buildResultMapping(Class<?> resultType, String property, String column,
                                               Class<?> javaTypeClass, JdbcType jdbcTypeEnum,
                                               List<RayResultFlags> flags) {
        RayResultMapping ret = new RayResultMapping();
        ret.setConfiguration(getConfiguration());
        ret.setProperty(property);
        ret.setColumn(column);
        ret.setJdbcType(jdbcTypeEnum);
        ret.setJavaType(javaTypeClass);
        return ret;
    }
}
