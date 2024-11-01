package service;

import org.example.model.InvestorModel;
import org.example.repository.InvestorRepo;
import org.example.service.InvestorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class InvestorServiceTest {

    @Mock
    private InvestorRepo investorRepo;

    @InjectMocks
    private InvestorService investorService;

    private InvestorModel investorModel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        investorModel = new InvestorModel();
        investorModel.setId(1L);
        investorModel.setIin("123456789012");
        investorModel.setFio("John Doe");
        investorModel.setPhoneNumber("1234567890");
        investorModel.setMail("john.doe@test.com");
        investorModel.setAddress("123 Test Street");
        investorModel.setInvestorType("Individual");
        investorModel.setScore(BigDecimal.valueOf(1000));
        investorModel.setCreatedAt(System.currentTimeMillis());
        investorModel.setUpdatedAt(System.currentTimeMillis());
    }

    @Test
    void testFindById_Found() {
        when(investorRepo.findById(1L)).thenReturn(Optional.of(investorModel));

        ResponseEntity<InvestorModel> response = investorService.findById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(investorModel, response.getBody());
    }

    @Test
    void testFindById_NotFound() {
        when(investorRepo.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<InvestorModel> response = investorService.findById(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    void testCreate() {
        when(investorRepo.save(any(InvestorModel.class))).thenReturn(investorModel);

        ResponseEntity<?> response = investorService.create(investorModel);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(investorModel, response.getBody());
    }
    @Test
    void testFindById_ReturnsCorrectData() {
        when(investorRepo.findById(1L)).thenReturn(Optional.of(investorModel));

        ResponseEntity<InvestorModel> response = investorService.findById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("123456789012", response.getBody().getIin());
        assertEquals("John Doe", response.getBody().getFio());
        assertEquals(BigDecimal.valueOf(1000), response.getBody().getScore());
    }

    @Test
    void testCreate_SetsTimestamps() {
        when(investorRepo.save(any(InvestorModel.class))).thenAnswer(invocation -> {
            InvestorModel model = invocation.getArgument(0);
            model.setCreatedAt(System.currentTimeMillis());
            model.setUpdatedAt(System.currentTimeMillis());
            return model;
        });

        ResponseEntity<?> response = investorService.create(investorModel);
        InvestorModel savedInvestor = (InvestorModel) response.getBody();
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(savedInvestor.getCreatedAt());
        assertNotNull(savedInvestor.getUpdatedAt());
    }

    @Test
    void testCreate_VerifySaveInvocation() {
        investorService.create(investorModel);
        verify(investorRepo).save(investorModel);
    }

    @Test
    void testFindById_InvalidId() {
        ResponseEntity<InvestorModel> response = investorService.findById(-1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testFindById_NullId() {
        ResponseEntity<InvestorModel> response = investorService.findById(null);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testScoreIsCorrectAfterCreation() {
        when(investorRepo.save(any(InvestorModel.class))).thenReturn(investorModel);

        ResponseEntity<?> response = investorService.create(investorModel);
        InvestorModel savedInvestor = (InvestorModel) response.getBody();
        assertEquals(BigDecimal.valueOf(1000), savedInvestor.getScore());
    }

}
