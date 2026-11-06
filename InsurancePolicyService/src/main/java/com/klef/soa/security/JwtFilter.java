package com.klef.soa.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        boolean isPublicEndpoint = path.equals("/insurancepolicy")
                || path.equals("/insurancepolicy/")
                || path.equals("/insurancepolicy/test")
                || path.equals("/insurancepolicy/login")
                || path.startsWith("/swagger-ui")
                || path.startsWith("/v3/api-docs")
                || path.startsWith("/actuator");

        // Public endpoints bypass security check
        if (isPublicEndpoint) {
            chain.doFilter(request, response);
            return;
        }

        // Read Authorization header
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            sendErrorResponse(
                    response,
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Authorization header is missing or invalid"
            );
            return;
        }

        String token = header.substring(7).trim();

        if (!jwtUtil.validateToken(token)) {
            sendErrorResponse(
                    response,
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Invalid or expired token"
            );
            return;
        }

        chain.doFilter(request, response);
    }

    private void sendErrorResponse(
            HttpServletResponse response,
            int status,
            String message)
            throws IOException {

        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String jsonResponse = "{\"error\":\"Unauthorized\",\"message\":\"" + message + "\"}";
        response.getWriter().write(jsonResponse);
    }
}
