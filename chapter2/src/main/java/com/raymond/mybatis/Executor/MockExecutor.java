package com.raymond.mybatis.Executor;

import java.sql.ResultSet;

import javax.sql.DataSource;

import com.raymond.mybatis.testdata.dao.Country;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MockExecutor implements Executor {
    private DataSource dataSource;

    public MockExecutor(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Object execute() {
        String sql = "select * from country order by id desc limit 1";
        log.info("从数据库中获取指定信息, 固定sql:{}", sql);
        try {
            ResultSet resultSet = dataSource.getConnection().prepareStatement(sql).executeQuery();
            if (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String countryName = resultSet.getString("country_name");
                String countryCode = resultSet.getString("country_code");
                return new Country(id, countryName, countryCode);
            }

        } catch (Exception e) {
            log.error("execute error", e);
        }
        return null;
    }
}
