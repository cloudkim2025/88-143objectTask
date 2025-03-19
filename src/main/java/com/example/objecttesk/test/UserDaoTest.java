package com.example.objecttesk.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.example.objecttesk.dao.DaoFactory;
import com.example.objecttesk.dao.UserDao;

public class UserDaoTest {
    public static void main(String[] args) {
        // Spring 컨테이너에 DaoFactory 설정 클래스를 등록
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);

        // 컨테이너에서 userDao 빈을 요청 (기본 스코프는 싱글톤)
        UserDao dao3 = context.getBean("userDao", UserDao.class);
        UserDao dao4 = context.getBean("userDao", UserDao.class);

        System.out.println("dao3: " + dao3);
        System.out.println("dao4: " + dao4);
    }
}
