# 🐶 PawLink

공공데이터포털의 유기동물 API를 연동하여 보호동물 정보를 제공하고,
실종동물 게시판을 운영하는 웹 서비스입니다.

외부 API 데이터를 주기적으로 동기화하고,
보호소 및 지역 정보를 연계하여 지역별 보호동물 조회 기능을 제공합니다.

---

## 🛠 Tech Stack

- Java 21
- Spring Boot
- Spring Data JPA
- Spring Security
- Thymeleaf
- PostgreSQL
- WebClient
- Flyway
- Docker / Docker Compose

---

## ✨ 주요 기능

- 공공데이터포털 유기동물 API 연동
- 보호동물 데이터 자동 수집 및 저장
- 보호소 정보 관리 및 보호동물-보호소 연관관계 구성
- 지역별 보호동물 조회
- Scheduler를 이용한 주기적 데이터 동기화

---

## 🚀 실행 방법

### 1. 환경 변수 설정

`.env.example`을 복사하여 `.env` 파일을 생성합니다.

```bash
cp .env.example .env
```

필요한 값을 입력합니다.

```properties
POSTGRES_DB=
POSTGRES_USER=
POSTGRES_PASSWORD=
ANIMAL_API_KEY=
```

### 2. 프로젝트 실행

```bash
./gradlew bootJar
docker compose up --build
```

브라우저에서

```
http://localhost:8080
```

으로 접속합니다.

---

## 🐳 Docker

Docker Compose를 사용하여 Spring Boot와 PostgreSQL을 함께 실행합니다.

실행

```bash
docker compose up --build
```

종료

```bash
docker compose down
```

---

## 🔄 Database Migration

Flyway를 이용해 데이터베이스 스키마 변경 이력을 관리합니다.

- 스키마 버전 관리
- 마이그레이션 자동 적용

---

## 🔐 Environment Variables

DB 비밀번호와 외부 API Key는 `.env` 파일로 관리하며 Git 저장소에는 포함하지 않습니다.

예시 파일은 `.env.example`을 제공합니다.

---

## ⚙️ 데이터 동기화

외부 API 데이터를 기존 DB와 비교하여 동기화합니다.

- 기존 데이터 → 변경 사항 Update
- 신규 데이터 → Insert

보호동물 식별번호(`desertionNo`)를 기준으로 중복을 방지합니다.

---

## 🏠 보호소 데이터 관리

보호소 정보를 별도 Entity로 관리하고,
보호동물과 연관관계를 구성하여 지역별 조회에 활용합니다.

---

## 💡 해결한 문제

- 공공데이터의 지역 코드 예외 데이터를 내부 지역 체계에 맞게 정규화
- 외부 API 동기화 시 중복 데이터 방지
- 보호동물-보호소-지역 간 연관관계 설계
- JPA 영속성 및 연관관계 관리
