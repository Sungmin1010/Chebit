# Chebit project
> JPA, TDD, Docker를 학습하면서 활용한 토이 프로젝트 입니다. 
> 도커 컴포즈를 통해 프로젝트를 바로 실행할 수 있으며,
> 초기 데이터는 이메일 'lm@mail.com', 패스워드 '1234' 로 로그인 가능 합니다.
> 기능은 계속 추가 및 수정되고 있습니다.

## 프로젝트 설명
1. 기획 의도
- 루틴한 삶을 살고 싶다
- 루틴한 행동들 속에서 건강과 행복을 찾고 싶다
- 24시간을 알차게 사용하고 싶다
- 작은 일들에서도 성취감을 느끼고 싶다
- 내가한 행동들을 기록하고 나중에 모아서 보고 싶다

2. 주요 기능 
- 매일 체크하고 싶은 Habit 추가/수정/삭제
- 하루 단위로 Habit 체크 가능
- 주단위로 Habit 체크 여부 확인 
- 연속 체크 일수 확인 가능
- (추가예정) 아이폰 단축어 기능 활용할 수 있는 api 개발
- (추가예정) Habit 체크시 포인트 보상 

3. 주요 기술
- Spring boot 
- java 
- H2(docker-compose), MySql(로컬)
- JPA
- Gradle
- Semantic UI
- Mustache

## 도커 컴포즈로 실행 방법
* 프로젝트 clone
```git
git clone https://github.com/Sungmin1010/Chebit.git
cd Chebit
git checkout dev-jpa
```
* 그래들 bootJar로 jar 파일 생성
```
./gradlew bootJar
```
* 도커 컴포즈 실행
```
docker-compose up -d --build
```
* Chebit 서비스 접속
```
localhost:8282
```
* h2 웹콘솔 접속
```
localhost:81
```
