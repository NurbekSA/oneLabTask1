package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.model.OrderModel;
import org.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("order")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public List<OrderModel> getAllOrders() {
        ResponseEntity<?> response = orderService.findAll();
        if(response.getStatusCode() == HttpStatus.OK){
            return (List<OrderModel>) response.getBody();
        }
        return null;
    }

    @GetMapping("/{id}")
    public OrderModel getOrderById(@PathVariable Long id) {
        ResponseEntity<?> response = orderService.findById(id);
        if(response.getStatusCode() == HttpStatus.OK){
            return (OrderModel) response.getBody();
        }
        return null;
    }

    @PostMapping
    public HttpStatusCode createOrder(@RequestBody OrderModel orderModel) {
        return orderService.create(orderModel).getStatusCode();
    }
}
