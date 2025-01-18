package com.raymond.mybatis.binding;

public interface IRealParam {
    Object getParam(String name);

    void setParam(String name, Object value);
}
