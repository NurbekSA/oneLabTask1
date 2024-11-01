package org.example.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import org.example.model.AuthRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.config.jwt.JwtUtil;
@RestController
@RequestMapping("auth")
public class AuthController {
    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping
    public String get(){
        return "Hello from Nurbek";
    }


    @PostMapping
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest, HttpServletResponse response) {

        String token = jwtUtil.generateToken(authRequest.getUsername());

        Cookie jwtCookie = new Cookie("jwtToken", token);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true); // Используйте, если приложение работает по HTTPS
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(24 * 60 * 60); // Устанавливаем срок действия токена (1 день)

        response.addCookie(jwtCookie);

        return ResponseEntity.ok("Login successful");
    }
}


