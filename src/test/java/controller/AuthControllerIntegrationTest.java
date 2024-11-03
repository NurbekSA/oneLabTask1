package controller;

import org.example.Main;
import org.example.config.SecurityConfig;
import org.example.config.jwt.JwtUtil;
import org.example.controller.AuthController;
import org.example.entity.model.AuthRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Main.class)
@ContextConfiguration(classes = {AuthController.class, JwtUtil.class, SecurityConfig.class})
@ComponentScan(basePackages = "org.example")
public class AuthControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testLogin() {
        String url = "http://localhost:" + port + "/auth";

        AuthRequest authRequest = new AuthRequest();
        authRequest.setUsername("Nur");
        authRequest.setPassword("12");

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<AuthRequest> request = new HttpEntity<>(authRequest, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getHeaders().get("Set-Cookie")).isNotEmpty();
        assertThat(response.getHeaders().get("Set-Cookie").get(0)).contains("jwtToken");
    }
}