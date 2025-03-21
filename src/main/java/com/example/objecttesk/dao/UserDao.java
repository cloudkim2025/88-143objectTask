package com.example.objecttesk.dao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.example.objecttesk.domain.User;

public class UserDao {
    private DataSource dataSource;

    // 수정자 메소드를 통한 DI
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // 사용자 추가
    public void add(User user) throws SQLException {
        Connection c = dataSource.getConnection();
        PreparedStatement ps = c.prepareStatement("INSERT INTO users(id, name, password) VALUES (?, ?, ?)");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());
        ps.executeUpdate();
        ps.close();
        c.close();
    }

    // 사용자 조회 (간단한 예시)
    public User get(String id) throws SQLException {
        Connection c = dataSource.getConnection();
        PreparedStatement ps = c.prepareStatement("SELECT * FROM users WHERE id = ?");
        ps.setString(1, id);
        ResultSet rs = ps.executeQuery();
        User user = null;
        if (rs.next()) {
            user = new User();
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
        }
        rs.close();
        ps.close();
        c.close();
        return user;
    }
}
