package com.raymond.raybatis.raybatis;

import java.sql.Connection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import com.raymond.raybatis.raybatis.configuration.RayBatisConfiguration;

import lombok.Data;

@Data
public class RayDefaultSession implements RaySqlSession {
    private RayBatisConfiguration configuration;

    public RayDefaultSession(RayBatisConfiguration configuration) {
        this.configuration = configuration;

    }

    @Override
    public <T> T selectOne(String statement) {
        return null;
    }

    @Override
    public <T> T selectOne(String statement, Object parameter) {
        return null;
    }

    @Override
    public <E> List<E> selectList(String statement) {
        return Collections.emptyList();
    }

    @Override
    public <E> List<E> selectList(String statement, Object parameter) {
        return Collections.emptyList();
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
