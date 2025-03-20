package com.example.objecttesk.dao;

import java.sql.Connection;
import java.sql.SQLException;

// ConnectionMaker 인터페이스를 구현한다.
public class CountingConnectionMaker implements ConnectionMaker {
    private int counter = 0;
    private ConnectionMaker realConnectionMaker; // 실제 DB 연결을 담당하는 객체

    // 생성자를 통해 실제 ConnectionMaker(DConnectionMaker)를 DI 받는다.
    public CountingConnectionMaker(ConnectionMaker realConnectionMaker) {
        this.realConnectionMaker = realConnectionMaker;
    }

    // DB 연결을 요청받을 때마다 카운터를 증가시키고, 실제 연결을 반환한다.
    @Override
    public Connection makeConnection() throws ClassNotFoundException, SQLException {
        counter++;
        return realConnectionMaker.makeConnection();
    }

    // 현재 DB 연결 요청 횟수를 반환합니다.
    public int getCounter() {
        return counter;
    }
}
