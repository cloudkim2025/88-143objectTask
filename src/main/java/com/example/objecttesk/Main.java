package com.example.objecttesk;

import com.example.objecttesk.dao.DUserDao;
import com.example.objecttesk.dao.NUserDao;
import com.example.objecttesk.domain.User;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        //  DUserDao 사용 (spring_tobi DB 연결)
        DUserDao dUserDao = new DUserDao();
        User user1 = new User();
        user1.setId("duser1");
        user1.setName("D-사용자");
        user1.setPassword("1234");
        dUserDao.add(user1);
        System.out.println(dUserDao.get("duser1").getName() + " 조회 성공!");

        // NUserDao 사용 (spring_tobi_2 DB 연결)
        NUserDao nUserDao = new NUserDao();
        User user2 = new User();
        user2.setId("nuser1");
        user2.setName("N-사용자");
        user2.setPassword("5678");
        nUserDao.add(user2);
        System.out.println(nUserDao.get("nuser1").getName() + " 조회 성공!");
    }
}
