package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.model.OrderModel;
import org.example.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("order")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderModel>> getAllOrders() {

        ResponseEntity<List<OrderModel>> response = orderService.findAll();

        if (response.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity.ok(response.getBody());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderModel> getOrderById(@PathVariable Long id) {
        ResponseEntity<OrderModel> response = orderService.findById(id);
        if (response.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity.ok(response.getBody());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping
    public ResponseEntity<OrderModel> createOrder(@RequestBody OrderModel orderModel) {
        ResponseEntity<OrderModel> savedOrder = orderService.create(orderModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder.getBody());
    }
}
