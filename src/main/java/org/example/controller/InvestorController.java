package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.model.InvestorModel;
import org.example.service.InvestorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/investor")
@AllArgsConstructor
public class InvestorController {

    private final InvestorService investorService;

    @GetMapping("/{id}")
    public InvestorModel getInvestorById(@PathVariable Long id) {
        ResponseEntity<?> response = investorService.findById(id);
        if(response.getStatusCode() == HttpStatus.OK){
            return (InvestorModel) response.getBody();
        }
        return null;
    }

    @PostMapping
    public HttpStatusCode createInvestor(@RequestBody InvestorModel investorModel) {
        return investorService.create(investorModel).getStatusCode();
    }
}
