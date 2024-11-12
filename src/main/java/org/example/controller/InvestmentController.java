package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.persistence.model.dto.InvestmentModelDTO;
import org.example.persistence.service.InvestmentService;
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
    public ResponseEntity<List<InvestmentModelDTO>> getAllInvestments() {
        return ResponseEntity.ok(investmentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvestmentModelDTO> getInvestmentById(@PathVariable Long id) {
        return ResponseEntity.ok(investmentService.findById(id));
    }

    @PostMapping
    public ResponseEntity<InvestmentModelDTO> createInvestment(@RequestBody InvestmentModelDTO investment, @RequestParam Long cardId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(investmentService.create(investment, cardId));
    }
    @PutMapping("/{id}/activate")
    public ResponseEntity<InvestmentModelDTO> activateInvestment(@PathVariable Long id) {
        return ResponseEntity.ok(investmentService.setActive(id));
    }

    @PutMapping("/{id}/logical-delete")
    public ResponseEntity<InvestmentModelDTO> logicalDeleteInvestment(@PathVariable Long id) {
        return ResponseEntity.ok(investmentService.logicalDelete(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvestment(@PathVariable Long id) {
        investmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
