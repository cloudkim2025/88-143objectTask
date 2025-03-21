package com.example.objecttesk.dao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.PrintWriter;
import java.util.logging.Logger;

public class DDataSource implements DataSource {
    @Override
    public Connection getConnection() throws SQLException {
        try {
            String url = "jdbc:mysql://localhost:3306/myproject_db";
            String user = "root";
            String password = "1234";
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver not found", e);
        }
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return getConnection();
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Logger getParentLogger() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throw new UnsupportedOperationException("Not implemented");
    }
}
