package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.model.InvestmentModel;
import org.example.service.InvestmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/investment")
@AllArgsConstructor
public class InvestmentController {

    private final InvestmentService investmentService;

    @GetMapping
    @Transactional(readOnly = true)
    public List<InvestmentModel> getAllInvestments() {
        ResponseEntity<?> response = investmentService.findAll();
        if(response.getStatusCode() == HttpStatus.OK){
            List<InvestmentModel> investmentModels = (List<InvestmentModel>) response.getBody();
            System.out.println("lasnd.,a s.f,a s.d,f as.,dm as.kd.mxc v. zxc");
            investmentModels.forEach(x -> System.out.println(x.toString()));
            return investmentModels;
        }
        return null;
    }

    @GetMapping("/{id}")
    public InvestmentModel getInvestmentById(@PathVariable Long id) {
        ResponseEntity<?> response = investmentService.findById(id);
        if(response.getStatusCode() == HttpStatus.OK){
            return (InvestmentModel) response.getBody();
        }
        return null;
    }

    @PostMapping
    public HttpStatusCode createInvestment(@RequestBody InvestmentModel investmentModel) {
        return investmentService.create(investmentModel).getStatusCode();
    }

    @PutMapping("/{id}")
    public HttpStatusCode updateInvestment(@PathVariable Long id, @RequestBody InvestmentModel investmentModel) {
        return investmentService.update(id, investmentModel).getStatusCode();
    }

    @DeleteMapping("/{id}")
    public HttpStatusCode deleteInvestment(@PathVariable Long id) {
        return investmentService.delete(id).getStatusCode();
    }
}
