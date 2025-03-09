package com.example.objecttesk.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SimpleConnectionMaker {
    private final String url = "jdbc:mysql://localhost:3306/myproject_db";
    private final String user = "root";
    private final String password = "1234";

    public Connection makeNewConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, user, password);
    }
}
