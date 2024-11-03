package controller;


import org.example.Main;
import org.example.config.SecurityConfig;
import org.example.config.jwt.JwtUtil;
import org.example.entity.model.AuthRequest;
import org.example.entity.model.InvestorModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.example.controller.InvestorController;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Main.class)
@ContextConfiguration(classes = {InvestorController.class, JwtUtil.class, SecurityConfig.class})
@ComponentScan(basePackages = "org.example")
public class InvestorControllerIntegrationTest {

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

        List<String> cookies = response.getHeaders().get("Set-Cookie");
        jwtToken = cookies.stream()
                .filter(cookie -> cookie.startsWith("jwtToken"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("JWT Token not found"));
    }

    @Test
    public void testGetInvestorById() {
        Long investorId = 1L; // Replace with an actual investor ID if available in your test data
        String url = "http://localhost:" + port + "/investor/" + investorId;

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", jwtToken);
        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<InvestorModel> response = restTemplate.exchange(url, HttpMethod.GET, request, InvestorModel.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    @Transactional
    public void testCreateInvestor() {
        String url = "http://localhost:" + port + "/investor";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", jwtToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        InvestorModel investorModel = new InvestorModel();
        investorModel.setMail("investor@example.com");
        investorModel.setPhoneNumber("1234567890");
        investorModel.setIsChecked(true);
        investorModel.setScore(new BigDecimal("7500.00"));
        investorModel.setIin("123456789012");
        investorModel.setFio("John Doe");
        investorModel.setAddress("123 Investment St");
        investorModel.setInvestorType("Individual");
        investorModel.setCreatedAt(System.currentTimeMillis());
        investorModel.setUpdatedAt(System.currentTimeMillis());

        HttpEntity<InvestorModel> request = new HttpEntity<>(investorModel, headers);

        ResponseEntity<?> response = restTemplate.exchange(url, HttpMethod.POST, request, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }
}
