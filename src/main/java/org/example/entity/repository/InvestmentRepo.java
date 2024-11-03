package org.example.entity.repository;

import org.example.entity.model.InvestmentModel;
import org.example.entity.model.InvestorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvestmentRepo extends JpaRepository<InvestmentModel, Long> {
    List<InvestmentModel> findByInvestor(InvestorModel investor);
    List<InvestmentModel> findByOrderId(Long orderId);
}
