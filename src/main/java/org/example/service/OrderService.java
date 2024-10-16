package org.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepo orderRepo;

    @Autowired
    public OrderService(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }

    // Получить все записи
    public List<OrderModel> findAll() {
        return orderRepo.findAll();
    }

    // Получить запись по ID
    public OrderModel findById(Long id) {
        return orderRepo.findById(id).orElse(null);
    }

    // Создать новую запись
    public OrderModel create(OrderModel orderModel) {
        orderModel.setDateOfOrder(OffsetDateTime.now()); // Устанавливаем дату заказа
        return orderRepo.save(orderModel);
    }

    // Обновить существующую запись
    public OrderModel update(Long id, OrderModel updatedOrderModel) {
        if (orderRepo.existsById(id)) {
            updatedOrderModel.setId(id);
            return orderRepo.save(updatedOrderModel);
        }
        return null; // Или можно выбросить исключение
    }

    // Удалить запись
    public void delete(Long id) {
        orderRepo.deleteById(id);
    }
}

