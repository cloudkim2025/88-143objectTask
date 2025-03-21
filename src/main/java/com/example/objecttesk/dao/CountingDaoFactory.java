package com.example.objecttesk.dao;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CountingDaoFactory {

    @Bean
    public UserDao userDao() {
        UserDao userDao = new UserDao();  // 기본 생성자 사용
        // 수정자 메소드를 통해 DataSource를 주입합니다.
        userDao.setDataSource(countingDataSource());
        return userDao;
    }

    @Bean
    public DataSource countingDataSource() {
        // CountingDataSource는 내부에 실제 DB 연결을 위한 DataSource (여기서는 DDataSource)를 DI 받습니다.
        return new CountingDataSource(realDataSource());
    }

    @Bean
    public DataSource realDataSource() {
        // 실제 DB 연결 기능을 제공하는 객체 (예: DDataSource)
        return new DDataSource();
    }
}
