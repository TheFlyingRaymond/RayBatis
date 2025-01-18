package com.raymond.mybatis.Executor;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

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
            PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(sqlSource.getSql(), Statement.RETURN_GENERATED_KEYS);
            Configuration configuration = mappedStatement.getConfiguration();
            parameterise(parameter, sqlSource, configuration, preparedStatement);
            int cnt = preparedStatement.executeUpdate();

            writeBackPK(preparedStatement, parameter);

            return cnt;
        } catch (Exception e) {
            log.error("execute error", e);
        }
        return 0;
    }

    private void writeBackPK(PreparedStatement statement, IRealParam parameter) {
        try {
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Object id = generatedKeys.getObject(1);
                if (id instanceof BigInteger) {
                    parameter.setParam("id", ((BigInteger) id).longValue());
                }
            }
        } catch (Exception e) {
            log.error("handleKey error", e);
            throw new RuntimeException(e);
        }
    }
}
