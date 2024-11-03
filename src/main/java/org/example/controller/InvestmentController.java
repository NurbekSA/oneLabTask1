package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.entity.model.InvestmentModel;
import org.example.entity.service.InvestmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/investments")
@RequiredArgsConstructor
public class InvestmentController {

    private final InvestmentService investmentService;

    @GetMapping
    public ResponseEntity<List<InvestmentModel>> getAllInvestments() {
        List<InvestmentModel> investments = investmentService.findAll();
        return ResponseEntity.ok(investments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvestmentModel> getInvestmentById(@PathVariable Long id) {
        InvestmentModel investment = investmentService.findById(id);
        return ResponseEntity.ok(investment);
    }

    @PostMapping
    public ResponseEntity<InvestmentModel> createInvestment(@RequestBody InvestmentModel investment, @RequestParam Long cardId) {
        InvestmentModel createdInvestment = investmentService.create(investment, cardId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdInvestment);
    }
    @PutMapping("/{id}/activate")
    public ResponseEntity<InvestmentModel> activateInvestment(@PathVariable Long id) {
        InvestmentModel updatedInvestment = investmentService.setActive(id);
        return ResponseEntity.ok(updatedInvestment);
    }

    @PutMapping("/{id}/logical-delete")
    public ResponseEntity<InvestmentModel> logicalDeleteInvestment(@PathVariable Long id) {
        InvestmentModel updatedInvestment = investmentService.logicalDelete(id);
        return ResponseEntity.ok(updatedInvestment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvestment(@PathVariable Long id) {
        investmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
