package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.entity.model.OrderModel;
import org.example.entity.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public List<OrderModel> getAllOrders() {
        return orderService.findAll();
    }

    @GetMapping("/{id}")
    public OrderModel getOrderById(@PathVariable Long id) {
        return orderService.findById(id);
    }

    @PostMapping
    public OrderModel createOrder(@RequestBody OrderModel orderModel) {
        return orderService.create(orderModel);
    }
}
