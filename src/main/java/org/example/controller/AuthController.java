package org.example.controller;

import org.example.persistence.model.dto.RegistrationRequestDTO;
import org.example.persistence.model.entity.SimpleUser;
import org.example.persistence.service.SimpleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private SimpleUserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegistrationRequestDTO request) {
        userService.registerUser(request);
        return ResponseEntity.ok("User registered successfully");
    }
}
