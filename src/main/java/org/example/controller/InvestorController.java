package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.persistence.model.dto.InvestorModelDTO;
import org.example.persistence.service.InvestorService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/investor")
@RequiredArgsConstructor
public class InvestorController {

    private final InvestorService investorService;

    @GetMapping("/{id}")
    public InvestorModelDTO getInvestorById(@PathVariable Long id) {
        return investorService.findById(id);
    }

    @PostMapping
    public InvestorModelDTO createInvestor(@RequestBody InvestorModelDTO investorModelDTO) {
        return investorService.create(investorModelDTO);
    }
}
