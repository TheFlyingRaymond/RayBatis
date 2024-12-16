package com.raymond.raybatis.raybatis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import com.raymond.raybatis.raybatis.configuration.RayBatisConfiguration;
import com.raymond.raybatis.raybatis.mapping.RayMappedStatement;
import com.raymond.raybatis.raybatis.mapping.RayResultMap;
import com.raymond.raybatis.raybatis.mapping.RayResultMapping;
import com.raymond.raybatis.raybatis.reflection.RayReflector;
import com.raymond.raybatis.raybatis.reflection.RayReflectorFactory;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import sun.reflect.misc.ReflectUtil;

@Data
@Slf4j
public class RayDefaultSession implements RaySqlSession {
    private RayBatisConfiguration configuration;

    public RayDefaultSession(RayBatisConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <T> T selectOne(String statementId) {
        return selectOne(statementId, null);
    }

    @Override
    public <T> T selectOne(String statementId, Object parameter) {
        RayMappedStatement statement = configuration.getStatementMap().get(statementId);
        if (statement == null) {
            log.error("statement not found");
            return null;
        }

        String sql = statement.getBoundSQL().getSql();

        try {
            Connection connection = configuration.getEnvironment().getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<T> ret = handle(resultSet, statement);
            if(ret.size() > 1){
                throw new RuntimeException("selectOne result size > 1");
            }

            return ret.get(0);
        } catch (Exception e) {
            log.error("selectOne error", e);
            throw new RuntimeException(e);
        }
    }


    private <T> List<T> handle(ResultSet resultSet, RayMappedStatement statement) throws Exception {
        RayResultMap resultMap = statement.getResultMap();

        List<T> ret = new ArrayList<>();
        while (resultSet.next()) {
            RayReflector reflector = RayReflectorFactory.getReflector(resultMap.getType());
            Object obj = reflector.getDefaultConstructor().newInstance();

            for (RayResultMapping mapping : resultMap.getResultMappings()) {
                String columnName = mapping.getColumn();
                String propertyName = mapping.getProperty();
                Object value = resultSet.getObject(columnName);

                reflector.getSetMethods().get(propertyName).invoke(obj, new Object[]{value});
            }


            ret.add((T) obj);
        }
        return ret;
    }

    @Override
    public <E> List<E> selectList(String statementId) {
        return selectList(statementId, null);
    }

    @Override
    public <E> List<E> selectList(String statementId, Object parameter) {
        RayMappedStatement statement = configuration.getStatementMap().get(statementId);
        if (statement == null) {
            log.error("statement not found");
            return null;
        }

        String sql = statement.getBoundSQL().getSql();

        try {
            Connection connection = configuration.getEnvironment().getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            return handle(resultSet, statement);
        } catch (Exception e) {
            log.error("selectOne error", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public <E> List<E> selectList(String statement, Object parameter, RowBounds rowBounds) {
        return Collections.emptyList();
    }

    @Override
    public <K, V> Map<K, V> selectMap(String statement, String mapKey) {
        return Collections.emptyMap();
    }

    @Override
    public <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey) {
        return Collections.emptyMap();
    }

    @Override
    public <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey, RowBounds rowBounds) {
        return Collections.emptyMap();
    }

    @Override
    public <T> Cursor<T> selectCursor(String statement) {
        return null;
    }

    @Override
    public <T> Cursor<T> selectCursor(String statement, Object parameter) {
        return null;
    }

    @Override
    public <T> Cursor<T> selectCursor(String statement, Object parameter, RowBounds rowBounds) {
        return null;
    }

    @Override
    public void select(String statement, Object parameter, ResultHandler handler) {

    }

    @Override
    public void select(String statement, ResultHandler handler) {

    }

    @Override
    public void select(String statement, Object parameter, RowBounds rowBounds, ResultHandler handler) {

    }

    @Override
    public int insert(String statement) {
        return 0;
    }

    @Override
    public int insert(String statement, Object parameter) {
        return 0;
    }

    @Override
    public int update(String statement) {
        return 0;
    }

    @Override
    public int update(String statement, Object parameter) {
        return 0;
    }

    @Override
    public int delete(String statement) {
        return 0;
    }

    @Override
    public int delete(String statement, Object parameter) {
        return 0;
    }

    @Override
    public void commit() {

    }

    @Override
    public void commit(boolean force) {

    }

    @Override
    public void rollback() {

    }

    @Override
    public void rollback(boolean force) {

    }

    @Override
    public List<BatchResult> flushStatements() {
        return Collections.emptyList();
    }

    @Override
    public void close() {

    }

    @Override
    public void clearCache() {

    }

    @Override
    public <T> T getMapper(Class<T> type) {
        return getConfiguration().getMapper(type, this);
    }

    @Override
    public Connection getConnection() {
        return null;
    }
}
