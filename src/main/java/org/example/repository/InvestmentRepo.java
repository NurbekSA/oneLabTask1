package org.example.repository;

import org.example.model.InvestmentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvestmentRepo extends JpaRepository<InvestmentModel, Long> {
    // Добавьте дополнительные методы поиска, если это необходимо
    List<InvestmentModel> findByInvestorId(Long investorId);
    List<InvestmentModel> findByOrderId(Long orderId);
}
