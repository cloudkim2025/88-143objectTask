package com.example.objecttesk.dao;

import com.example.objecttesk.domain.User;
import java.sql.*;

public class UserDao {
    private final String url = "jdbc:mysql://localhost:3306/myproject_db";
    private final String userName = "root";
    private final String password = "1234";

    // 🔹 중복된 DB 연결 코드 제거
    private Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, userName, password);
    }

    public void add(User user) throws ClassNotFoundException, SQLException {
        Connection c = getConnection();  //  중복 제거: getConnection() 사용
        PreparedStatement ps = c.prepareStatement("INSERT INTO users(id, name, password) VALUES (?, ?, ?)");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();
        ps.close();
        c.close();
    }

    public User get(String id) throws ClassNotFoundException, SQLException {
        Connection c = getConnection();  // 중복 제거: getConnection() 사용
        PreparedStatement ps = c.prepareStatement("SELECT * FROM users WHERE id = ?");
        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();
        rs.next();

        User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));

        rs.close();
        ps.close();
        c.close();

        return user;
    }
}
