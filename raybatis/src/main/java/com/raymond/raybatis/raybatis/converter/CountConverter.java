package com.raymond.raybatis.raybatis.converter;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.raymond.raybatis.raybatis.dao.Country;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CountConverter implements ResultConverter<Long> {
    @Override
    public Long convert(ResultSet resultSet) {
        log.info("CountryConverter 开始执行转化");
        List<Country> ret = new ArrayList<>();
        try {
            if (resultSet.next()) {
                long count = resultSet.getLong(1);
                return count;
            }
        } catch (Exception e) {
            log.error("转换异常", e);
        }
        return 0L;
    }
}
