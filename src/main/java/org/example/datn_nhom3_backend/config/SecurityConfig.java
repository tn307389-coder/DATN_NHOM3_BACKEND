package org.example.datn_nhom3_backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtUtil jwtUtil;

    @Value("${jwt.header}")
    private String jwtHeader;

    @Value("${jwt.prefix}")
    private String jwtPrefix;

    public SecurityConfig(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    // Quyền theo phương thức HTTP cho từng endpoint (/api/<path>)
    // GET: đọc dữ liệu. WRITE (POST/PUT/DELETE): thao tác.
    private static final Map<String, String[]> GET_ROLES = new HashMap<>();
    private static final Map<String, String[]> WRITE_ROLES = new HashMap<>();

    static {
        String[] all = {"ADMIN", "NV", "GV", "HV"};
        String[] adminOnly = {"ADMIN"};
        String[] an = {"ADMIN", "NV"};
        String[] ag = {"ADMIN", "GV"};
        String[] ang = {"ADMIN", "NV", "GV"};

        // Các endpoint cho phép tất cả vai tròng được XEM (trừ 5 endpoint chỉ ADMIN)
        for (String p : new String[]{
                "hoc-vien", "ho-so-hoc-vien", "giao-vien", "chuong-trinh-hoc",
                "khoa-hoc", "dang-ky-khoa-hoc", "lop-hoc", "mon-hoc", "phong-hoc",
                "xe-tap-lai", "xe", "phan-cong", "ca-hoc", "lich-hoc", "diem-danh",
                "bang-diem-thuong-xuyen", "phong-thi", "ca-thi", "lich-thi",
                "ket-qua-thi", "thi-sat-hach", "hang-gplx", "thong-bao",
                "lich-su-bao-tri-xe"}) {
            GET_ROLES.put(p, all);
        }
        // 5 endpoint chỉ ADMIN được xem
        for (String p : new String[]{
                "dashboard", "tai-khoan", "thanh-toan", "tra-gplx", "nhat-ky-he-thong"}) {
            GET_ROLES.put(p, adminOnly);
        }

        WRITE_ROLES.put("hoc-vien", an);
        WRITE_ROLES.put("ho-so-hoc-vien", an);
        WRITE_ROLES.put("giao-vien", an);
        WRITE_ROLES.put("chuong-trinh-hoc", an);
        WRITE_ROLES.put("khoa-hoc", an);
        WRITE_ROLES.put("dang-ky-khoa-hoc", an);
        WRITE_ROLES.put("lop-hoc", an);
        WRITE_ROLES.put("mon-hoc", an);
        WRITE_ROLES.put("phong-hoc", an);
        WRITE_ROLES.put("xe-tap-lai", an);
        WRITE_ROLES.put("xe", an);
        WRITE_ROLES.put("phan-cong", an);
        WRITE_ROLES.put("ca-hoc", an);
        WRITE_ROLES.put("lich-hoc", an);
        WRITE_ROLES.put("diem-danh", ag);
        WRITE_ROLES.put("bang-diem-thuong-xuyen", ag);
        WRITE_ROLES.put("phong-thi", an);
        WRITE_ROLES.put("ca-thi", ang);
        WRITE_ROLES.put("lich-thi", ang);
        WRITE_ROLES.put("ket-qua-thi", adminOnly);
        WRITE_ROLES.put("thi-sat-hach", ang);
        WRITE_ROLES.put("hang-gplx", an);
        WRITE_ROLES.put("thong-bao", an);
        WRITE_ROLES.put("tin-tuc", an);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtUtil, jwtHeader, jwtPrefix);

        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .authorizeHttpRequests(auth -> {
                auth.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
                auth.requestMatchers("/api/login", "/api/logout", "/api/refresh").permitAll();
                auth.requestMatchers(HttpMethod.POST, "/api/login/google").permitAll();
                auth.requestMatchers(HttpMethod.POST, "/api/quen-mat-khau/**").permitAll();
                auth.requestMatchers(HttpMethod.POST, "/api/dang-ky-khoa-hoc/public").permitAll();
                auth.requestMatchers(HttpMethod.POST, "/api/dang-ky-khoa-hoc/send-otp").permitAll();
                auth.requestMatchers(HttpMethod.POST, "/api/dang-ky-khoa-hoc/verify-otp").permitAll();
                auth.requestMatchers(HttpMethod.POST, "/api/dang-ky-khoa-hoc/tra-cuu").permitAll();
                auth.requestMatchers(HttpMethod.GET, "/api/tin-tuc/**").permitAll();
                auth.requestMatchers(HttpMethod.GET, "/api/danh-muc/**").permitAll();
                auth.requestMatchers(HttpMethod.GET, "/api/khoa-hoc/**").permitAll();
                auth.requestMatchers(HttpMethod.GET, "/api/hang-gplx/**").permitAll();
                auth.requestMatchers(HttpMethod.GET, "/api/hoc-vien/me/**").hasAnyRole("ADMIN", "NV", "GV", "HV");
                auth.requestMatchers(HttpMethod.GET, "/api/giao-vien/me/**").hasAnyRole("ADMIN", "NV", "GV");
                auth.requestMatchers(HttpMethod.GET, "/api/tai-khoan/me").hasAnyRole("ADMIN", "NV", "GV", "HV");
                auth.requestMatchers(HttpMethod.PUT, "/api/tai-khoan/me").hasAnyRole("ADMIN", "NV", "GV", "HV");
                auth.requestMatchers(HttpMethod.PUT, "/api/tai-khoan/me/doi-mat-khau").hasAnyRole("ADMIN", "NV", "GV", "HV");
                auth.requestMatchers(HttpMethod.GET, "/api/dashboard/**").hasAnyRole("ADMIN", "NV");
                auth.requestMatchers(HttpMethod.POST, "/api/upload/avatar").hasAnyRole("ADMIN", "NV", "GV", "HV");
                auth.requestMatchers(HttpMethod.POST, "/api/upload/tin-tuc").hasAnyRole("ADMIN", "NV");
                auth.requestMatchers(HttpMethod.POST, "/api/anh-cho-duyet").hasAnyRole("ADMIN", "NV", "GV", "HV");
                auth.requestMatchers(HttpMethod.GET, "/api/anh-cho-duyet/cua-toi").hasAnyRole("ADMIN", "NV", "GV", "HV");
                auth.requestMatchers(HttpMethod.GET, "/api/anh-cho-duyet").hasAnyRole("ADMIN", "NV");
                auth.requestMatchers(HttpMethod.PUT, "/api/anh-cho-duyet/**").hasAnyRole("ADMIN", "NV");
                auth.requestMatchers("/api/files/**").permitAll();
                auth.requestMatchers("/ws/**").permitAll();
                auth.requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/api-docs/**", "/v3/api-docs/**").permitAll();

                // Thanh toán QR: HV đã đăng nhập mới được khởi tạo / xem trạng thái / xác nhận
                auth.requestMatchers(HttpMethod.POST, "/api/thanh-toan/khoi-tao").hasAnyRole("ADMIN", "NV", "HV");
                auth.requestMatchers(HttpMethod.POST, "/api/thanh-toan/xac-nhan/**").hasAnyRole("ADMIN", "NV", "HV");
                auth.requestMatchers(HttpMethod.GET, "/api/thanh-toan/*/trang-thai").hasAnyRole("ADMIN", "NV", "HV");
                auth.requestMatchers(HttpMethod.GET, "/api/thanh-toan/*/qr").hasAnyRole("ADMIN", "NV", "HV");

                // Dịch vụ học viên: lịch sử thanh toán + đăng ký học lại
                auth.requestMatchers(HttpMethod.GET, "/api/thanh-toan/me").hasAnyRole("ADMIN", "NV", "HV");
                auth.requestMatchers(HttpMethod.POST, "/api/dang-ky-khoa-hoc/me/dang-ky-lai").hasAnyRole("ADMIN", "NV", "HV");

                // Email thông báo: chỉ Admin / Nhân viên mới được gửi
                auth.requestMatchers("/api/email/**").hasAnyRole("ADMIN", "NV");

                GET_ROLES.forEach((path, roles) ->
                    auth.requestMatchers(HttpMethod.GET, "/api/" + path + "/**").hasAnyRole(roles));

                WRITE_ROLES.forEach((path, roles) -> {
                    auth.requestMatchers(HttpMethod.POST, "/api/" + path + "/**").hasAnyRole(roles);
                    auth.requestMatchers(HttpMethod.PUT, "/api/" + path + "/**").hasAnyRole(roles);
                    auth.requestMatchers(HttpMethod.DELETE, "/api/" + path + "/**").hasAnyRole(roles);
                });

                // Mọi phương thức khác (kể cả write với endpoint chỉ ADMIN) -> chỉ ADMIN
                auth.anyRequest().hasRole("ADMIN");
            })
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.getWriter().write("{\"success\":false,\"message\":\"Chưa xác thực hoặc phiên đã hết hạn\"}");
                })
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    response.getWriter().write("{\"success\":false,\"message\":\"Bạn không có quyền truy cập tài nguyên này\"}");
                }))
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:5173", "http://127.0.0.1:5173", "http://[::1]:5173"));
        configuration.setAllowedMethods(Arrays.asList(
                "GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept", "X-Requested-With"));
        configuration.setExposedHeaders(List.of("Authorization", "Content-Disposition"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
