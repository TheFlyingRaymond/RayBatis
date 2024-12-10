package com.raymond.configuration;

import java.util.HashMap;
import java.util.Map;

import com.raymond.converter.ResultConverter;

import lombok.Data;

@Data
public class RayBatisConfiguration { // 数据库连接信息
    private String url = "jdbc:mysql://localhost:3306/raybatis"; // 替换为你的数据库URL
    private String user = "root"; // 替换为你的数据库用户名
    private String password = ""; // 替换为你的数据库密码

    private Map<String, String> methodNameToSql = new HashMap<>();

    private Map<String, ResultConverter> methodNameToResultConverter = new HashMap<>();


}
