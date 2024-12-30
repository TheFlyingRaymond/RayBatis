package com.raymond.mybatis.Executor;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public interface ResultSetHandler {
    <E> List<E> handleResultSets(PreparedStatement stmt) throws SQLException;
}
