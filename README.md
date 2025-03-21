


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



## 1. 의존관계 주입 (DI) vs. 의존관계 검색 (DL)

- **의존관계 주입 (DI):**
  - **개념:**  
    필요한 객체(예: DB 연결 객체)를 외부에서 미리 만들어서, 생성자나 수정자(setter) 등을 통해 주입받는 방식.
  - **장점:**  
    - 코드 내부에 구체적인 클래스 이름이 나타나지 않아 결합도가 낮아짐  
    - 유연하게 운영/개발/테스트 환경을 전환할 수 있음 (설정만 바꾸면 됨)  
    - 테스트가 용이함 (Mock 객체 주입 가능)
  - **예시:**  
    ```java
    // 생성자 DI 방식
    public UserDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }
    ```
  
- **의존관계 검색 (DL):**
  - **개념:**  
    클래스 내부에서 IoC 컨테이너(예: Spring ApplicationContext)를 직접 호출하여 필요한 의존 객체를 찾아오는 방식.
  - **예시:**  
    ```java
    public UserDao() {
        AnnotationConfigApplicationContext context =
            new AnnotationConfigApplicationContext(DaoFactory.class);
        this.connectionMaker = context.getBean("connectionMaker", ConnectionMaker.class);
    }
    ```
  - **비교:**  
    DI 방식은 코드가 더 깔끔하며, 컨테이너 API가 노출되지 않는 장점이 있음.

---

## 2. DI 적용 방법: 생성자 vs. 수정자 (Setter) 메소드

- **생성자 DI:**
  - 생성자 파라미터를 통해 의존 객체를 전달받는 방식.
  - 장점: 필수 의존성을 강제할 수 있음.
  
- **수정자 DI (Setter Injection):**
  - `setConnectionMaker()` 같은 수정자 메소드를 통해 의존 객체를 주입받음.
  - **예시:**
    ```java
    public class UserDao {
        private ConnectionMaker connectionMaker;
        
        // 수정자 메소드 DI
        public void setConnectionMaker(ConnectionMaker connectionMaker) {
            this.connectionMaker = connectionMaker;
        }
    }
    ```
  - 장점: 외부 설정(XML 또는 자바 설정)을 활용할 때 자바빈 규약에 맞춰 간단하게 사용 가능.

---

## 3. DI의 응용: CountingConnectionMaker 예제

- **문제:**  
  DAO에서 DB 연결 횟수를 세어보고 싶은 경우, 기존 DAO 코드에 일일이 카운터 코드를 추가하면 번거롭고 코드가 지저분해집니다.

- **해결 방법:**  
  - **CountingConnectionMaker 클래스:**  
    - `ConnectionMaker` 인터페이스를 구현하고, 내부에 실제 DB 연결을 담당하는 `DConnectionMaker` (또는 다른 구현체)를 DI 받습니다.
    - `makeConnection()` 호출 시 카운터를 증가시키고, 실제 연결은 내부의 객체가 수행하도록 합니다.
  
  - **예시 코드:**
    ```java
    public class CountingConnectionMaker implements ConnectionMaker {
        private int counter = 0;
        private ConnectionMaker realConnectionMaker;
    
        public CountingConnectionMaker(ConnectionMaker realConnectionMaker) {
            this.realConnectionMaker = realConnectionMaker;
        }
    
        @Override
        public Connection makeConnection() throws ClassNotFoundException, SQLException {
            counter++;
            return realConnectionMaker.makeConnection();
        }
    
        public int getCounter() {
            return counter;
        }
    }
    ```

- **DI 설정 (CountingDaoFactory):**
  - 기존 DaoFactory 대신, CountingDaoFactory를 만들어 DI 설정만 변경하면 모든 DAO가 CountingConnectionMaker를 사용하도록 할 수 있습니다.
  - **예시 코드:**
    ```java
    @Configuration
    public class CountingDaoFactory {
    
        @Bean
        public UserDao userDao() {
            UserDao userDao = new UserDao();
            userDao.setConnectionMaker(connectionMaker());
            return userDao;
        }
    
        @Bean
        public ConnectionMaker connectionMaker() {
            return new CountingConnectionMaker(realConnectionMaker());
        }
    
        @Bean
        public ConnectionMaker realConnectionMaker() {
            return new DConnectionMaker();
        }
    }
    ```

- **테스트:**
  - DI 컨테이너를 이용하여 UserDao와 CountingConnectionMaker를 가져오고, DB 연결 횟수가 제대로 증가하는지 확인합니다.
  - **예시 테스트 코드:**
    ```java
    public class UserDaoConnectionCountingTest {
        public static void main(String[] args) throws ClassNotFoundException, SQLException {
            AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(CountingDaoFactory.class);
    
            UserDao dao = context.getBean("userDao", UserDao.class);
            // DAO 작업 수행 (여러 번 DB 연결을 호출하는 메소드 실행)
    
            CountingConnectionMaker ccm = context.getBean("connectionMaker", CountingConnectionMaker.class);
            System.out.println("Connection counter: " + ccm.getCounter());
    
            context.close();
        }
    }
    ```

---

## 4. DI의 장점 요약

- **유연성:**  
  - 운영환경, 개발환경, 테스트환경 등 필요에 따라 DI 설정만 변경하면 되므로, DAO 코드를 수정할 필요가 없습니다.
  
- **관심사의 분리:**  
  - DAO는 DB 연결에만 집중하고, 추가 기능(예: 연결 횟수 카운팅)은 별도의 클래스로 분리하여 관리할 수 있습니다.
  
- **높은 응집도와 낮은 결합도:**  
  - DAO가 구체적인 구현 클래스에 의존하지 않고 인터페이스에만 의존함으로써, 코드의 유지보수와 확장이 쉬워집니다.

- **스프링의 핵심 원칙:**  
  - 스프링 프레임워크의 대부분 기능은 DI를 통해 이루어지며, DI 없이는 스프링도 존재할 수 없습니다.

---

# 1장 요약: 사용자 정보 DAO와 객체지향 설계 개선, IoC/DI 적용

## 1. 책임 분리 및 관심사의 분리 (SoC)
- **문제점:**  
  한 클래스에 여러 책임이 섞여 있으면 유지보수가 어려움.
- **해결:**  
  - 사용자 정보를 DB에 등록하고 조회하는 기능을 분리하여 두 개의 클래스로 만들었다.
  - 각 클래스는 자신의 책임에만 집중하도록 리팩토링하였다.

## 2. 인터페이스 도입 및 전략 패턴 적용
- **목표:**  
  - 변할 수 있는 기능(예: DB 연결 방식)을 인터페이스로 추상화하여, 해당 인터페이스를 구현하는 여러 클래스를 사용할 수 있게 함.
- **전략 패턴:**  
  - UserDao는 구체적인 DB 연결 클래스에 의존하지 않고, ConnectionMaker(혹은 DataSource) 인터페이스에만 의존하도록 설계.
  - 실제 DB 연결 기능은 DConnectionMaker, NConnectionMaker 등으로 대체할 수 있어, 한쪽 클래스의 변경이 다른 클래스에 영향을 주지 않는다.

## 3. 개방-폐쇄 원칙 및 낮은 결합도, 높은 응집도
- **개방-폐쇄 원칙:**  
  - 한쪽 클래스(예: ConnectionMaker의 구현체)가 변경되더라도, 이를 사용하는 UserDao 코드는 수정할 필요가 없게 함.
- **낮은 결합도 & 높은 응집도:**  
  - 각 클래스가 자신의 책임에 집중하고, 외부 오브젝트와 느슨하게 연결되어 있어 유지보수 및 확장이 용이하다.

## 4. 제어의 역전(IoC) 및 DI(의존관계 주입)
- **IoC/DI 적용:**  
  - 객체 생성 및 의존 관계 맺는 작업을 별도의 오브젝트 팩토리(또는 DI 컨테이너)에 맡겨, UserDao는 단순히 자신이 사용할 오브젝트(예: ConnectionMaker 또는 DataSource)를 외부에서 주입받는다.
  - 이는 개발자가 DB 연결 방식이나 다른 의존 오브젝트를 쉽게 변경할 수 있게 도와준다.
- **DI의 실질적 효과:**  
  - 개발 환경, 운영 환경, 테스트 환경에 따라 다른 DB 연결 객체를 주입하기만 하면, UserDao 코드는 그대로 유지할 수 있다.
  - DI는 생성자 주입, 수정자 주입 등 여러 방식으로 적용될 수 있으며, 좋은 객체지향 설계와 결합되어야 한다.

## 5. 싱글톤 레지스트리 및 DI 컨테이너 활용
- **전통적 싱글톤 단점 극복:**  
  - 전통적인 싱글톤 패턴은 테스트와 확장에 한계가 있다.
  - 스프링과 같은 DI 컨테이너를 사용하면, 싱글톤 개념을 유지하면서도 유연하고 깔끔한 설계가 가능하다.
- **싱글톤 레지스트리:**  
  - 컨테이너는 애플리케이션 내에서 필요한 객체들을 싱글톤으로 관리하여, 여러 곳에서 동일한 객체를 안전하게 사용할 수 있게 해준다.

## 6. 결론: 스프링과 좋은 객체지향 설계
- **스프링의 역할:**  
  - 스프링은 객체 생성, 의존 관계 주입, 컨테이너 관리 등 번거로운 작업을 편리하게 해주는 도구이다.
  - 하지만 좋은 설계와 분리, 확장성 있는 코드는 개발자가 객체지향 원칙을 따르고, 경험과 학습을 통해 만들어야 한다.
- **요점:**  
  - 좋은 설계(관심사의 분리, 낮은 결합도, 높은 응집도, 개방-폐쇄 원칙)를 적용하면, 한쪽 클래스의 변경이 다른 클래스에 미치는 영향을 최소화할 수 있다.
  - DI/IoC 컨테이너를 통해 이러한 설계를 실현하면, 개발, 운영, 테스트 환경 모두에서 유연하게 코드의 의존 관계를 관리할 수 있다.

---

이 요약은 1장에서 사용자 정보를 DB에 등록하고 조회하는 간단한 DAO를 개선하면서, 객체지향 설계의 원칙과 IoC/DI, 그리고 스프링 프레임워크의 핵심 개념을 어떻게 적용하는지에 대해 정리한 것입니다.
