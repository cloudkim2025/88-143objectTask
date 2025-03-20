package com.example.objecttesk.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CountingDaoFactory {

    @Bean
    public UserDao userDao() {
        // ConnectionMaker로 CountingConnectionMaker를 주입한다.
        return new UserDao(connectionMaker());
    }

    @Bean
    public ConnectionMaker connectionMaker() {
        // CountingConnectionMaker를 생성하는데, 내부에 실제 DB 연결 기능을 가진 DConnectionMaker를 주입한다.
        return new CountingConnectionMaker(realConnectionMaker());
    }

    @Bean
    public ConnectionMaker realConnectionMaker() {
        // 실제 DB 연결 기능을 담당하는 객체이다.
        return new DConnectionMaker();
    }
}
