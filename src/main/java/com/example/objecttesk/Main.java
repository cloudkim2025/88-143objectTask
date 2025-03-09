package com.example.objecttesk;

import com.example.objecttesk.dao.UserDao;
import com.example.objecttesk.domain.User;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // UserDao 인스턴스 생성
        UserDao userDao = new UserDao();

        // 새로운 사용자 추가
        User user = new User();
        user.setId("testUser");
        user.setName("테스트 사용자");
        user.setPassword("test1234");
        userDao.add(user);
        System.out.println(user.getId() + " 등록 성공!");

        // 사용자 조회
        User retrievedUser = userDao.get("testUser");
        System.out.println("조회된 사용자: " + retrievedUser.getName());
    }
}
