package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.model.InvestorModel;
import org.example.service.InvestorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/investor")
@AllArgsConstructor
public class InvestorController {

    private final InvestorService investorService;

    @GetMapping("/{id}")
    public ResponseEntity<InvestorModel> getInvestorById(@PathVariable Long id) {
        ResponseEntity<?> response = investorService.findById(id);

        if (response.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity.ok((InvestorModel) response.getBody());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping
    public ResponseEntity<?> createInvestor(@RequestBody InvestorModel investorModel) {
        ResponseEntity<?> response = investorService.create(investorModel);

        return ResponseEntity.status(response.getStatusCode()).build();
    }
}
