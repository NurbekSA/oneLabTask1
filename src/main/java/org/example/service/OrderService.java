package org.example.service;

import org.example.model.OrderModel;
import org.example.repository.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    Long now = System.currentTimeMillis();
    private final OrderRepo orderRepo;

    @Autowired
    public OrderService(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }

    public ResponseEntity<List<OrderModel>> findAll() {
        List<OrderModel> orders = orderRepo.findAll();
        return ResponseEntity.ok(orders);
    }

    public ResponseEntity<OrderModel> findById(Long id) {
        OrderModel orderModel = orderRepo.findById(id).orElse(null);
        if (orderModel == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(orderModel);
    }

    public ResponseEntity<?> create(OrderModel orderModel) {
        orderModel.setDateOfOrder(now); // Устанавливаем дату заказа
        OrderModel savedOrder = orderRepo.save(orderModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
    }
}
