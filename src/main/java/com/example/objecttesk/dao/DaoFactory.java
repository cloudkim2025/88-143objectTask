package com.example.objecttesk.dao;

public class DaoFactory {
    public UserDao userDao() {
        // DConnectionMaker를 사용하여 ConnectionMaker 객체 생성 후 UserDao에 주입
        ConnectionMaker connectionMaker = new DConnectionMaker();
        return new UserDao(connectionMaker);
    }
}
