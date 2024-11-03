//package controller;
//
//import org.example.Main;
//import org.example.config.SecurityConfig;
//import org.example.config.jwt.JwtUtil;
//import org.example.controller.AuthController;
//import org.example.entity.model.AuthRequest;
//import org.example.entity.model.BusinessModel;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@ActiveProfiles("test")
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Main.class)
//@ContextConfiguration(classes = {AuthController.class, JwtUtil.class, SecurityConfig.class})
//@ComponentScan(basePackages = "org.example")
//public class BusinessControllerIntegrationTest {
//
//    @LocalServerPort
//    private int port;
//
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    private String jwtToken;
//
//    @BeforeEach
//    public void setUp() {
//        String url = "http://localhost:" + port + "/auth";
//
//        AuthRequest authRequest = new AuthRequest();
//        authRequest.setUsername("Nur");
//        authRequest.setPassword("12");
//
//        HttpHeaders headers = new HttpHeaders();
//        HttpEntity<AuthRequest> request = new HttpEntity<>(authRequest, headers);
//        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
//
//        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
//        assertThat(response.getHeaders().get("Set-Cookie")).isNotEmpty();
//        assertThat(response.getHeaders().get("Set-Cookie").get(0)).contains("jwtToken");
//
//
//        List<String> cookies = response.getHeaders().get("Set-Cookie");
//
//        assertThat(cookies).isNotEmpty();
//
//        jwtToken = cookies.stream()
//                .filter(cookie -> cookie.startsWith("jwtToken"))
//                .findFirst()
//                .orElseThrow(() -> new RuntimeException("JWT Token not found"));
//
//        System.out.println("4444");
//    }
//
//
//
//
////    @Test
////    public void testGetAllBusinesses() {
////
////        String url = "http://localhost:" + port + "/business";
////
////        HttpHeaders headers = new HttpHeaders();
////        headers.add("Cookie", jwtToken);
////        HttpEntity<Void> request = new HttpEntity<>(headers);
////
////        ResponseEntity<BusinessModel[]> response = restTemplate.exchange(url, HttpMethod.GET, request, BusinessModel[].class);
////
////        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
////        assertThat(response.getBody()).isNotNull();
////    }
//
//
//    @Test
//    @Transactional
//    public void testCreateBusiness() {
//        String url = "http://localhost:" + port + "/business";
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Cookie", jwtToken);
//
//        BusinessModel businessModel = new BusinessModel(null, null, "Example Corp", "123456789", "123 Business St", "Credit Card", "Technology", now, "John Doe", "123456789012", "+77011234567", "contact@example.com", now, now);
//        businessModel.setDirectorNumber("1234567890");
//        businessModel.setDirectorMail("director@example.com");
//        businessModel.setIsCheked("Yes");
//        businessModel.setDirectorFIO("John Doe");
//        businessModel.setDirectorIIN("123456789012");
//        businessModel.setCompanyName("Example Company");
//        businessModel.setCompanyBIN("123456789");
//        businessModel.setAddress("123 Example St");
//        businessModel.setTypeOfPaymentSystem("Credit Card");
//        businessModel.setSector("Retail");
//        businessModel.setDateOfBusinessStarted(System.currentTimeMillis());
//        businessModel.setCreatedAt(System.currentTimeMillis());
//        businessModel.setUpdatedAt(System.currentTimeMillis());
//
//        HttpEntity<BusinessModel> request = new HttpEntity<>(businessModel, headers);
//
//        ResponseEntity<?> response = restTemplate.exchange(url, HttpMethod.POST, request, Void.class);
//
//        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
//    }
//}
