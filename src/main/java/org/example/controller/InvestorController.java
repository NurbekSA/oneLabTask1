package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.entity.model.InvestorModel;
import org.example.entity.service.InvestorService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/investor")
@RequiredArgsConstructor
public class InvestorController {

    private final InvestorService investorService;

    @GetMapping("/{id}")
    public InvestorModel getInvestorById(@PathVariable Long id) {
        return investorService.findById(id);
    }

    @PostMapping
    public InvestorModel createInvestor(@RequestBody InvestorModel investorModel) {
        return investorService.create(investorModel);
    }
}
