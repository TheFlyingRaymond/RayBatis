package com.raymond.mybatis.Executor;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.collections4.CollectionUtils;

import com.raymond.mybatis.binding.IRealParam;
import com.raymond.mybatis.mapping.MappedStatement;
import com.raymond.mybatis.script.SimpleSqlSource;
import com.raymond.mybatis.session.Configuration;
import com.raymond.mybatis.type.TypeHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleExecutor implements Executor {
    private DataSource dataSource;

    public SimpleExecutor(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private static void parameterise(IRealParam parameter, SimpleSqlSource sqlSource,
                                     Configuration configuration,
                                     PreparedStatement preparedStatement) throws SQLException {
        int index = 1;
        for (String item : sqlSource.getParameterMappings()) {
            Object param = parameter.getParam(item);
            TypeHandler typeHandler = configuration.getTypeHandlerRegistry().getTypeHandler(param.getClass());
            typeHandler.setParameter(preparedStatement, index, param, null);
            index += 1;
        }
    }

    @Override
    public <E> List<E> query(MappedStatement mappedStatement, IRealParam parameter) {
        try {
            SimpleSqlSource sqlSource = mappedStatement.getSqlSource();
            PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(sqlSource.getSql());
            Configuration configuration = mappedStatement.getConfiguration();
            parameterise(parameter, sqlSource, configuration, preparedStatement);

            List<E> ret = new DefaultResultSetHandler(mappedStatement.getKeyStatementId(), configuration)
                    .handleResultSets(preparedStatement);
            if (CollectionUtils.isEmpty(ret)) {
                return null;
            }
            return ret;
        } catch (Exception e) {
            log.error("execute error", e);
        }
        return null;
    }

    @Override
    public int update(MappedStatement mappedStatement, IRealParam parameter) {
        try {
            SimpleSqlSource sqlSource = mappedStatement.getSqlSource();
            PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(sqlSource.getSql());
            Configuration configuration = mappedStatement.getConfiguration();
            parameterise(parameter, sqlSource, configuration, preparedStatement);
            return preparedStatement.executeUpdate();
        } catch (Exception e) {
            log.error("execute error", e);
        }
        return 0;
    }
}
