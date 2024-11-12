package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.persistence.model.dto.OrderModelDTO;
import org.example.persistence.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public List<OrderModelDTO> getAllOrders() {
        return orderService.findAll();
    }

    @GetMapping("/{id}")
    public OrderModelDTO getOrderById(@PathVariable Long id) {
        return orderService.findById(id);
    }

    @PostMapping
    public OrderModelDTO createOrder(@RequestBody OrderModelDTO orderModelDTO) {
        return orderService.create(orderModelDTO);
    }
}
