
## Về tác giả
- **Họ và tên:** Võ Quốc Việt
- **Note:** Đây là bài tập cá nhân của mình, nếu bạn có nhu cầu sử dụng, vui lòng liên hệ mình qua email:
- **Email:** voviet266@gmail.com

## Yêu cầu hệ thống
- **JDK:** 11 hoặc cao hơn
- **Maven:** 3.6.3 hoặc cao hơn

## Cấu trúc dự án
- `src/main/kotlin`: Chứa mã nguồn chính của ứng dụng.
- `src/main/resources`: Chứa các file cấu hình, chẳng hạn như `application.properties`.
- `pom.xml`: File cấu hình Maven cho dự án.

## Thiết lập môi trường
1. **Cài đặt JDK:** Tải JDK từ Oracle hoặc sử dụng OpenJDK. Đảm bảo rằng biến môi trường `JAVA_HOME` đã được thiết lập đúng.
2. **Cài đặt Maven:** Tải và cài đặt Maven từ trang chủ Maven. Đảm bảo rằng biến môi trường `MAVEN_HOME` đã được thiết lập đúng.
3. **Kiểm tra cài đặt:** Mở terminal và chạy lệnh sau để đảm bảo Maven và JDK đã được cài đặt thành công:
    ```bash
    mvn -v
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
    mvn install
    ```
4. **Chạy ứng dụng:** Sử dụng Maven để khởi chạy ứng dụng Spring Boot bằng lệnh sau:
    ```bash
    mvn spring-boot:run
    ```
    Hoặc có thể build thành file `.jar` trước rồi chạy:
    ```bash
    mvn clean install
    java -jar target/<project-name>-0.0.1-SNAPSHOT.jar
    ```
5. **Truy cập ứng dụng:** Ứng dụng sẽ chạy mặc định trên cổng 8080. Bạn có thể truy cập tại:
    ```arduino
    http://localhost:8080
    ```

## Các lệnh Maven phổ biến
- **Chạy ứng dụng:** `mvn spring-boot:run`
- **Build dự án:** `mvn clean install`
- **Kiểm tra lỗi code (Linter):** `mvn checkstyle:check`
- **Kiểm tra và chạy các bài kiểm thử:** `mvn test`
- **Cập nhật các phụ thuộc:** `mvn versions:display-dependency-updates`

## Các tài liệu tham khảo
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Kotlin Documentation](https://kotlinlang.org/docs/reference/)
- [Maven Documentation](https://maven.apache.org/guides/index.html)
- [Spring Boot Maven Plugin Documentation](https://docs.spring.io/spring-boot/docs/current/maven-plugin/reference/html/)
- [Checkstyle Maven Plugin Documentation](https://maven.apache.org/plugins/maven-checkstyle-plugin/)
