package controller;

import org.example.Main;
import org.example.config.SecurityConfig;
import org.example.config.jwt.JwtUtil;
import org.example.controller.AuthController;
import org.example.model.AuthRequest;
import org.example.model.BusinessModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Main.class)
@ContextConfiguration(classes = {AuthController.class, JwtUtil.class, SecurityConfig.class})
@ComponentScan(basePackages = "org.example")
public class BusinessControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String jwtToken;

    @BeforeEach
    public void setUp() {
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


        List<String> cookies = response.getHeaders().get("Set-Cookie");

        assertThat(cookies).isNotEmpty();

        jwtToken = cookies.stream()
                .filter(cookie -> cookie.startsWith("jwtToken"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("JWT Token not found"));

        System.out.println("4444");
    }




    @Test
    public void testGetAllBusinesses() {

        String url = "http://localhost:" + port + "/business";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", jwtToken);
        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<BusinessModel[]> response = restTemplate.exchange(url, HttpMethod.GET, request, BusinessModel[].class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
    }
}
