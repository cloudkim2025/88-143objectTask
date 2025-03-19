package com.example.objecttesk.test;

import com.example.objecttesk.dao.DaoFactory;
import com.example.objecttesk.dao.UserDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

public class UserDaoTest {
    public static void main(String[] args) throws Exception {
        // DaoFactory의 인스턴스를 직접 생성한 후, 매번 새로운 UserDao를 받아옴
        DaoFactory factory = new DaoFactory();
        UserDao dao1 = factory.userDao();
        UserDao dao2 = factory.userDao();

        System.out.println("dao1: " + dao1);
        System.out.println("dao2: " + dao2);
    }
}
