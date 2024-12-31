package com.raymond.mybatis.Executor;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public interface ResultSetHandler {
    <E> List<E> handleResultSets(PreparedStatement stmt) throws SQLException;
}
