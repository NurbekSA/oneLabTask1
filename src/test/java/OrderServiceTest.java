import org.example.model.OrderModel;
import org.example.repository.OrderRepo;
import org.example.service.OrderService;
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
import static org.mockito.Mockito.when;

class OrderServiceTest {

    @Mock
    private OrderRepo orderRepo;

    @InjectMocks
    private OrderService orderService;

    private OrderModel orderModel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderModel = new OrderModel(null, null, null, true, true, "EQUITY", new BigDecimal("10000.00"), new BigDecimal("5000.00"), "USD", System.currentTimeMillis(), System.currentTimeMillis() + 604800000L, "PENDING", "Business Expansion", "Expansion of operations to new regions", "Real Estate", "null");
        orderModel.setId(1L);
        orderModel.setInvestmentType("Equity");
        orderModel.setTargetAmount(BigDecimal.valueOf(10000));
        orderModel.setActualAmount(BigDecimal.valueOf(5000));
        orderModel.setCurrency("USD");
        orderModel.setDateOfOrder(System.currentTimeMillis());
        orderModel.setDueDate(System.currentTimeMillis() + 86400000); // 1 день позже
        orderModel.setStatus("Pending");
        orderModel.setPurpose("Investing in startup");
        orderModel.setDescription("Initial order for investment");
        orderModel.setCollateral("None");
        orderModel.setIsActive(true);
        orderModel.setIsAlive(true);
    }

    @Test
    void testFindAll() {
        when(orderRepo.findAll()).thenReturn(Collections.singletonList(orderModel));

        ResponseEntity<List<OrderModel>> response = orderService.findAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(orderModel, response.getBody().get(0));
    }

    @Test
    void testFindById_Found() {
        when(orderRepo.findById(1L)).thenReturn(Optional.of(orderModel));

        ResponseEntity<OrderModel> response = orderService.findById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(orderModel, response.getBody());
    }

    @Test
    void testFindById_NotFound() {
        when(orderRepo.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<OrderModel> response = orderService.findById(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    void testCreate() {
        when(orderRepo.save(any(OrderModel.class))).thenReturn(orderModel);

        ResponseEntity<?> response = orderService.create(orderModel);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(orderModel, response.getBody());
    }
}
