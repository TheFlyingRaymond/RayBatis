package com.raymond.mybatis.Executor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;

import javax.sql.DataSource;

import com.raymond.mybatis.mapping.MappedStatement;
import com.raymond.mybatis.script.SimpleSqlSource;
import com.raymond.mybatis.testdata.dao.Country;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleExecutor implements Executor {
    private DataSource dataSource;

    public SimpleExecutor(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public <T> T query(MappedStatement mappedStatement, Map<String, Object> parameter) {
        try {
            SimpleSqlSource sqlSource = mappedStatement.getSqlSource();
            PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(sqlSource.getSql());
            int index = 1;
            for (String item : sqlSource.getParameterMappings()) {
                preparedStatement.setLong(index, (Long) parameter.get(item));
                index += 1;
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String countryName = resultSet.getString("country_name");
                String countryCode = resultSet.getString("country_code");
                return (T) new Country(id, countryName, countryCode);
            }
        } catch (Exception e) {
            log.error("execute error", e);
        }
        return null;
    }
}
