


## 1. 의존 관계 주입(Dependency Injection, DI)
- **정의:**  
  클래스가 필요한 의존 객체(예: DB 연결 객체)를 스스로 생성하지 않고, 외부에서 미리 만들어서 전달받는 방식입니다.
  
- **예시 코드:**
  ```java
  public class UserDao {
      private final ConnectionMaker connectionMaker;
  
      // 생성자 주입: 외부에서 ConnectionMaker 구현체를 전달받음
      public UserDao(ConnectionMaker connectionMaker) {
          this.connectionMaker = connectionMaker;
      }
  
      public void add(User user) throws ClassNotFoundException, SQLException {
          Connection c = connectionMaker.makeConnection();
          // DB 작업 수행
      }
  
      public User get(String id) throws ClassNotFoundException, SQLException {
          Connection c = connectionMaker.makeConnection();
          // DB에서 User 조회 후 반환
      }
  }
  ```

- **장점:**
  - 코드 간 결합도 감소
  - 유연한 구현체 교체 (예: DConnectionMaker, NConnectionMaker)
  - 단위 테스트 시 모의 객체(Mock)를 주입 가능

## 2. 의존 관계 검색(Dependency Lookup)
- **정의:**  
  클래스가 필요할 때 외부 컨테이너(IoC 컨테이너)에서 스스로 의존 객체를 찾아 할당하는 방식입니다.
  
- **예시 코드:**
  ```java
  public UserDao(){
      AnnotationConfigApplicationContext context =
          new AnnotationConfigApplicationContext(DaoFactory.class);
      this.connectionMaker = context.getBean("connectionMaker", ConnectionMaker.class);
  }
  ```

- **비교:**
  - **DI:** 외부에서 미리 주입 (주입 방식)  
  - **의존 관계 검색:** 클래스 내부에서 IoC 컨테이너를 통해 검색 (검색 방식)  
  두 방식 모두 런타임 시 필요한 의존 객체를 결정하지만, DI는 코드의 프레임워크 종속성을 낮추는 장점이 있음.

## 3. DaoFactory를 활용한 팩토리 패턴
- **목적:**  
  객체 생성 책임을 한 곳으로 모아두어, 여러 DAO가 필요한 의존 객체를 생성하는 방식을 중앙에서 관리함.
  
- **예시 코드 (리팩토링된 DaoFactory):**
  ```java
  @Configuration
  public class DaoFactory {
  
      @Bean
      public UserDao userDao() {
          return new UserDao(connectionMaker());
      }
  
      @Bean
      public ConnectionMaker connectionMaker() {
          return new DConnectionMaker();
      }
  }
  ```
- **효과:**  
  - 코드 중복 제거
  - 유지보수 용이성 (DB 연결 방식 변경 시 한 곳만 수정)
  - 테스트나 확장이 쉬워짐

## 4. 싱글톤 패턴과 그 한계 (참고용)
- **싱글톤 패턴:**  
  클래스의 인스턴스가 오직 하나만 생성되어 전역에서 공유되는 패턴.
- **예시 코드 (참고):**
  ```java
  public class UserDao {
      private static UserDao INSTANCE;
      private ConnectionMaker connectionMaker;
  
      private UserDao(ConnectionMaker connectionMaker) {
          this.connectionMaker = connectionMaker;
      }
  
      public static synchronized UserDao getInstance() {
          if (INSTANCE == null) {
              INSTANCE = new UserDao(new DConnectionMaker());
          }
          return INSTANCE;
      }
  }
  ```
- **주의점:**  
  - 테스트하기 어려움  
  - 전역 상태로 인한 결합도 증가  
  - 실습에서는 DI/팩토리 패턴을 사용하여 싱글톤의 단점을 회피

## 5. 결론 및 정리
- **의존 관계 주입(DI)** 은 코드의 유연성을 높이고, 테스트 및 유지보수를 쉽게 만든다.
- **의존 관계 검색(Dependency Lookup)** 은 필요한 의존 객체를 스스로 IoC 컨테이너에서 찾는 방식으로, DI와 유사한 장점을 가지지만 코드 내부에 프레임워크 의존성이 늘어난다.
- **DaoFactory** 를 활용한 팩토리 패턴은 객체 생성 로직을 한 곳에 모아두어, 여러 DAO들이 공통의 의존 객체를 쉽게 공유하고 변경할 수 있도록 도와준다.
- **싱글톤 패턴**은 참고용 개념으로, 전역 상태 공유의 단점을 잘 이해하고 DI와 비교하여 언제 사용해야 할지 판단하면 뒨다.

---
