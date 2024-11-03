package org.example.entity.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.model.exception.ResourceNotFoundException;
import org.example.entity.model.InvestorModel;
import org.example.entity.repository.InvestorRepo;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InvestorService {
    Long now = System.currentTimeMillis();
    private final InvestorRepo investorRepo;
    private final InvestmentService investmentService;



    public InvestorModel findById(Long id) {
        return investorRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Investor with id " + id + " not found"));
    }

    public InvestorModel create(InvestorModel investorModel) {
        investorModel.setIsChecked(true); // При реальном запуске нужно поменять на false
        investorModel.setCreatedAt(now);
        investorModel.setUpdatedAt(now);
        return investorRepo.save(investorModel);
    }
}
