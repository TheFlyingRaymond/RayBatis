package com.raymond;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RayMappedStatement {
    private String nodeId;
    private String sql;
    private Class<?> resultType;
    private String resultMap;
    private String namespace;

    public String getKey() {
        return namespace + ":" + nodeId;
    }

    public String getResultMapKey() {
        return namespace + ":" + resultMap;
    }
}
