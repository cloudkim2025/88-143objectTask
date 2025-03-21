package com.example.objecttesk.dao;

import java.sql.Connection;
import java.sql.SQLException;

public interface DataSource {
    // DB 연결을 가져오는 메소드
    Connection getConnection() throws SQLException;

    // (필요시) 사용자명과 비밀번호를 받아 연결을 가져오는 메소드
    Connection getConnection(String username, String password) throws SQLException;
}
