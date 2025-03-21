package com.example.objecttesk;

import com.example.objecttesk.dao.UserDao;
import com.example.objecttesk.dao.DDataSource;
import com.example.objecttesk.domain.User;

public class Main {
    public static void main(String[] args) {
        try {
            // DDataSource 사용 예시
            UserDao userDao = new UserDao();
            userDao.setDataSource(new DDataSource());

            User user = new User();
            user.setId("duser1");
            user.setName("D-사용자");
            user.setPassword("1234");
            userDao.add(user);
            System.out.println(userDao.get("duser1").getName() + " 조회 성공!");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
