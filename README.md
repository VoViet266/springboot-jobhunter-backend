
## Về tác giả
- **Họ và tên:** Võ Quốc Việt
- **Note:** Đây là bài tập cá nhân của mình, nếu bạn có nhu cầu sử dụng, vui lòng liên hệ mình qua email:
- **Email:** voviet266@gmail.com

## Yêu cầu hệ thống
- **JDK:** 17 hoặc cao hơn
- **Gradle:** 6.8 hoặc cao hơn

## Cấu trúc dự án
- `src/main/kotlin`: Chứa mã nguồn chính của ứng dụng.
- `src/main/resources`: Chứa các file cấu hình, chẳng hạn như `application.properties`.
- `build.gradle`: File cấu hình Gradle cho dự án.

## Thiết lập môi trường
1. **Cài đặt JDK:** Tải JDK từ Oracle hoặc sử dụng OpenJDK. Đảm bảo rằng biến môi trường `JAVA_HOME` đã được thiết lập đúng.
2. **Cài đặt Gradle:** Tải và cài đặt Gradle từ trang chủ Gradle. Đảm bảo rằng biến môi trường `GRADLE_HOME` đã được thiết lập đúng.
3. **Kiểm tra cài đặt:** Mở terminal và chạy lệnh sau để đảm bảo Gradle và JDK đã được cài đặt thành công:
    ```bash
    gradle -v
    java -version
    ```

## Hướng dẫn khởi chạy
1. **Clone repository:**
    ```bash
    git clone <repository-url>
    cd <repository-name>
    ```
2. **Cấu hình file `application.properties`:** Điều chỉnh các thông tin cấu hình phù hợp trong `src/main/resources/application.properties`, bao gồm kết nối cơ sở dữ liệu, cổng và các tham số khác.
3. **Cài đặt các phụ thuộc:** Chạy lệnh sau để cài đặt các phụ thuộc cần thiết:
    ```bash
    gradle build
    ```
4. **Chạy ứng dụng:** Sử dụng Gradle để khởi chạy ứng dụng Spring Boot bằng lệnh sau:
    ```bash
    gradle bootRun
    ```
    Hoặc có thể build thành file `.jar` trước rồi chạy:
    ```bash
    gradle clean build
    java -jar build/libs/<project-name>-0.0.1-SNAPSHOT.jar
    ```
5. **Truy cập ứng dụng:** Ứng dụng sẽ chạy mặc định trên cổng 8080. Bạn có thể truy cập tại:
    ```arduino
    http://localhost:8080
    ```

## Các lệnh Gradle phổ biến
- **Chạy ứng dụng:** `gradle bootRun`
- **Build dự án:** `gradle clean build`
- **Kiểm tra lỗi code (Linter):** `gradle check`
- **Kiểm tra và chạy các bài kiểm thử:** `gradle test`
- **Cập nhật các phụ thuộc:** `gradle dependencyUpdates`

## Các tài liệu tham khảo
- [Spring Framework Documentation](https://spring.io/projects/spring-framework)
- [Spring Data JPA Documentation](https://spring.io/projects/spring-data-jpa)
- [Spring Security Documentation](https://spring.io/projects/spring-security)
- [Spring Web Documentation](https://spring.io/guides/gs/serving-web-content/)
- [Maven Repository](https://mvnrepository.com/)
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Kotlin Documentation](https://kotlinlang.org/docs/reference/)
- [Gradle Documentation](https://docs.gradle.org/current/userguide/userguide.html)
- [Spring Boot Gradle Plugin Documentation](https://docs.spring.io/spring-boot/docs/current/gradle-plugin/reference/html/)
- [Checkstyle Gradle Plugin Documentation](https://docs.gradle.org/current/userguide/checkstyle_plugin.html)

