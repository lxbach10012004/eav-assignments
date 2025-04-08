# Ứng dụng Spring Boot Producer-Consumer với Kafka và Docker

Dự án này bao gồm hai ứng dụng Spring Boot: **Producer** và **Consumer**, sử dụng Apache Kafka để truyền thông điệp giữa hai ứng dụng. Kafka và Zookeeper được triển khai thông qua Docker.

- **Producer-app**: Gửi thông điệp tới Kafka topic (chạy trên `localhost:8080`).
- **Consumer-app**: Nhận thông điệp từ Kafka topic và xử lý (chạy trên `localhost:8081`).
- **Docker-kafka-zookeeper**: Cung cấp môi trường Kafka và Zookeeper bằng Docker.

## Yêu cầu

- Java 17
- Maven
- Docker Desktop
- Kafka (được cung cấp qua Docker trong dự án)
- PowerShell (để chạy lệnh kiểm tra API)

## Cài đặt và chạy dự án

### 1. Chạy Kafka và Zookeeper bằng Docker

1. Di chuyển vào thư mục `docker-kafka-zookeeper`:
cd docker-kafka-zookeeper
2. Khởi động Kafka và Zookeeper:
docker-compose up -d

Kafka sẽ chạy trên `localhost:9092`.

### 2. Cấu hình ứng dụng

#### Producer-app
Cấu hình Kafka trong `producer-app/src/main/resources/application.yml`:
server:
port: 8080
spring:
kafka:
bootstrap-servers: localhost:9092
producer:
key-serializer: org.apache.kafka.common.serialization.StringSerializer
value-serializer: org.apache.kafka.common.serialization.StringSerializer

#### Consumer-app
Cấu hình Kafka trong `consumer-app/src/main/resources/application.yml`:
server:
port: 8081
spring:
kafka:
bootstrap-servers: localhost:9092
consumer:
group-id: my-group
auto-offset-reset: earliest
key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
value-deserializer: org.apache.kafka.common.serialization.StringDeserializer

### 3. Chạy ứng dụng

1. Mở terminal trong thư mục `producer-app` và chạy:
mvn spring-boot:run

2. Mở terminal khác trong thư mục `consumer-app` và chạy:
mvn spring-boot:run

### 4. Kiểm tra API bằng PowerShell

Sau khi cả hai ứng dụng đang chạy, sử dụng PowerShell để kiểm tra:

#### 4.1. Gửi thông điệp từ Producer
Mở PowerShell và chạy lệnh sau để gửi một thông điệp tới `producer-app`:
Invoke-RestMethod -Uri http://localhost:8080/users -Method POST -ContentType "application/json" -Body '{"name":"Alice","age":25}'

Lệnh này gửi một yêu cầu POST tới endpoint `/users` của `producer-app`, với dữ liệu JSON chứa thông tin người dùng (tên: Alice, tuổi: 25). Thông điệp sẽ được gửi tới Kafka topic.

#### 4.2. Lấy dữ liệu từ Consumer
Chạy lệnh sau để kiểm tra dữ liệu đã được `consumer-app` xử lý:
Invoke-RestMethod http://localhost:8081/users

Lệnh này gửi yêu cầu GET tới endpoint `/users` của `consumer-app` để lấy thông tin đã nhận từ Kafka.

#### 4.3. Kết quả
Kết quả chạy được minh họa trong hình ảnh:

![Kết quả](img/1.png)

---

## Ghi chú

- Đảm bảo Docker Desktop đang chạy trước khi khởi động Kafka.
- Các endpoint `/users` cần được triển khai trong mã nguồn của `producer-app` và `consumer-app`. Nếu endpoint khác, hãy điều chỉnh lệnh PowerShell tương ứng.
- Có thể kiểm tra log của `consumer-app` để xác nhận thông điệp đã được nhận từ Kafka.

---

Bài làm của: **Lê Xuân Bách** - MSV 22024506

ChatGPT: 

Dưới đây là phiên bản viết lại của file README với định dạng đẹp, rõ ràng hơn, sử dụng markdown một cách trực quan, hiện đại và dễ đọc:



---



# 🚀 Ứng dụng Spring Boot Producer - Consumer với Kafka & Docker 

Dự án gồm hai ứng dụng Spring Boot: **Producer**  và **Consumer** , sử dụng **Apache Kafka**  để truyền thông điệp giữa hai bên. Kafka và Zookeeper được triển khai thông qua Docker Compose.

## 🧩 Kiến trúc hệ thống 



```lua
+-------------+          Kafka           +-------------+
| Producer    |  --->  Topic (Kafka)  -> | Consumer    |
| Spring Boot |                         | Spring Boot |
+-------------+                         +-------------+
        |                                     |
    localhost:8080                      localhost:8081
```


## 📦 Thành phần chính 

| Thành phần | Mô tả | 
| --- | --- | 
| producer-app | Gửi thông điệp tới Kafka topic (port 8080) | 
| consumer-app | Nhận và xử lý thông điệp từ Kafka topic (port 8081) | 
| docker-kafka-zookeeper | Cung cấp môi trường Kafka & Zookeeper bằng Docker Compose | 



---



## 🔧 Yêu cầu hệ thống 

 
- Java 17
 
- Maven
 
- Docker Desktop
 
- PowerShell (nếu kiểm tra API bằng lệnh)



---



## 🚀 Hướng dẫn triển khai 


### 1. Khởi động Kafka & Zookeeper bằng Docker 



```bash
cd docker-kafka-zookeeper
docker-compose up -d
```


> Kafka sẽ chạy tại `localhost:9092`



---



### 2. Cấu hình ứng dụng 

`producer-app/src/main/resources/application.yml`


```yaml
server:
  port: 8080

spring:
  kafka:
    bootstrap-servers: localhost:9092
    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.apache.kafka.common.serialization.StringSerializer
```

`consumer-app/src/main/resources/application.yml`


```yaml
server:
  port: 8081

spring:
  kafka:
    bootstrap-servers: localhost:9092
    consumer:
      group-id: my-group
      auto-offset-reset: earliest
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.apache.kafka.common.serialization.StringDeserializer
```



---



### 3. Chạy ứng dụng 

 
- Chạy **Producer** :



```bash
cd producer-app
mvn spring-boot:run
```

 
- Chạy **Consumer** :



```bash
cd consumer-app
mvn spring-boot:run
```



---



### 4. Kiểm tra hoạt động với PowerShell 


#### 4.1. Gửi thông điệp từ Producer 



```powershell
Invoke-RestMethod -Uri http://localhost:8080/users -Method POST -ContentType "application/json" -Body '{"name":"Alice","age":25}'
```


#### 4.2. Lấy thông điệp từ Consumer 



```powershell
Invoke-RestMethod http://localhost:8081/users
```


#### 4.3. Kết quả 


Thông điệp sẽ được gửi từ Producer → Kafka → Consumer. Kết quả xử lý được hiển thị như sau:

![Kết quả](/img/1.png) 



---



## 📌 Lưu ý 

 
- Đảm bảo **Docker Desktop**  đang chạy trước khi khởi động Kafka.
 
- Các endpoint `/users` cần được hiện thực trong mã nguồn `producer-app` và `consumer-app`.
 
- Nếu thay đổi endpoint, bạn cũng cần điều chỉnh lệnh kiểm tra tương ứng.
 
- Kiểm tra **
Kiểm tra log của `consumer-app`**  để xác minh rằng thông điệp đã được nhận thành công từ Kafka.



---
Bài làm của: **Lê Xuân Bách** - MSV 22024506
