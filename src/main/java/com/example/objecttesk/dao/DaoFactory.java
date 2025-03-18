package com.example.objecttesk.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DaoFactory {
    @Bean
    public UserDao userDao() {
        return new UserDao(connectionMaker());
    }

    // ConnectionMaker 객체 생성 로직을 한 곳으로 분리
    @Bean
    public ConnectionMaker connectionMaker() {
        return new DConnectionMaker();
    }
}
