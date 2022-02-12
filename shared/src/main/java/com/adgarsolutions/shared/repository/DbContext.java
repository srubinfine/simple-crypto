package com.adgarsolutions.shared.repository;

import java.io.Closeable;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface DbContext extends Closeable {
    PreparedStatement getPreparedStatement(String query, Object... params) throws SQLException;
    <T> PreparedStatement getPreparedStatementIterable(String query, Iterable<T> iterable) throws SQLException;
}
