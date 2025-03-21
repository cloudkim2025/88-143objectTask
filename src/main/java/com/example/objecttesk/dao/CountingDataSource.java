package com.example.objecttesk.dao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.io.PrintWriter;
import java.util.logging.Logger;

public class CountingDataSource implements DataSource {
    private int counter = 0;
    private DataSource realDataSource;

    public CountingDataSource(DataSource realDataSource) {
        this.realDataSource = realDataSource;
    }

    public int getCounter() {
        return counter;
    }

    @Override
    public Connection getConnection() throws SQLException {
        counter++;
        return realDataSource.getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        counter++;
        return realDataSource.getConnection(username, password);
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return realDataSource.getLogWriter();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        realDataSource.setLogWriter(out);
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        realDataSource.setLoginTimeout(seconds);
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return realDataSource.getLoginTimeout();
    }

    @Override
    public Logger getParentLogger() {
        try {
            return realDataSource.getParentLogger();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return realDataSource.unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return realDataSource.isWrapperFor(iface);
    }
}
