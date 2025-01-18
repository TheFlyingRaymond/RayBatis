package com.raymond.mybatis.binding;

import java.util.Map;

public class MapRealParam implements IRealParam {
    private Map<String, Object> paramMap;

    public MapRealParam(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    @Override
    public Object getParam(String name) {
        return paramMap.get(name);
    }
}
