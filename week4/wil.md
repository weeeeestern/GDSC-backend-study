# 4주차

- 어플리케이션이 사용할 DB 설계
- JPA를 이용한 DB 구현

---

- **개체**(Entity) : 문제 상황을 구성하는 요소
- **관계**(Relationship) : 개체와 개체 사이의 관계
- ER Model : 문제 상황을 개체와 관계로 표현하는 방법을 ER Model (Entity-Relationship Model) 이라고 한다. → **ERD** :  다이어그램으로 표현
    - 개체 → 테이블 (엔티티)
    - 관계 → 테이블 or 외래키
    - 속성 → 테이블 컬럼

---

- DB 설계
    - 1 : N 관계  ex) 유저 - 할 일
        - **외래키**로 구현 (멤버 id), 비식별 관계
    - N : M 관계  ex) 유저 - 동아리
        - 테이블로 구현 // 중간 테이블로 나누어야 함.
            
            ![image.png](image.png)
            
    - <DB 설계 실습>
    
    ![image.png](image%201.png)
    

---

- JPA (Java Persistence API)
    - 데이터베이스에서 읽어온 데이터를 자바 객체로 매핑하는 자바의 표준 기술 (ORM)
    - 엔티티(Entity)는 자바와 데이터베이스가 소통하는 단위
    - 테이블의 데이터 하나(레코드)는 엔티티 객체 하나로 매핑된다
    
    → 엔티티 클래스를 정의하면, **JPA**가 엔티티 클래스 정의를 보고 테이블을 생성하는 SQL(쿼리)을 알아서 작성하고 실행한다.
    
    - <JPA 세팅 실습>
        - build.gradle 의 코드 중, dependencies 부분에 밑 내용 추가 (의존성 추가)
        - 의존성 정보가 바뀌면 반드시 gradle을 다시 로드하자
            
            ![image.png](image%202.png)
            
        - resources – application.properties 파일에 DB 접속 정보를 작성(.yml)
            
            ![image.png](image%203.png)
            
        - 후, main 에서 application 실행 후, 관리자 콘솔 접속으로 DB 연결

---

- todo 패키지 안에 Todo 클래스 만들기
    - 엔티티 클래스는 테이블을, 클래스 필드는 컬럼을 나타낸다 ( 컬럼은 객체지향 은닉을 위해 private 선언)
    - @Entity 어노테이션으로 이 클래스가 **엔티티**라는 것을 명시
    - @Id 어노테이션으로 PK 필드에 이 필드가 **PK**라는 것을 명시
    - @GeneratedValue : 자동으로 1씩 증가하는 속성 부여
    - @Column : ERD에서 설계했던 Column 이름과 타입을 맞추기
    - → JPA 가 자동으로 sql문을 작성해서 실행하고 table까지 만들어준 셈
- member 패키지 안에 Member 클래스 만들기

- 외래키 직접 저장 시 →  연관된 데이터가 필요할 때, 외래키로 데이터를 조회하는 코드를 직접 작성해야 한다. (sql join문 직접 작성해야함)
- 엔티티로 저장하면 테이블을 만들 때 외래키를 만들어주고, 연관된 데이터가 필요할 때, 자동으로 join 쿼리가 실행되면서 연관된 데이터를 얻는다.
    - @JoinColumn : 외래키(FK) 컬럼 정보를 명시하는 어노테이션 (FK 컬럼 이름 등)
    - @ManyToOne
    - @OneToOne
    - @ManyToMany 는 N : M 관계를 나타낸다. N : M 관계는 외래키대신 **테이블**로 구현하므로 사용하지 않는다.
    - @OneToMany :  1에 해당하는 엔티티(ex. 유저)에 N(ex. 동아리)에 대한 연관관계를 명시하는
    **양방향 매핑**에 사용된다.
    
- fetch type (연관관계 종류를 나타내는 어노테이션, 연결된 엔티티를 **언제 가져올지** 명시함)
    - EAGER, LAZY. //LAZY를 사용하자.
    
- 엔티티 생성자 // lombok을 사용하면 자동으로 만들어준다.
    - @Builder
    - JPA는 엔티티 객체를 다룰 때 public 또는 protected의 인자 없는 생성자(**기본 생성자**)가 필요하다.
        - @NoArgsConstructor (이때 access 속성을 통해 접근 제한자를 protected로 설정)
        - 엔티티 객체에 @Getter를 추가해 모든 필드에 getter를 만든다.