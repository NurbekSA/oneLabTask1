package service;//import org.example.kafka.KafkaSender;
import org.example.model.InvestmentModel;
import org.example.model.InvestorModel;
import org.example.model.OrderModel;
import org.example.repository.InvestmentRepo;
import org.example.service.InvestmentService;
import org.example.service.InvestorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class InvestmentServiceTest {

    @Mock
    private InvestmentRepo investmentRepo;

    @Mock
    private InvestorService investorService;



    @InjectMocks
    private InvestmentService investmentService;

    private InvestmentModel investmentModel;
    private InvestorModel investorModel;
    private OrderModel orderModel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        investorModel = new InvestorModel();
        investorModel.setId(1L);
        investorModel.setIsChecked(true);

        orderModel = new OrderModel();
        orderModel.setId(1L);

        investmentModel = new InvestmentModel();
        investmentModel.setId(1L);
        investmentModel.setInvestor(investorModel);
        investmentModel.setOrder(orderModel);
        investmentModel.setAmount(BigDecimal.valueOf(1000));
        investmentModel.setIsPaid(false);
        investmentModel.setIsActive(false);
        investmentModel.setIsAlive(false);
    }

    @Test
    void testFindAll_ReturnsInvestments() {
        when(investmentRepo.findAll()).thenReturn(Collections.singletonList(investmentModel));

        ResponseEntity<?> response = investmentService.findAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, ((List<?>) response.getBody()).size());
        assertEquals(investmentModel, ((List<?>) response.getBody()).get(0));
    }

    @Test
    void testFindAll_NoInvestments() {
        when(investmentRepo.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = investmentService.findAll();
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    void testFindById_Found() {
        when(investmentRepo.findById(1L)).thenReturn(Optional.of(investmentModel));

        ResponseEntity<?> response = investmentService.findById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(investmentModel, response.getBody());
    }

    @Test
    void testFindById_NotFound() {
        when(investmentRepo.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<?> response = investmentService.findById(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    void testCreate_InvestorNotChecked() {
        investorModel.setIsChecked(false);
        when(investorService.findById(1L)).thenReturn(ResponseEntity.ok(investorModel));

        ResponseEntity<?> response = investmentService.create(investmentModel);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Инвестор не прошёл проверку.", response.getBody());
    }

    @Test
    void testCreate_InvestorNotFound() {
        when(investorService.findById(1L)).thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));

        ResponseEntity<?> response = investmentService.create(investmentModel);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Не удалось найти инвестора", response.getBody());
    }

    @Test
    void testUpdate_Found() {
        when(investmentRepo.findById(1L)).thenReturn(Optional.of(investmentModel));
        when(investmentRepo.save(any(InvestmentModel.class))).thenReturn(investmentModel);

        InvestmentModel updatedInvestment = new InvestmentModel();
        updatedInvestment.setId(1L);
        updatedInvestment.setIsActive(true);
        updatedInvestment.setAmount(BigDecimal.valueOf(2000));

        ResponseEntity<?> response = investmentService.update(1L, updatedInvestment);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(investmentModel, response.getBody());
        assertEquals(true, investmentModel.getIsActive());
        assertEquals(BigDecimal.valueOf(2000), investmentModel.getAmount());
    }

    @Test
    void testUpdate_NotFound() {
        when(investmentRepo.findById(1L)).thenReturn(Optional.empty());

        InvestmentModel updatedInvestment = new InvestmentModel();
        ResponseEntity<?> response = investmentService.update(1L, updatedInvestment);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Не удалось найти инвестицию  с айди1", response.getBody());
    }

    @Test
    void testDelete_Found() {
        when(investmentRepo.existsById(1L)).thenReturn(true);

        ResponseEntity<?> response = investmentService.delete(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Успешно удалено", response.getBody());
        verify(investmentRepo, times(1)).deleteById(1L);
    }

    @Test
    void testDelete_NotFound() {
        when(investmentRepo.existsById(1L)).thenReturn(false);

        ResponseEntity<?> response = investmentService.delete(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Инвестиция не найдена", response.getBody());
    }
}
