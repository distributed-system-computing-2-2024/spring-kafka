# Spring-kafka
Spring scheduler를 사용해 5분 단위로 한국도로공사 실시간 소통 데이터 링크정보를 가지고 와 Kafka에 publish하는 스프링 Batch 서버


## 이 프로젝트의 설정
Kafka

topicName : "trafficSystem"

Partition 개수 : 4

replication 개수 : 1

```
git clone https://github.com/distributed-system-computing-2-2024/spring-kafka.git
cd spring-kafka
```

아래 명령어는 Docker 실행 및 로그인 돼있다고 가정

## Windows

```
gradlew build
docker-compose up -d
```

## Mac

```
./gradlew build
sudo docker-compose up -d
```
