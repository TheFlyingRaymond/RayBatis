package com.raymond.raybatis.raybatis.builder;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.type.JdbcType;

import com.raymond.raybatis.raybatis.configuration.RayBatisConfiguration;
import com.raymond.raybatis.raybatis.mapping.RayResultFlags;
import com.raymond.raybatis.raybatis.mapping.RayResultMap;
import com.raymond.raybatis.raybatis.mapping.RayResultMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RayResultMapBuilder extends BaseBuilder {
    private String namespace;
    private XNode node;

    public RayResultMapBuilder(RayBatisConfiguration configuration,
                               String namespace, XNode node) {
        super(configuration);
        this.namespace = namespace;
        this.node = node;
    }

    public void parse() {
        try {
            String id = node.getStringAttribute("id");
            Class<?> typeClass = resolveClass(node.getStringAttribute("type"));
            List<RayResultMapping> resultMappings = new ArrayList<>();
            List<XNode> children = node.getChildren();
            for (XNode child : children) {
                List<RayResultFlags> flags = new ArrayList<>();
                if ("id".equals(child.getName())) {
                    flags.add(RayResultFlags.ID);
                }
                resultMappings.add(buildResultMapping(child, typeClass, flags));
            }
            id = namespace + "." + id;
            RayResultMap resultMap = new RayResultMap(configuration, id, typeClass, resultMappings, false);
            configuration.addResultMap(resultMap);
        } catch (Exception e) {
            log.error("parseResultMap error, id:{}", node.getName(), e);
        }
    }

    private RayResultMapping buildResultMapping(XNode child, Class<?> resultType, List<RayResultFlags> flags) {
        String property = child.getStringAttribute("property");
        String column = child.getStringAttribute("column");
        String javaType = child.getStringAttribute("javaType");
        Class<?> javaTypeClass = resolveClass(javaType);
        String jdbcType = child.getStringAttribute("jdbcType");
        JdbcType jdbcTypeEnum = resolveJdbcType(jdbcType);

        RayResultMapping ret = new RayResultMapping();
        ret.setConfiguration(getConfiguration());
        ret.setProperty(property);
        ret.setColumn(column);
        ret.setJdbcType(jdbcTypeEnum);
        ret.setJavaType(javaTypeClass);
        return ret;
    }
}
