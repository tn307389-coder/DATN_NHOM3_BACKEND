package org.example.datn_nhom3_backend.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final String jwtHeader;
    private final String jwtPrefix;

    private static final List<String> PUBLIC_PATHS = List.of(
        "/ws", "/api/login", "/api/logout", "/api/refresh", "/api/quen-mat-khau",
        "/api/dang-ky-khoa-hoc/public", "/api/dang-ky-khoa-hoc/send-otp",
        "/api/dang-ky-khoa-hoc/verify-otp", "/api/dang-ky-khoa-hoc/tra-cuu",
        "/api/tin-tuc", "/api/danh-muc", "/api/khoa-hoc", "/api/hang-gplx", "/api/files",
        "/api-docs", "/swagger-ui", "/v3/api-docs"
    );

    public JwtAuthenticationFilter(JwtUtil jwtUtil, String jwtHeader, String jwtPrefix) {
        this.jwtUtil = jwtUtil;
        this.jwtHeader = jwtHeader;
        this.jwtPrefix = jwtPrefix;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();
        for (String p : PUBLIC_PATHS) {
            if (path.startsWith(p)) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        String header = request.getHeader(jwtHeader);
        String token = null;

        if (header != null && header.startsWith(jwtPrefix + " ")) {
            token = header.substring((jwtPrefix + " ").length());
        }

        if (token != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (jwtUtil.isTokenValid(token)) {
                String username = jwtUtil.extractUsername(token);
                List<SimpleGrantedAuthority> authorities = jwtUtil.extractRoles(token).stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(username, null, authorities);
                authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}
