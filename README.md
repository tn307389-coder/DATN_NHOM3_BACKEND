# DriveHub - Backend (Quản lý trung tâm đào tạo lái xe)

API backend của hệ thống DriveHub, xây dựng bằng **Spring Boot 3.3.5 + Java 23**, cơ sở dữ liệu **SQL Server**.

## Yêu cầu

- JDK 17+ (khuyến nghị 21)
- Maven (hoặc dùng `mvnw.cmd` đi kèm)
- SQL Server (mặc định `MSI:1433`, database `duantotnghiep01`)

## Cài đặt và chạy

1. Khởi tạo database bằng file `duantotnghiep01.sql` ở thư mục gốc đồ án.
2. Cấu hình kết nối DB + JWT + email trong `src/main/resources/application.properties` (có thể ghi đè qua biến môi trường).
3. Chạy:

```bash
mvnw.cmd spring-boot:run
```

Server khởi động tại `http://localhost:8081`.

## Biến môi trường (tùy chọn)

| Biến | Mô tả |
|---|---|
| `JWT_SECRET` | Secret ký JWT (HS256, >= 32 ký tự) |
| `MAIL_USERNAME` | Email gửi OTP (Gmail) |
| `MAIL_PASSWORD` | Mật khẩu ứng dụng Gmail |
| `GOOGLE_CLIENT_ID` | Client ID Google OAuth |

Nếu không đặt, hệ thống dùng giá trị mặc định trong `application.properties`.

## API Docs (Swagger)

Sau khi chạy, truy cập:

- Swagger UI: `http://localhost:8081/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8081/api-docs`

## Bảo mật

- **JWT**: Access token (24h) + Refresh token (7 ngày), endpoint `POST /api/refresh`.
- **Khóa tài khoản**: tự khóa sau 5 lần nhập sai mật khẩu, mở khóa sau 15 phút.
- **Đổi mật khẩu**: `PUT /api/tai-khoan/me/doi-mat-khau`.
- **Nhật ký hệ thống**: ghi tự động qua `@LogAction` (AOP) cho các thao tác ghi.
- Phân quyền theo vai trò **ADMIN / NV / GV / HV** trong `config/SecurityConfig.java`.

## Cấu trúc

```
src/main/java/.../datn_nhom3_backend/
├── aspect/       # LogActionAspect (AOP ghi nhật ký)
├── config/       # Security, JwtUtil, CORS, JwtAuthenticationFilter
├── controller/   # REST controllers
├── dto/          # Request/Response DTO
├── entity/       # JPA entities
├── repository/   # Spring Data repositories
├── service/      # Business logic
└── exception/    # Xử lý ngoại lệ
```

## Kiểm thử

- Test đơn vị (JUnit 5, H2): `mvnw.cmd test`
- Test tích hợp (IT, chạy với H2 profile `test`): `mvnw.cmd test -Dtest="*IT"`

## Tính năng chính

- Quản lý: khóa học, lớp, lịch học, lịch thi, điểm danh batch, bảng điểm thường xuyên, thi sát hạch, kết quả thi, phòng/ca, xe & bảo trì, phân công, tin tức, thông báo, tài khoản.
- Đăng ký khóa học công khai + gửi OTP qua email + tra cứu hồ sơ.
- Thanh toán QR (mô phỏng), WebSocket chatbot tư vấn, upload/duyệt ảnh đại diện, dashboard thống kê.
- Đăng nhập Google (OAuth2 ID token).
