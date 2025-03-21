package com.example.objecttesk.dao;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DaoFactory {

    @Bean
    public UserDao userDao() {
        // 기본 생성자로 UserDao 객체를 생성한 후
        UserDao userDao = new UserDao();
        // setDataSource() 수정자 메소드를 통해 DataSource를 주입합니다.
        userDao.setDataSource(dataSource());
        return userDao;
    }

    @Bean
    public DataSource dataSource() {
        // 여기서는 DDataSource를 예시로 사용합니다.
        return new DDataSource();
    }
}
