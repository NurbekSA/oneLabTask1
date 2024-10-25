package org.example.service;

import org.example.model.InvestmentModel;
import org.example.model.InvestorModel;
import org.example.repository.InvestmentRepo;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class InvestmentService {
    private static final Logger logger = LoggerFactory.getLogger(InvestmentService.class);
    private final InvestmentService investmentService;
    private final InvestorService investorService;

    public InvestmentService(InvestmentService investmentService, InvestorService investorService) {
        this.investmentService = investmentService;
        this.investorService = investorService;
    }

    public ResponseEntity<List<InvestmentModel>> findAll() {
        ResponseEntity<List<InvestmentModel>> response = investmentService.findAll();
        if(response.getStatusCode() == HttpStatus.NOT_FOUND)
            return response.getBody();

        List<InvestmentModel> investments = response.getBody();
        return ResponseEntity.ok(investments);
    }

    public ResponseEntity<InvestmentModel> findById(Long id) {
        InvestmentModel investment = investmentService.findById(id).orElse(null);
        if (investment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(investment);
    }

    public ResponseEntity<?> save(InvestmentModel investment) {

        ResponseEntity<?> response = investorService.findById(investment.getId());
        if (response.getStatusCode() != HttpStatus.OK)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Не получилась найти инвестора");

        InvestorModel investor = (InvestorModel) response.getBody();

        if (!investor.getIsChecked())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Инвестор не прошёл проверку.");

        if (investor.getCards() == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Карта не привязана");

        investment.setIsPaid(false);
        investment.setIsActive(false);
        investment.setIsAlive(false);
        investment.setInvestmentDate(System.currentTimeMillis());

        investmentService.save(investment);

        logger.info("Инвестиция успешно сохранена в БД");

        // todo: sending a payment request

        return ResponseEntity.ok(investment);
    }

    public ResponseEntity<?> update(Long id, InvestmentModel updatedInvestmentModel) {
        if (investmentService.existsById(id)) {
            updatedInvestmentModel.setId(id);
            InvestmentModel savedInvestment = investmentService.save(updatedInvestmentModel);
            return ResponseEntity.ok(savedInvestment);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Инвестиция не найдена");
    }

    public ResponseEntity<?> update(Long id, InvestmentModel updatedInvestmentModel) {
        InvestmentModel existingInvestment = investmentService(id);

        existingInvestment.setInvestor(updatedInvestmentModel.getInvestor());
        existingInvestment.setOrder(updatedInvestmentModel.getOrder());
        existingInvestment.setIsPaid(updatedInvestmentModel.getIsPaid());
        existingInvestment.setIsActive(updatedInvestmentModel.getIsActive());
        existingInvestment.setIsAlive(updatedInvestmentModel.getIsAlive());
        existingInvestment.setAmount(updatedInvestmentModel.getAmount());
        existingInvestment.setInvestmentDate(updatedInvestmentModel.getInvestmentDate());

        return null; // Или можно выбросить исключение
    }



    public ResponseEntity<?> delete(Long id) {
        if (investmentService.existsById(id)) {
            investmentService.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Инвестиция не найдена");
    }

    public ResponseEntity<?> saveAll(List<InvestmentModel> investments) throws Exception {
        investments.forEach(x -> System.out.println(x.toString()));
        investmentService.saveAll(investments);
        return ResponseEntity.ok("Все инвестиции успешно сохранены.");
    }
}
