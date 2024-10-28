package org.example.service;

import org.example.model.CardModel;
import org.example.model.InvestmentModel;
import org.example.model.InvestorModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    private final InvestmentService investmentService;

    public PaymentService(InvestmentService investmentService) {
        this.investmentService = investmentService;
    }

    public ResponseEntity<?> pay(InvestmentModel investment, Long cardId){
        InvestorModel investor = investment.getInvestor();
        if(investor == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Не удалось найти инвестора");
        CardModel card = (CardModel) investor.getCards().stream().filter(x -> x.getId() == cardId);
        if (card == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Нет привязанной карты");

        ResponseEntity<?> payResponse = requestTuJetPay(card);
        if(payResponse.getStatusCode() != HttpStatus.OK) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Оплата прошла не успешно");

        investment.setIsPaid(true);
        ResponseEntity<?> updateResponse = investmentService.update(investment.getId(), investment);

        if(updateResponse.getStatusCode() == HttpStatus.OK){
            return ResponseEntity.ok(payResponse.getBody());
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Не получилось обнавить Investment");
        }
    }
    public ResponseEntity requestTuJetPay(CardModel card){
        //имитация запроса на джет пей
        try{
            Thread.sleep(1000);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return ResponseEntity.ok("Здесь будет чек об оплате из jetPay");
    }
}
