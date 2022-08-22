# sensor-monitoring-system
![image](https://user-images.githubusercontent.com/84849202/185909806-8a662c1c-8474-425b-87fa-45c46813cacb.png)

## Description
라즈베리파이로부터 다양한 센서 데이터를 측정하여 웹브라우저 / 안드로이드 앱에서 그래프로 보여주는 시스템입니다.  
온도, 이산화탄소, 유해물질, 가속도 데이터를 저장하고 모니터링 할 수 있습니다.  

## Environment
- Device: Rasbperry Pi 4
- Sensor: MPU6050 (Temp, Accel), CCS811 (eCO2, TVOC)
- Package: Axios, Volley, Rechart, MPAndroidChart

## Tech Stack
- Device: Python
- Back-End: Spring Framework
- Front-End: React, Android
- Database: PostgreSQL
- Version Control: Git, Github

## Structure
![image](https://user-images.githubusercontent.com/84849202/185910898-ffd5b61e-d88f-4f33-b593-5c9516bb00a6.png)

## Feature
### 1. 센서 데이터 저장
라즈베리파이에서 센서 데이터를 측정하고, Spring 서버의 API를 호출하여 DB에 저장할 수 있습니다.

### 2. 센서 데이터 시각화
![image](https://user-images.githubusercontent.com/84849202/185915251-19edd493-ae88-4529-8e6c-9fe9b1e82057.png)
DB에 저장된 센서 데이터를 실시간으로 갱신하여 그래프 형태로 확인할 수 있습니다.

### 3. 안드로이드
![image](https://user-images.githubusercontent.com/84849202/185915351-4469c2ba-b5ca-426e-a75e-755a3c76c312.png)  
센서 데이터를 안드로이드 앱을 통해서도 확인할 수 있습니다.

## Deployment
```
./gradlew build
java -jar build/libs/*-SNAPSHOT.jar
```
