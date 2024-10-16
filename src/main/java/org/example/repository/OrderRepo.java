package org.example.repository;

import org.example.model.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<OrderModel, Long> {
    // Добавьте дополнительные методы поиска, если это необходимо
    List<OrderModel> findByCompanyId(Long companyId);
}
