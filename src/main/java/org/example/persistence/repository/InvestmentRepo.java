package org.example.persistence.repository;

import org.example.persistence.model.entity.InvestmentModel;
import org.example.persistence.model.entity.InvestorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvestmentRepo extends JpaRepository<InvestmentModel, Long> {
    List<InvestmentModel> findByInvestor(InvestorModel investor);
    List<InvestmentModel> findByOrderId(Long orderId);
}
