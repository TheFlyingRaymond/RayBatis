package com.raymond.mybatis.Executor;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.raymond.mybatis.mapping.MappedStatement;
import com.raymond.mybatis.mapping.ResultMap;
import com.raymond.mybatis.mapping.ResultMapping;
import com.raymond.mybatis.session.Configuration;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DefaultResultSetHandler implements ResultSetHandler {
    private String statementId;
    private Configuration configuration;

    @Override
    public <E> List<E> handleResultSets(PreparedStatement stmt) throws SQLException {
        MappedStatement mappedStatement = configuration.getMappedStatement(statementId);
        ResultMap resultMap = mappedStatement.getResultMap();
        ResultSet resultSet = stmt.executeQuery();

        List<E> ret = new ArrayList<>();
        while (resultSet.next()) {
            handle(resultSet, resultMap, ret);
        }
        return ret;
    }

    private <E> void handle(ResultSet resultSet, ResultMap resultMap, List<E> ret) {
        try {
            Class<?> type = resultMap.getType();
            Object item = type.newInstance();
            for (ResultMapping resultMapping : resultMap.getResultMappings()) {
                String column = resultMapping.getColumn();
                String property = resultMapping.getProperty();
                Field field = type.getDeclaredField(property);
                field.setAccessible(true);
                field.set(item, resultSet.getObject(column));
            }
            ret.add((E) item);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
