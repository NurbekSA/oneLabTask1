package controller;

import org.example.Main;
import org.example.config.SecurityConfig;
import org.example.config.jwt.JwtUtil;
import org.example.model.AuthRequest;
import org.example.model.OrderModel;
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
import org.example.controller.OrderController;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Main.class)
@ContextConfiguration(classes = {OrderController.class, JwtUtil.class, SecurityConfig.class})
@ComponentScan(basePackages = "org.example")
public class OrderControllerIntegrationTest {

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
    public void testGetAllOrders() {
        String url = "http://localhost:" + port + "/order";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", jwtToken);
        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<OrderModel[]> response = restTemplate.exchange(url, HttpMethod.GET, request, OrderModel[].class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void testGetOrderById() {
        Long orderId = 1L; // Replace with an actual order ID if available in your test data
        String url = "http://localhost:" + port + "/order/" + orderId;

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", jwtToken);
        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<OrderModel> response = restTemplate.exchange(url, HttpMethod.GET, request, OrderModel.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    @Transactional
    public void testCreateOrder() {
        String url = "http://localhost:" + port + "/order";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", jwtToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        OrderModel orderModel = new OrderModel();
        orderModel.setIsActive(true);
        orderModel.setIsAlive(true);
        orderModel.setInvestmentType("Equity");
        orderModel.setTargetAmount(new BigDecimal("100000.00"));
        orderModel.setActualAmount(new BigDecimal("50000.00"));
        orderModel.setCurrency("USD");
        orderModel.setDateOfOrder(System.currentTimeMillis());
        orderModel.setDueDate(System.currentTimeMillis() + 86400000L); // +1 day
        orderModel.setPurpose("Expand business operations");
        orderModel.setDescription("Order for expanding retail operations");
        orderModel.setCollateral("Company assets");

        HttpEntity<OrderModel> request = new HttpEntity<>(orderModel, headers);

        ResponseEntity<?> response = restTemplate.exchange(url, HttpMethod.POST, request, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }
}
