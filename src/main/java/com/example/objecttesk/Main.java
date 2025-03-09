package com.example.objecttesk;

import com.example.objecttesk.dao.DConnectionMaker;
import com.example.objecttesk.dao.NConnectionMaker;
import com.example.objecttesk.dao.UserDao;
import com.example.objecttesk.domain.User;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        //  DConnectionMaker 사용
        UserDao userDaoD = new UserDao(new DConnectionMaker());
        User user1 = new User();
        user1.setId("duser1");
        user1.setName("D-사용자");
        user1.setPassword("1234");
        userDaoD.add(user1);
        System.out.println(userDaoD.get("duser1").getName() + " 조회 성공!");

        // NConnectionMaker 사용
        UserDao userDaoN = new UserDao(new NConnectionMaker());
        User user2 = new User();
        user2.setId("nuser1");
        user2.setName("N-사용자");
        user2.setPassword("5678");
        userDaoN.add(user2);
        System.out.println(userDaoN.get("nuser1").getName() + " 조회 성공!");
    }
}
