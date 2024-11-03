package service;

import org.example.entity.model.OrderModel;
import org.example.entity.model.exception.ResourceNotFoundException;
import org.example.entity.repository.OrderRepo;
import org.example.entity.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private OrderRepo orderRepo;

    @InjectMocks
    private OrderService orderService;

    private OrderModel orderModel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderModel = new OrderModel();
        orderModel.setId(1L);
        orderModel.setInvestmentType("Equity");
        orderModel.setTargetAmount(1000d);
        orderModel.setActualAmount(351d);
        orderModel.setCurrency("USD");
        orderModel.setDateOfOrder(System.currentTimeMillis());
        orderModel.setDueDate(System.currentTimeMillis() + 86400000); // 1 day later
        orderModel.setPurpose("Investing in startup");
        orderModel.setDescription("Initial order for investment");
        orderModel.setCollateral("None");
        orderModel.setIsActive(true);
        orderModel.setIsAlive(true);
    }

    @Test
    void testFindAll_whenRecordsExist_shouldReturnRecords() {
        when(orderRepo.findAll()).thenReturn(Collections.singletonList(orderModel));

        List<OrderModel> result = orderService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(orderModel, result.get(0));
    }

    @Test
    void testFindAll_whenNoRecordsExist_shouldReturnEmptyList() {
        when(orderRepo.findAll()).thenReturn(Collections.emptyList());

        List<OrderModel> result = orderService.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindById_whenRecordExists_shouldReturnRecord() {
        when(orderRepo.findById(1L)).thenReturn(Optional.of(orderModel));

        OrderModel result = orderService.findById(1L);

        assertNotNull(result);
        assertEquals(orderModel, result);
    }

    @Test
    void testFindById_whenRecordDoesNotExist_shouldThrowException() {
        when(orderRepo.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> orderService.findById(1L));
        assertEquals("Order with id 1 not found", exception.getMessage());
    }

    @Test
    void testCreate_shouldSaveAndReturnOrderModel() {
        when(orderRepo.save(any(OrderModel.class))).thenReturn(orderModel);

        OrderModel result = orderService.create(orderModel);

        assertNotNull(result);
        assertEquals(orderModel, result);
        verify(orderRepo, times(1)).save(orderModel);
    }

    @Test
    void testCreate_shouldSetDateOfOrder() {
        when(orderRepo.save(any(OrderModel.class))).thenAnswer(invocation -> {
            OrderModel savedOrder = invocation.getArgument(0);
            savedOrder.setDateOfOrder(System.currentTimeMillis());
            return savedOrder;
        });

        OrderModel result = orderService.create(orderModel);

        assertNotNull(result);
        assertNotNull(result.getDateOfOrder());
        assertEquals(orderModel.getDateOfOrder(), result.getDateOfOrder());
    }

    @Test
    void testCreate_whenOrderModelIsNull_shouldThrowIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> orderService.create(null));
        assertEquals("Order model cannot be null", exception.getMessage());
    }
}
