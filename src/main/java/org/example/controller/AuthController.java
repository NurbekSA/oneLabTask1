package org.example.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.example.entity.model.AuthRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.config.jwt.JwtUtil;
@RestController
@RequestMapping("auth")
@AllArgsConstructor
public class AuthController {

    private JwtUtil jwtUtil;

    @GetMapping
    public String get(){
        return "Hello from Nurbek";
    }


    @PostMapping
    public ResponseEntity<String> login(@RequestBody AuthRequest authRequest, HttpServletResponse response) {

        String token = jwtUtil.generateToken(authRequest.getUsername());

        Cookie jwtCookie = new Cookie("jwtToken", token);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(2 * 60 * 60);

        response.addCookie(jwtCookie);

        return ResponseEntity.ok("Login successful");
    }
}


