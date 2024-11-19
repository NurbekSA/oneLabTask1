package org.example.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        logger.info("Starting JwtFilter for request: {}", request.getRequestURI());

        String token = null;
        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            chain.doFilter(request, response);
        }

        for (Cookie cookie : cookies) {
            logger.info("Inspecting cookie: {}={}", cookie.getName(), cookie.getValue());
            if ("jwtToken".equals(cookie.getName())) {
                token = cookie.getValue();
                logger.info("JWT token found in cookie: {}", token);
                break;
            }
        }

        String username = "";
        try {
            username = jwtUtil.extractUsername(token);
            logger.info("Extracted username from token: {}", username);
        } catch (Exception e) {
            logger.error("Error occurred while processing the token: {}", e.getMessage(), e);
        }


        if (username != "" && SecurityContextHolder.getContext().getAuthentication() == null) {
            logger.info("No existing authentication found. Validating token.");

            if (jwtUtil.isTokenValid(token)) {
                logger.info("Token is valid. Setting authentication for user: {}", username);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                logger.warn("Token validation failed for token: {}", token);
            }
        } else {
            logger.info("Authentication already exists in the SecurityContext or username is null.");
        }

        logger.info("JwtFilter processing complete for request: {}", request.getRequestURI());
        chain.doFilter(request, response);
    }
}
