package org.example.entity.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.model.exception.ResourceNotFoundException;
import org.example.entity.model.OrderModel;
import org.example.entity.repository.OrderRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepo orderRepo;

    public List<OrderModel> findAll() {
        return orderRepo.findAll();
    }

    public OrderModel findById(Long id) {
        return orderRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order with id " + id + " not found"));
    }

    public OrderModel create(OrderModel orderModel) {
        if (orderModel == null) {
            throw new IllegalArgumentException("Order model cannot be null");
        }
        orderModel.setDateOfOrder(System.currentTimeMillis()); // Устанавливаем дату заказа
        return orderRepo.save(orderModel);
    }
}
