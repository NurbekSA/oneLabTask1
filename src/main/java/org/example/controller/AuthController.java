package org.example.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.example.security.jwt.JwtUtil;
import org.example.persistence.model.entity.SimpleUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody SimpleUser authRequest, HttpServletResponse response) {
        logger.info("Login attempt for user: {}", authRequest.getUsername());

        // Authenticate the user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        logger.info("Authentication successful for user: {}", authRequest.getUsername());

        // Generate JWT token
        String token = jwtUtil.generateToken(authRequest.getUsername());
        logger.debug("Generated JWT token for user: {}", authRequest.getUsername());

        // Create and add JWT cookie
        Cookie jwtCookie = new Cookie("jwtToken", token);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true); // Use only with HTTPS
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(24 * 60 * 60); // Token expiration (1 day)

        response.addCookie(jwtCookie);
        logger.info("JWT cookie set for user: {}", authRequest.getUsername());

        return ResponseEntity.ok("Login successful");


    }
}
