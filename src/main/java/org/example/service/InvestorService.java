package org.example.service;

import org.example.model.InvestmentModel;
import org.example.model.InvestorModel;
import org.example.repository.InvestorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvestorService {
    Long now = System.currentTimeMillis();
    private final InvestorRepo investorRepo;
    private final InvestmentService investmentService;

    @Autowired
    public InvestorService(InvestorRepo investorRepo, InvestmentService investmentService) {
        this.investorRepo = investorRepo;
        this.investmentService = investmentService;
    }

    public ResponseEntity<InvestorModel> findById(Long id) {
        InvestorModel investorModel = investorRepo.findById(id).orElse(null);
        if (investorModel == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(investorModel);
    }

    // Создать новую запись
    public ResponseEntity<?> create(InvestorModel investorModel) {
        investorModel.setIsChecked(true); //todo: При реальном запуске нужно поменять на false
        investorModel.setCreatedAt(now); // Устанавливаем дату создания
        investorModel.setUpdatedAt(now); // Устанавливаем дату обновления
        InvestorModel savedInvestor = investorRepo.save(investorModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedInvestor);
    }
}
