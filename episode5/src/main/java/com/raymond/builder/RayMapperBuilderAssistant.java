package com.raymond.builder;

import java.util.List;

import org.apache.ibatis.type.JdbcType;

import com.raymond.configuration.RayBatisConfiguration;
import com.raymond.enums.RaySqlCommandType;
import com.raymond.mapping.RayMappedStatement;
import com.raymond.mapping.RayResultFlags;
import com.raymond.mapping.RayResultMap;
import com.raymond.mapping.RayResultMapping;
import com.raymond.mapping.RaySimpleSqlSource;

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

    public RayMappedStatement addMappedStatement(RayBatisConfiguration configuration, String id, RaySimpleSqlSource sqlSource, RaySqlCommandType commandType,
                                                 String resultMap, Class<?> resultTypeClass) {
        RayMappedStatement statement = new RayMappedStatement();
        statement.setConfiguration(configuration);
        statement.setId(namespace+ "." + id);
        RayResultMap rayResultMap = configuration.getResultMaps().get(namespace + "." + resultMap);
        statement.setResultMap(rayResultMap);
        return statement;
    }
}
