package org.example.entity.service;

import org.example.entity.model.CardModel;
import org.example.entity.model.exception.ResourceNotFoundException;
import org.example.entity.model.InvestmentModel;
import org.example.entity.model.InvestorModel;
import org.example.entity.model.exception.UnpaidException;
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
        logger.info("FIND_ALL: Retrieving all investments from the database.");
        List<InvestmentModel> investments = investmentRepo.findAll();
        if (investments.isEmpty()) {
            logger.warn("FIND_ALL: No investments found in the database.");
            throw new ResourceNotFoundException("No investments found");
        }
        logger.info("FIND_ALL: Successfully retrieved {} investments from the database.", investments.size());
        return investments;
    }

    public InvestmentModel findById(Long id) {
        logger.info("FIND_BY_ID: Searching for investment with ID: {}", id);
        return investmentRepo.findById(id)
                .orElseThrow(() -> {
                    logger.warn("FIND_BY_ID: Investment with ID {} not found.", id);
                    return new ResourceNotFoundException("Investment with id " + id + " not found");
                });
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public InvestmentModel create(InvestmentModel investment, Long cardId) {
        logger.info("CREATE: Creating a new investment for investor ID: {} and card ID: {}", investment.getInvestor().getId(), cardId);
        InvestorModel investor = investorService.findById(investment.getInvestor().getId());

        if (Boolean.FALSE.equals(investor.getIsChecked())) {
            throw new IllegalArgumentException("Investor has not passed verification.");
        }

        CardModel card = investor.getCards().stream()
                .filter(x -> x.getId().equals(cardId))
                .findFirst()
                .orElseThrow(() ->
                        new ResourceNotFoundException("The card could not be found")
                );

        logger.info("CREATE: Setting default investment properties.");
        investment.setIsPaid(false);
        investment.setIsActive(false);
        investment.setIsAlive(false);
        investment.setInvestmentDate(System.currentTimeMillis());

        logger.info("CREATE: Sending message to Kafka topic 'onelab.payment-api.payment-by-card'");

        try {
            CompletableFuture<KafkaMessage> response = kafkaRequestReply.sendRequest(card.getCardNumber(),"onelab.payment-api.payment-by-card", "onelab.entity-api.response");
            KafkaMessage result = response.get();
            String resultBody = result.getBody();

            logger.info("CREATE: Received message: {}", resultBody);


            if(result.getRequestResult() == KafkaMessage.RequestResult.FAILED || resultBody == null){
                throw new UnpaidException("payment failed: " + result.getBody());
            }
            else {
                logger.info("CREATE: Setting investment ID {} as paid.", investment.getId());

                investment.setIsPaid(true);
                investmentRepo.save(investment);

                return investmentRepo.save(investment);
            }

        } catch (InterruptedException e) {
            logger.warn("CREATE: Thread was interrupted, restoring the interrupted status.", e);
            Thread.currentThread().interrupt();
            throw new UnpaidException("Operation interrupted. Please try again later.");
        } catch (ExecutionException e) {
            logger.warn("CREATE: Execution exception occurred.", e);
            throw new UnpaidException("Operation interrupted. Please try again later.");
        }

    }

    public InvestmentModel logicalDelete(Long id) {
        logger.info("LOGICAL_DELETE: Logically deleting investment ID {}.", id);
        InvestmentModel investment = investmentRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Investment not found")
                );
        investment.setIsAlive(false);
        InvestmentModel updatedInvestment = investmentRepo.save(investment);
        logger.info("LOGICAL_DELETE: Investment ID {} logically deleted successfully.", id);
        return updatedInvestment;
    }

    public InvestmentModel setActive(Long id) {
        logger.info("SET_ACTIVE: Setting investment ID {} as active.", id);
        InvestmentModel investment = investmentRepo.findById(id)
                .orElseThrow(() -> {
                    logger.warn("SET_ACTIVE: Investment with ID {} not found.", id);
                    return new ResourceNotFoundException("Investment not found");
                });
        investment.setIsActive(true);
        InvestmentModel updatedInvestment = investmentRepo.save(investment);
        logger.info("SET_ACTIVE: Investment ID {} set as active successfully.", id);
        return updatedInvestment;
    }

    public void delete(Long id) {
        logger.info("DELETE: Deleting investment with ID: {}", id);
        if (!investmentRepo.existsById(id)) {
            throw new ResourceNotFoundException("Investment with id " + id + " not found");
        }
        investmentRepo.deleteById(id);
        logger.info("DELETE: Investment with ID {} deleted successfully.", id);
    }
}
