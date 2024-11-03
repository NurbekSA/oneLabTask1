//package service;//import org.example.kafka.KafkaSender;
//import org.example.model.InvestmentModel;
//import org.example.model.InvestorModel;
//import org.example.model.OrderModel;
//import org.example.repository.InvestmentRepo;
//import org.example.service.InvestmentService;
//import org.example.service.InvestorService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.math.BigDecimal;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//class InvestmentServiceTest {
//
//    @Mock
//    private InvestmentRepo investmentRepo;
//
//    @Mock
//    private InvestorService investorService;
//
//
//
//    @InjectMocks
//    private InvestmentService investmentService;
//
//    private InvestmentModel investmentModel;
//    private InvestorModel investorModel;
//    private OrderModel orderModel;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//
//        investorModel = new InvestorModel();
//        investorModel.setId(1L);
//        investorModel.setIsChecked(true);
//
//        orderModel = new OrderModel();
//        orderModel.setId(1L);
//
//        investmentModel = new InvestmentModel();
//        investmentModel.setId(1L);
//        investmentModel.setInvestor(investorModel);
//        investmentModel.setOrder(orderModel);
//        investmentModel.setAmount(BigDecimal.valueOf(1000));
//        investmentModel.setIsPaid(false);
//        investmentModel.setIsActive(false);
//        investmentModel.setIsAlive(false);
//    }
//
//    @Test
//    void testFindAll_ReturnsInvestments() {
//        when(investmentRepo.findAll()).thenReturn(Collections.singletonList(investmentModel));
//
//        ResponseEntity<?> response = investmentService.findAll();
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(1, ((List<?>) response.getBody()).size());
//        assertEquals(investmentModel, ((List<?>) response.getBody()).get(0));
//    }
//
//    @Test
//    void testFindAll_NoInvestments() {
//        when(investmentRepo.findAll()).thenReturn(Collections.emptyList());
//
//        ResponseEntity<?> response = investmentService.findAll();
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//        assertEquals(null, response.getBody());
//    }
//
//    @Test
//    void testFindById_Found() {
//        when(investmentRepo.findById(1L)).thenReturn(Optional.of(investmentModel));
//
//        ResponseEntity<?> response = investmentService.findById(1L);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(investmentModel, response.getBody());
//    }
//
//    @Test
//    void testFindById_NotFound() {
//        when(investmentRepo.findById(1L)).thenReturn(Optional.empty());
//
//        ResponseEntity<?> response = investmentService.findById(1L);
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//        assertEquals(null, response.getBody());
//    }
//
//    @Test
//    void testCreate_InvestorNotChecked() {
//        investorModel.setIsChecked(false);
//        when(investorService.findById(1L)).thenReturn(ResponseEntity.ok(investorModel));
//
//        ResponseEntity<?> response = investmentService.create(investmentModel);
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//        assertEquals("Инвестор не прошёл проверку.", response.getBody());
//    }
//
//    @Test
//    void testCreate_InvestorNotFound() {
//        when(investorService.findById(1L)).thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
//
//        ResponseEntity<?> response = investmentService.create(investmentModel);
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//        assertEquals("Не удалось найти инвестора", response.getBody());
//    }
//
//    @Test
//    void testUpdate_Found() {
//        when(investmentRepo.findById(1L)).thenReturn(Optional.of(investmentModel));
//        when(investmentRepo.save(any(InvestmentModel.class))).thenReturn(investmentModel);
//
//        InvestmentModel updatedInvestment = new InvestmentModel();
//        updatedInvestment.setId(1L);
//        updatedInvestment.setIsActive(true);
//        updatedInvestment.setAmount(BigDecimal.valueOf(2000));
//
//        ResponseEntity<?> response = investmentService.update(1L, updatedInvestment);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(investmentModel, response.getBody());
//        assertEquals(true, investmentModel.getIsActive());
//        assertEquals(BigDecimal.valueOf(2000), investmentModel.getAmount());
//    }
//
//    @Test
//    void testUpdate_NotFound() {
//        when(investmentRepo.findById(1L)).thenReturn(Optional.empty());
//
//        InvestmentModel updatedInvestment = new InvestmentModel();
//        ResponseEntity<?> response = investmentService.update(1L, updatedInvestment);
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//        assertEquals("Не удалось найти инвестицию  с айди1", response.getBody());
//    }
//
//    @Test
//    void testDelete_Found() {
//        when(investmentRepo.existsById(1L)).thenReturn(true);
//
//        ResponseEntity<?> response = investmentService.delete(1L);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("Успешно удалено", response.getBody());
//        verify(investmentRepo, times(1)).deleteById(1L);
//    }
//
//    @Test
//    void testDelete_NotFound() {
//        when(investmentRepo.existsById(1L)).thenReturn(false);
//
//        ResponseEntity<?> response = investmentService.delete(1L);
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//        assertEquals("Инвестиция не найдена", response.getBody());
//    }
//}





//package org.example.service;
//
//import org.example.model.InvestmentModel;
//import org.example.model.InvestorModel;
//import org.example.model.exception.ResourceNotFoundException;
//import org.example.repository.InvestmentRepo;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.slf4j.Logger;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class InvestmentServiceTest {
//
//    @Mock
//    private InvestmentRepo investmentRepo;
//
//    @Mock
//    private InvestorService investorService;
//
//    @Mock
//    private Logger logger;
//
//    @InjectMocks
//    private InvestmentService investmentService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testFindAll_whenInvestmentsExist_shouldReturnInvestments() {
//        List<InvestmentModel> investments = new ArrayList<>();
//        investments.add(new InvestmentModel());
//        when(investmentRepo.findAll()).thenReturn(investments);
//
//        List<InvestmentModel> result = investmentService.findAll();
//
//        assertNotNull(result);
//        assertFalse(result.isEmpty());
//        verify(investmentRepo, times(1)).findAll();
//    }
//
//    @Test
//    void testFindAll_whenNoInvestmentsExist_shouldThrowException() {
//        when(investmentRepo.findAll()).thenReturn(new ArrayList<>());
//
//        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> investmentService.findAll());
//        assertEquals("No investments found", exception.getMessage());
//        verify(investmentRepo, times(1)).findAll();
//    }
//
//    @Test
//    void testFindById_whenInvestmentExists_shouldReturnInvestment() {
//        Long id = 1L;
//        InvestmentModel investment = new InvestmentModel();
//        when(investmentRepo.findById(id)).thenReturn(Optional.of(investment));
//
//        InvestmentModel result = investmentService.findById(id);
//
//        assertNotNull(result);
//        verify(investmentRepo, times(1)).findById(id);
//    }
//
//    @Test
//    void testFindById_whenInvestmentDoesNotExist_shouldThrowException() {
//        Long id = 1L;
//        when(investmentRepo.findById(id)).thenReturn(Optional.empty());
//
//        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> investmentService.findById(id));
//        assertEquals("Investment with id " + id + " not found", exception.getMessage());
//        verify(investmentRepo, times(1)).findById(id);
//    }
//
//    @Test
//    void testCreate_whenInvestorIsVerified_shouldSaveAndReturnInvestment() {
//        Long investorId = 1L;
//        InvestorModel investor = new InvestorModel();
//        investor.setId(investorId);
//        investor.setIsChecked(true);
//        investor.setCards(new Object());  // Mocking the presence of a linked card
//
//        InvestmentModel investment = new InvestmentModel();
//        investment.setInvestor(investor);
//
//        when(investorService.findById(investorId)).thenReturn(investor);
//        when(investmentRepo.save(investment)).thenReturn(investment);
//
//        InvestmentModel result = investmentService.create(investment);
//
//        assertNotNull(result);
//        assertFalse(result.getIsPaid());
//        assertFalse(result.getIsActive());
//        assertFalse(result.getIsAlive());
//        verify(investorService, times(1)).findById(investorId);
//        verify(investmentRepo, times(1)).save(investment);
//        verify(logger, times(1)).info("Investment successfully saved to the database");
//    }
//
//    @Test
//    void testCreate_whenInvestorNotVerified_shouldThrowException() {
//        InvestorModel investor = new InvestorModel();
//        investor.setId(1L);
//        investor.setIsChecked(false);
//
//        InvestmentModel investment = new InvestmentModel();
//        investment.setInvestor(investor);
//
//        when(investorService.findById(investor.getId())).thenReturn(investor);
//
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> investmentService.create(investment));
//        assertEquals("Investor has not passed verification.", exception.getMessage());
//        verify(investorService, times(1)).findById(investor.getId());
//        verify(investmentRepo, never()).save(investment);
//    }
//
//    @Test
//    void testUpdate_whenInvestmentExists_shouldUpdateAndReturnInvestment() {
//        Long id = 1L;
//        InvestmentModel existingInvestment = new InvestmentModel();
//        InvestmentModel updatedInvestment = new InvestmentModel();
//        updatedInvestment.setIsAlive(true);
//        updatedInvestment.setIsActive(true);
//        updatedInvestment.setAmount(1000.0);
//
//        when(investmentRepo.findById(id)).thenReturn(Optional.of(existingInvestment));
//        when(investmentRepo.save(existingInvestment)).thenReturn(existingInvestment);
//
//        InvestmentModel result = investmentService.update(id, updatedInvestment);
//
//        assertNotNull(result);
//        assertEquals(updatedInvestment.getAmount(), result.getAmount());
//        assertEquals(updatedInvestment.getIsAlive(), result.getIsAlive());
//        assertEquals(updatedInvestment.getIsActive(), result.getIsActive());
//        verify(investmentRepo, times(1)).findById(id);
//        verify(investmentRepo, times(1)).save(existingInvestment);
//    }
//
//    @Test
//    void testDelete_whenInvestmentExists_shouldDeleteInvestment() {
//        Long id = 1L;
//        when(investmentRepo.existsById(id)).thenReturn(true);
//
//        investmentService.delete(id);
//
//        verify(investmentRepo, times(1)).existsById(id);
//        verify(investmentRepo, times(1)).deleteById(id);
//    }
//
//    @Test
//    void testDelete_whenInvestmentDoesNotExist_shouldThrowException() {
//        Long id = 1L;
//        when(investmentRepo.existsById(id)).thenReturn(false);
//
//        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> investmentService.delete(id));
//        assertEquals("Investment with id " + id + " not found", exception.getMessage());
//        verify(investmentRepo, times(1)).existsById(id);
//        verify(investmentRepo, never()).deleteById(id);
//    }
//}
