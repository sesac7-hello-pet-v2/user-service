# Hello Pet v2 - User Service

사용자 관리를 담당하는 마이크로서비스입니다.

## 📌 개요

Hello Pet v2의 User Service는 사용자 계정 관리, 프로필 정보 관리, 비밀번호 보안 등을 담당합니다. 헥사고날 아키텍처(포트와 어댑터 패턴)를 적용하여 도메인 중심의 설계를 구현하고, DDD(Domain-Driven Design) 원칙을 따라 개발되었습니다.

## 🚀 기술 스택

### Core Framework
- **Java**: 17
- **Spring Boot**: 3.5.6
- **Spring Cloud**: 2025.0.0
- **Build Tool**: Gradle 8.5

### Dependencies
- **데이터베이스**: PostgreSQL + Spring Data JPA
- **보안**: Spring Security Crypto (BCrypt)
- **통신**: OpenFeign (마이크로서비스 간 통신)
- **모니터링**: Spring Boot Actuator
- **메트릭**: Micrometer + Prometheus
- **트레이싱**: OpenTelemetry
- **유틸리티**: Lombok
- **검증**: Spring Validation

## 🏗️ 아키텍처

### 헥사고날 아키텍처 (Ports & Adapters)

```
┌───────────────────────────────────────────────────┐
│                  Presentation Layer                │
│              (REST Controllers, DTOs)              │
└─────────────────────┬──────────────────────────────┘
                      │
        ┌─────────────▼──────────────┐
        │   Application Layer        │
        │  (Use Cases, Port In/Out)  │
        └─────────────┬──────────────┘
                      │
    ┌─────────────────▼─────────────────┐
    │         Domain Layer              │
    │   (Entities, Value Objects,       │
    │    Domain Services, Repository)   │
    └─────────────────┬─────────────────┘
                      │
        ┌─────────────▼──────────────┐
        │   Infrastructure Layer     │
        │  (JPA, External Services)  │
        └────────────────────────────┘
```

## 📁 프로젝트 구조

```
user-service/
├── src/main/java/hello/pet/userservice/
│   ├── adapter/                          # 외부 어댑터
│   │   ├── in/                          # 인바운드 어댑터
│   │   │   └── web/                     # REST 컨트롤러
│   │   │       ├── UserController.java
│   │   │       ├── InternalUserController.java
│   │   │       └── dto/
│   │   │           ├── RegisterUserRequest.java
│   │   │           ├── RegisterUserResponse.java
│   │   │           ├── UpdateUserDetailRequest.java
│   │   │           ├── UserDetailResponse.java
│   │   │           ├── CheckPasswordRequest.java
│   │   │           ├── UniqueCheckRequest.java
│   │   │           └── LoginValidationRequest.java
│   │   ├── out/                         # 아웃바운드 어댑터
│   │   │   ├── persistence/             # 데이터베이스
│   │   │   │   ├── JpaUserRepository.java
│   │   │   │   ├── JpaUserDetailRepository.java
│   │   │   │   └── UserRepositoryImpl.java
│   │   │   └── web/                     # 외부 API
│   │   │       └── dto/
│   │   └── config/
│   │       └── PasswordConfig.java      # 비밀번호 암호화 설정
│   ├── application/                      # 애플리케이션 계층
│   │   ├── port/                        # 포트 정의
│   │   │   ├── in/                      # 인바운드 포트
│   │   │   │   ├── CreateUserUseCase.java
│   │   │   │   ├── ReadUserUseCase.java
│   │   │   │   ├── UpdateUserUseCase.java
│   │   │   │   ├── DeleteUserUseCase.java
│   │   │   │   └── command/
│   │   │   │       ├── RegisterUserCommand.java
│   │   │   │       ├── ReadUserCommand.java
│   │   │   │       ├── UpdateUserDetailCommand.java
│   │   │   │       └── UniqueCheckCommand.java
│   │   │   └── out/                     # 아웃바운드 포트
│   │   │       ├── repository/
│   │   │       │   └── UserRepository.java
│   │   │       └── result/
│   │   │           ├── RegisterUserResult.java
│   │   │           ├── UserDetailResult.java
│   │   │           └── UniqueCheckResult.java
│   │   ├── service/                      # 유스케이스 구현
│   │   │   └── UserService.java
│   │   └── exception/                    # 예외 처리
│   │       ├── UserNotFoundException.java
│   │       ├── UserDetailNotFoundException.java
│   │       ├── PasswordNotMatchedException.java
│   │       └── DuplicateEmailException.java
│   └── domain/                           # 도메인 계층
│       ├── entity/
│       │   ├── User.java                # 사용자 엔티티
│       │   ├── UserDetail.java          # 사용자 상세 정보
│       │   └── UserRole.java            # 사용자 권한
│       ├── vo/                          # Value Objects
│       │   ├── Email.java
│       │   ├── Password.java
│       │   └── UserActivation.java
│       └── repository/
│           └── UserDomainRepository.java
├── src/main/resources/
│   └── application.yaml                  # 설정 파일
├── Dockerfile                             # Docker 이미지
├── docker-compose.yaml                   # Docker Compose 설정
├── build.gradle                           # 빌드 설정
└── README.md
```

## 🔧 주요 기능

### 1. 사용자 계정 관리
- **회원가입**: 이메일/비밀번호 기반 계정 생성
- **중복 확인**: 이메일, 닉네임 중복 체크
- **계정 삭제**: 비밀번호 확인 후 계정 삭제
- **계정 상태**: 활성화/비활성화 관리

### 2. 사용자 정보 관리
- **프로필 조회**: 사용자 기본 정보 및 상세 정보
- **프로필 수정**: 닉네임, 연락처, 주소 등 수정
- **상세 정보**: 반려동물 정보, 선호도 등 추가 정보

### 3. 보안
- **비밀번호 암호화**: BCrypt (라운드 10)
- **비밀번호 검증**: 현재 비밀번호 확인
- **권한 관리**: USER, ADMIN 역할 구분
- **데이터 검증**: 이메일 형식, 필수 필드 검증

### 4. 내부 서비스 연동
- **로그인 검증**: Auth Service를 위한 인증 정보 제공
- **사용자 정보 조회**: 다른 서비스를 위한 사용자 정보 제공
- **계정 상태 확인**: 활성화 상태 검증

## 🔑 API 엔드포인트

### 사용자 관리 API

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/v1/users` | 회원가입 | ❌ |
| GET | `/v1/users` | 사용자 정보 조회 | ✅ |
| PUT | `/v1/users` | 사용자 정보 수정 | ✅ |
| DELETE | `/v1/users` | 계정 삭제 | ✅ |
| GET | `/v1/users/exist` | 중복 확인 | ❌ |
| POST | `/v1/users/check-password` | 비밀번호 확인 | ✅ |

### 내부 API (Internal)

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/internal/users/validate` | 로그인 검증 | ❌ (Internal) |
| POST | `/internal/users/info` | 사용자 정보 조회 | ❌ (Internal) |

### 요청/응답 예시

**회원가입 요청:**
```json
POST /v1/users
{
  "email": "user@example.com",
  "password": "Password123!",
  "nickname": "홍길동",
  "phoneNumber": "010-1234-5678",
  "address": "서울시 강남구",
  "profileImage": "https://example.com/profile.jpg"
}
```

**회원가입 응답:**
```json
{
  "id": 1,
  "email": "user@example.com",
  "nickname": "홍길동",
  "role": "USER",
  "createdAt": "2024-10-27T10:00:00"
}
```

**중복 확인 요청:**
```
GET /v1/users/exist?field=email&value=user@example.com
```

**중복 확인 응답:**
```json
{
  "isUnique": false,
  "field": "email",
  "message": "이미 사용중인 이메일입니다."
}
```

## 🚦 시작하기

### 사전 요구사항
- JDK 17 이상
- Gradle 8.5 이상
- PostgreSQL 14 이상

### 로컬 개발 환경

1. **PostgreSQL 설정**
```bash
# PostgreSQL 실행 (docker-compose 사용)
docker-compose up -d

# 또는 개별 실행
docker run -d \
  --name postgres-user \
  -p 5432:5432 \
  -e POSTGRES_DB=db-user \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  postgres:14-alpine
```

2. **프로젝트 클론**
```bash
git clone <repository-url>
cd user-service
```

3. **빌드**
```bash
./gradlew clean build
```

4. **실행**
```bash
./gradlew bootRun
```

### 환경 변수

```yaml
# 데이터베이스
SPRING_DATASOURCE_URL: jdbc:postgresql://localhost:5432/db-user
SPRING_DATASOURCE_USERNAME: postgres
SPRING_DATASOURCE_PASSWORD: postgres

# JPA 설정
SPRING_JPA_HIBERNATE_DDL_AUTO: validate  # Production
SPRING_JPA_SHOW_SQL: false

# 서버 포트
SERVER_PORT: 8082
```

## 🐳 Docker

### 이미지 빌드
```bash
docker build -t hello-pet-user .
```

### 컨테이너 실행
```bash
docker run -d \
  --name user-service \
  -p 8082:8082 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/db-user \
  hello-pet-user
```

### Docker Compose
```yaml
version: '3.8'
services:
  postgres:
    image: postgres:14-alpine
    environment:
      POSTGRES_DB: db-user
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
    ports:
      - "5432:5432"
    volumes:
      - postgres-data:/var/lib/postgresql/data

  user-service:
    build: .
    ports:
      - "8082:8082"
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://postgres:5432/db-user
    depends_on:
      - postgres

volumes:
  postgres-data:
```

## 🗄️ 데이터베이스 스키마

### users 테이블
```sql
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email_value VARCHAR(255) UNIQUE NOT NULL,
    password_value VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    activation_value BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### user_details 테이블
```sql
CREATE TABLE user_details (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT UNIQUE REFERENCES users(id) ON DELETE CASCADE,
    nickname VARCHAR(100) NOT NULL,
    phone_number VARCHAR(20),
    address TEXT,
    profile_image VARCHAR(500),
    birth_date DATE,
    gender VARCHAR(10),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

## 📊 모니터링

### Actuator 엔드포인트
```bash
# 헬스 체크
GET http://localhost:8082/actuator/health

# Prometheus 메트릭
GET http://localhost:8082/actuator/prometheus

# 애플리케이션 정보
GET http://localhost:8082/actuator/info
```

### 주요 메트릭
- 회원가입 성공/실패 카운트
- 사용자 조회 응답 시간
- 비밀번호 검증 시도 횟수
- 데이터베이스 연결 풀 상태

## 🔒 보안 고려사항

### 비밀번호 보안
- **BCrypt 해싱**: 라운드 10 (조정 가능)
- **최소 요구사항**: 8자 이상, 대소문자, 숫자, 특수문자 포함
- **검증 제한**: 비밀번호 확인 시도 횟수 제한 (예정)

### 데이터 보안
- **민감 정보 마스킹**: 로그에서 비밀번호 마스킹
- **SQL Injection 방지**: JPA 파라미터 바인딩
- **XSS 방지**: 입력값 검증 및 이스케이핑

### API 보안
- **인증 확인**: Gateway에서 JWT 토큰 검증
- **권한 확인**: 사용자 본인 정보만 수정 가능
- **Rate Limiting**: API 호출 횟수 제한 (예정)

## 🐛 문제 해결

### 데이터베이스 연결 실패
```bash
# PostgreSQL 상태 확인
docker ps | grep postgres

# 연결 테스트
psql -h localhost -U postgres -d db-user
```

### Unique 제약 조건 위반
- 이메일 중복 확인 API 사용
- 예외 처리로 적절한 오류 메시지 반환

### 비밀번호 검증 실패
- BCrypt 라운드 설정 확인
- 인코딩 설정 확인 (UTF-8)

## 🧪 테스트

```bash
# 단위 테스트
./gradlew test

# 통합 테스트
./gradlew integrationTest

# 테스트 커버리지
./gradlew jacocoTestReport
```

## 📈 성능 최적화

- **쿼리 최적화**: N+1 문제 해결 (Fetch Join)
- **인덱싱**: email, nickname 필드 인덱스
- **캐싱**: 자주 조회되는 사용자 정보 캐싱 (예정)
- **연결 풀링**: HikariCP 최적화
