# Sử dụng hình ảnh OpenJDK để chạy ứng dụng Spring Boot
FROM openjdk:17-jdk-slim

# Thêm file JAR vào container
COPY build/libs/Backend_Api-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
# Cấu hình entrypoint để chạy ứng dụng
ENTRYPOINT ["java", "-jar", "/app.jar"]
