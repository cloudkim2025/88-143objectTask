package com.example.objecttesk.dao;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.sql.SQLException;

public class UserDaoConnectionCountingTest {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // Spring IoC 컨테이너에 CountingDaoFactory 설정을 등록한다.
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(CountingDaoFactory.class);

        // UserDao 빈을 가져와서 DAO 작업을 수행한다.
        UserDao dao = context.getBean("userDao", UserDao.class);

        // 예를 들어, DAO의 add() 또는 get() 메소드를 몇 번 호출할 수 있다.
        // (여기서는 간단하게 DAO를 한 번 호출하는 예시
        // dao.add(new User(...));
        // dao.get("someId");

        // CountingConnectionMaker 빈을 가져와서 DB 연결 횟수를 출력한다.
        CountingConnectionMaker ccm = context.getBean("connectionMaker", CountingConnectionMaker.class);
        System.out.println("Connection counter: " + ccm.getCounter());

        context.close();
    }
}
