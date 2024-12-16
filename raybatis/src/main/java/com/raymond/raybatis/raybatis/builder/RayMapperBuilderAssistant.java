package com.raymond.raybatis.raybatis.builder;

import java.util.List;

import org.apache.ibatis.type.JdbcType;

import com.raymond.raybatis.raybatis.configuration.RayBatisConfiguration;
import com.raymond.raybatis.raybatis.enums.RaySqlCommandType;
import com.raymond.raybatis.raybatis.mapping.RayMappedStatement;
import com.raymond.raybatis.raybatis.mapping.RayResultFlags;
import com.raymond.raybatis.raybatis.mapping.RayResultMap;
import com.raymond.raybatis.raybatis.mapping.RayResultMapping;
import com.raymond.raybatis.raybatis.mapping.RaySimpleSqlSource;

import lombok.Data;

@Data
public class RayMapperBuilderAssistant extends RayBaseBuilder {
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
        statement.setSqlCommandType(commandType);
        return statement;
    }
}
