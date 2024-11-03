package org.example.entity.service;

import org.example.entity.model.CardModel;
import org.example.entity.model.exception.ResourceNotFoundException;
import org.example.entity.model.InvestmentModel;
import org.example.entity.model.InvestorModel;
import org.example.entity.repository.InvestmentRepo;
import org.example.kafka.KafkaRequestReply;
import org.example.kafka.proto.tutorial.KafkaMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@EnableTransactionManagement
public class InvestmentService {

    // Веб срвер отправляет запрос на инвестицию
    // Перед добавлением проверяется Прощал ли инвестор проверку.
    // Дело в том что после регистрации данные инвестора проходят проверку и в это время он может заходит в платформу
    // Для безопасности значения setIsPaid setIsActive setIsAlive устонавляается на черверной части
    // Отправляется сообщение пла платежный шлюз
    // Метод обернут в транзакцию для отката при сбое

    private static final Logger logger = LoggerFactory.getLogger(InvestmentService.class);
    private final InvestmentRepo investmentRepo;
    private final InvestorService investorService;
    private final KafkaRequestReply kafkaRequestReply;


    public InvestmentService(InvestmentRepo investmentRepo, @Lazy InvestorService investorService, KafkaRequestReply kafkaRequestReply) {
        this.investmentRepo = investmentRepo;
        this.investorService = investorService;
        this.kafkaRequestReply = kafkaRequestReply;
    }

    public List<InvestmentModel> findAll() {
        logger.info("Retrieving all investments from the database.");
        List<InvestmentModel> investments = investmentRepo.findAll();
        if (investments.isEmpty()) {
            logger.warn("No investments found in the database.");
            throw new ResourceNotFoundException("No investments found");
        }
        logger.info("Successfully retrieved {} investments from the database.", investments.size());
        return investments;
    }

    public InvestmentModel findById(Long id) {
        logger.info("Searching for investment with ID: {}", id);
        return investmentRepo.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Investment with ID {} not found.", id);
                    return new ResourceNotFoundException("Investment with id " + id + " not found");
                });
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public InvestmentModel create(InvestmentModel investment, Long cardId) {
        logger.info("Creating a new investment for investor ID: {} and card ID: {}", investment.getInvestor().getId(), cardId);
        InvestorModel investor = investorService.findById(investment.getInvestor().getId());

        if (Boolean.FALSE.equals(investor.getIsChecked())) {
            throw new IllegalArgumentException("Investor has not passed verification.");
        }

        CardModel card = investor.getCards().stream()
                .filter(x -> x.getId().equals(cardId))
                .findFirst()
                .orElseThrow(() ->
                    new ResourceNotFoundException("Failed to find Card")
                );

        logger.info("Setting default investment properties.");
        investment.setIsPaid(false);
        investment.setIsActive(false);
        investment.setIsAlive(false);
        investment.setInvestmentDate(System.currentTimeMillis());



        logger.info("Investment.Creat. Sending message to Kafka topic 'payment-topic'");

        try {
            CompletableFuture<KafkaMessage> response = kafkaRequestReply.sendRequest(card.getCardNumber(),"payment-topic", "investment-topic");
            KafkaMessage result = response.get(); // Блокирует поток до получения результата
            logger.info("Получено сообщение: {}", result.getBody());
        }catch (InterruptedException e) {
            logger.warn("Thread was interrupted, restoring the interrupted status.", e);
            Thread.currentThread().interrupt(); // Restore the interrupted status
        } catch (ExecutionException e) {
            logger.warn("Execution exception occurred.", e);
            e.printStackTrace();
        }


        logger.info("Setting investment ID {} as paid.", investment.getId());

        investment.setIsPaid(true);
        investmentRepo.save(investment);

        return investmentRepo.save(investment);
    }


    public InvestmentModel logicalDelete(Long id) {
        logger.info("Logically deleting investment ID {}.", id);
        InvestmentModel investment = investmentRepo.findById(id)
                .orElseThrow(() ->
                     new ResourceNotFoundException("Investment not found")
                );
        investment.setIsAlive(false);
        InvestmentModel updatedInvestment = investmentRepo.save(investment);
        logger.info("Investment ID {} logically deleted successfully.", id);
        return updatedInvestment;
    }

    public InvestmentModel setActive(Long id) {
        logger.info("Setting investment ID {} as active.", id);
        InvestmentModel investment = investmentRepo.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Investment with ID {} not found.", id);
                    return new ResourceNotFoundException("Investment not found");
                });
        investment.setIsActive(true);
        InvestmentModel updatedInvestment = investmentRepo.save(investment);
        logger.info("Investment ID {} set as active successfully.", id);
        return updatedInvestment;
    }

    public void delete(Long id) {
        logger.info("Deleting investment with ID: {}", id);
        if (!investmentRepo.existsById(id)) {
            throw new ResourceNotFoundException("Investment with id " + id + " not found");
        }
        investmentRepo.deleteById(id);
        logger.info("Investment with ID {} deleted successfully.", id);
    }
}
