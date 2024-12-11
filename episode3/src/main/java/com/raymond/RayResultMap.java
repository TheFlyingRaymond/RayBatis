package com.raymond;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RayResultMap {
    private String nodeId;
    private Class<?> type;
    private Map<String, String> columnsMappings;
    private String namespace;

    public String getKey() {
        return namespace + ":" + nodeId;
    }
}
