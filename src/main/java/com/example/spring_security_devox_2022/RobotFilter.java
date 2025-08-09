package com.example.spring_security_devox_2022;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

class RobotFilter extends OncePerRequestFilter {
    private static final String PASSWORD_HEADER_NAME = "x-robot-password";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        var password = request.getHeader(PASSWORD_HEADER_NAME);
        if (password == null) {
            filterChain.doFilter(request, response);
            return;
        } else if (!"beep-boop".equals(password)) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setHeader(HttpHeaders.CONTENT_TYPE, "text/plain;charset=utf-8");
            response.getWriter().println("You're not Mr Robot");
            return;
        }
        var newContext = SecurityContextHolder.createEmptyContext();
        newContext.setAuthentication(new RobotAuthentication());
        SecurityContextHolder.setContext(newContext);
        filterChain.doFilter(request, response);
        return;
    }
}
