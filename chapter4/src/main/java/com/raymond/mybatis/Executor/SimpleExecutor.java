package com.raymond.mybatis.Executor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.collections4.CollectionUtils;

import com.raymond.mybatis.mapping.MappedStatement;
import com.raymond.mybatis.script.SimpleSqlSource;
import com.raymond.mybatis.session.Configuration;
import com.raymond.mybatis.testdata.dao.Country;
import com.raymond.mybatis.type.TypeHandler;

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
            Configuration configuration = mappedStatement.getConfiguration();
            parameterise(parameter, sqlSource, configuration, preparedStatement);

            List<T> ret = new DefaultResultSetHandler(mappedStatement.getKeyStatementId(), configuration)
                    .handleResultSets(preparedStatement);
            if (CollectionUtils.isEmpty(ret)) {
                return null;
            }
            return ret.get(0);
        } catch (Exception e) {
            log.error("execute error", e);
        }
        return null;
    }

    private static void parameterise(Map<String, Object> parameter, SimpleSqlSource sqlSource,
                                     Configuration configuration,
                                     PreparedStatement preparedStatement) throws SQLException {
        int index = 1;
        for (String item : sqlSource.getParameterMappings()) {
            Object param = parameter.get(item);
            TypeHandler typeHandler = configuration.getTypeHandlerRegistry().getTypeHandler(param.getClass());
            typeHandler.setParameter(preparedStatement, index, param, null);
            index += 1;
        }
    }
}
