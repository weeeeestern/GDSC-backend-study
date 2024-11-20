# 11주차

### API 문서화 (api 사용법)

- spring doc (외부 라이브러리) : api 자동 문서화
- Swagger-ui : spring doc 이 생성한 api 문서에 swagger 디자인 적용

[gradle.build](http://gradle.build) 파일 dependencies에

```java
implementation 'org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0'
```

추가 (gradle 건들이면 refresh 필수!!)

@ApiResponse( responseCode = “404”, description = “ 찾을 수 없다.”) → 상태 코드에 설명 추가 가능

[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html) → swagger 적용된 api 사용법 有, postman 처럼 요청을 보내볼 수 있다. ( + api 호출 시 필요한 필드를 알 수도 있다)

---

### 배포과정

매번 소스 코드를 git 으로 받아온 뒤 실행하는 건 매우 어렵기 때문에, 
여러 어플리케이션 소스 코드를 합쳐서, 하나의 실행가능한 파일로 만드는 **빌드** 과정을 거친다. 

여러 의존성 관리, build 도와주는 도구

- gradle : 빠르고 간단함. 코드 기반의 빌드 스크립트
- maven (xml)

```java
./gradlew.bat build      //window 기준 빌드 실행 명령어
```

자바 어플리케이션 빌드

- 모든 테스트를 통과해야, 빌드가 된다.
- 빌드 성공 시, 하나의 실행가능한 .jar 파일이 만들어진다.
- AWS ec2 등에서 java를 설치하고, jar 파일을 옮겨서 실행하면 타 인스턴스에 스프링 서버를 실행할 수 있다.
    - 자바 설치하고.. 이런거 귀찮은데 docker를 쓰면 실행 환경도 동일하게 배포 가능
    - jar 파일
        - aws S3 스토리지에 파일 업로드 하고, 각 서버에서 필요할 때 다운로드
        - docker : jar 파일이 포함된 docker 이미지를 빌드하여 컨테이너로 실행

컴파일러 : (.java) → (.class)

JVM : 바이트코드 파일(.class) → 기계어 로 변환 (프로그램 runtime 중에만 존재)

- 빌드 과정에서는 JVM이 만든 바이트 코드는 존재하지 않음..

---

koyeb 

- 로컬에서 빌드 X
- 깃에 올린 소스코드로 코옙 서버 기기가 빌드 및 실행을 해준다.
    - 코옙도
    도커 컴포즈 파일로 실행 환경 설정하고, 도커 이미지 파일 만들고, 도커 이미지 내보내기 (실행)