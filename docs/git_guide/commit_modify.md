# <커밋 메세지 수정>
### 1. 로컬 커밋 메세지 수정
```
git commit --amend // 에디터 열기
// 에디터에서 편집
// ESC 누름
// :wq 입력후 ENTER 누름
// 에디터 모드 빠져나옴
```
### 2. 로컬 커밋 내역 재확인
```
git log

// 또는 간단하게 확인용
git log --oneline
```
### 3. 변경내용 원격에 반영
```
// 원격에 반영 가능할때만 반영
git push --force-with-lease

// 무조건 반영 (위험)
git push --force
```
