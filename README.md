Chebit 프로젝트 도커 컴포즈로 실행 방법
---
* 프로젝트 clone
```git
git clone https://github.com/Sungmin1010/Chebit.git
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
