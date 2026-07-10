## 🛠 Tech Stack

### Backend
- Java
- Spring Boot
- Spring Data JPA
- Spring Security
- WebClient

### Database
- PostgreSQL

### Frontend
- HTML
- Thymeleaf
- CKEditor
- JavaScript
- AJAX

### Tools
- GitHub
- IntelliJ IDEA

## 📂 PawLink
### 🔹 공통
- JPA(Entity) 기반 데이터 관리
- Java Record DTO 사용
- Bean Validation을 이용한 입력값 검증
- 전역 예외 처리(`@ControllerAdvice`) 및 에러 코드 관리
- Enum 다국어 처리를 위한 `messages.properties` 적용
- 게시물 목록 페이징 구현
### 👤 User / Auth
- 회원가입
- Spring Security 기반 로그인 / 로그아웃
- 권한별 접근 제어

> 기존 Session 방식에서 Spring Security로 변경
>
> **변경 이유**
> POST 요청의 인증 및 권한 검증을 보다 안전하고 일관성 있게 처리하기 위해 Spring Security 기반으로 리팩터링
### 🐾 실종동물 게시판
- 게시글 CRUD
- 게시글 등록 후 Detail 페이지로 Redirect
- 게시글 수정 / 삭제 공통 화면 구성
- CKEditor를 이용한 게시글 작성
- AJAX 기반 댓글 기능
- 회원 / 비회원 댓글 로직 분리
### 🐶 보호동물 게시판
- 공공데이터 API(JSON) 연동
- JSON → DTO → Entity 변환
- 보호동물 데이터 DB 저장
- Scheduler를 이용한 주기적 데이터 동기화
- 기존 데이터 Update / 신규 데이터 Insert 로직 구현
