package org.example.service;

import org.example.model.InvestmentModel;
import org.example.model.InvestorModel;
import org.example.repository.InvestmentRepo;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InvestmentService {
    private static final Logger logger = LoggerFactory.getLogger(InvestmentService.class);
    private final InvestmentRepo investmentRepo;

    private final InvestorService investorService;

    public InvestmentService(InvestmentRepo investmentRepo, @Lazy InvestorService investorService) {
        this.investmentRepo = investmentRepo;
        this.investorService = investorService;
    }

    public ResponseEntity<?> findAll() {
        List<InvestmentModel> investments = investmentRepo.findAll();
        if (investments == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        return ResponseEntity.ok(investments);
    }

    public ResponseEntity<?> findById(Long id) {
        InvestmentModel investments = investmentRepo.findById(id).orElse(null);
        if (investments == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        return ResponseEntity.ok(investments);
    }
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ResponseEntity<?> create(InvestmentModel investment) {

        ResponseEntity<?> response = investorService.findById(investment.getId());
        if (response.getStatusCode() != HttpStatus.OK)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.getBody());

        InvestorModel investor = (InvestorModel)response.getBody();

        if (!investor.getIsChecked())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Инвестор не прошёл проверку.");

        if (investor.getCards() == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Карта не привязана");

        investment.setIsPaid(false);
        investment.setIsActive(false);
        investment.setIsAlive(false);
        investment.setInvestmentDate(System.currentTimeMillis());

        investmentRepo.save(investment);

        logger.info("Инвестиция успешно сохранена в БД");

        // todo: sending a payment request

        return ResponseEntity.ok(investment);
    }

    public ResponseEntity<?> update(Long id, InvestmentModel updatedInvestment) {
        InvestmentModel existInvestment = investmentRepo.findById(id).orElse(null);
        if (existInvestment == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Не удалось найти инвестицию  с айди" + id);

        existInvestment.setIsAlive(updatedInvestment.getIsAlive());
        existInvestment.setInvestmentDate(updatedInvestment.getInvestmentDate());
        existInvestment.setIsActive(updatedInvestment.getIsActive());
        existInvestment.setAmount(updatedInvestment.getAmount());
        existInvestment.setInvestor(updatedInvestment.getInvestor());
        existInvestment.setOrder(updatedInvestment.getOrder());
        existInvestment.setIsPaid(updatedInvestment.getIsPaid());

        return ResponseEntity.ok(investmentRepo.save(updatedInvestment));
    }


    public ResponseEntity<?> delete(Long id) {
        if (investmentRepo.existsById(id)) {
            investmentRepo.deleteById(id);
            return ResponseEntity.ok("Успешно удалено");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Инвестиция не найдена");
    }
}
