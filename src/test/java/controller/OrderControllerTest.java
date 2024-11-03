package controller;

import org.example.Main;
import org.example.entity.model.OrderModel;
import org.example.entity.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Test
    @WithMockUser(username = "Nurbek")
    void testGetAllOrders() throws Exception {
        OrderModel order = new OrderModel();
        order.setId(1L);
        order.setInvestmentType("Equity");
        order.setTargetAmount(1000d);

        when(orderService.findAll()).thenReturn(List.of(order));

        mockMvc.perform(get("/api/order"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].investmentType").value("Equity"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].targetAmount").value(1000d));
    }

    @Test
    @WithMockUser(username = "Nurbek")
    void testGetOrderById_Found() throws Exception {
        OrderModel order = new OrderModel();
        order.setId(1L);
        order.setInvestmentType("Equity");
        order.setTargetAmount(1000d);

        when(orderService.findById(1L)).thenReturn(order);

        mockMvc.perform(get("/api/order/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.investmentType").value("Equity"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.targetAmount").value(1000d));
    }


    @Test
    @WithMockUser(username = "Nurbek")
    void testCreateOrder() throws Exception {
        OrderModel order = new OrderModel();
        order.setId(1L);
        order.setInvestmentType("Equity");
        order.setTargetAmount(1000d);

        when(orderService.create(any(OrderModel.class))).thenReturn(order);

        mockMvc.perform(post("/api/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"investmentType\": \"Equity\", \"targetAmount\": 1000}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.investmentType").value("Equity"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.targetAmount").value(1000d));
    }
}

