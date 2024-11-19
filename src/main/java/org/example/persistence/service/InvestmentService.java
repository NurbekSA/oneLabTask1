package org.example.persistence.service;

import org.example.persistence.model.dto.InvestmentModelDTO;
import org.example.persistence.model.entity.CardModel;
import org.example.persistence.model.entity.InvestmentModel;
import org.example.persistence.model.entity.InvestorModel;
import org.example.persistence.model.exception.ResourceNotFoundException;
import org.example.persistence.model.exception.UnpaidException;
import org.example.persistence.repository.InvestmentRepo;
import org.example.kafka.KafkaRequestReply;
import org.example.kafka.proto.tutorial.KafkaMessage;
import org.example.persistence.repository.InvestorRepo;
import org.example.persistence.repository.OrderRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Profile("withKafka")
@Service
@EnableTransactionManagement
public class InvestmentService {

    private static final Logger logger = LoggerFactory.getLogger(InvestmentService.class);
    private final InvestmentRepo investmentRepo;
    private final InvestorRepo investorRepo;
    private final OrderRepo orderRepo;
    private final KafkaRequestReply kafkaRequestReply;

    public InvestmentService(InvestmentRepo investmentRepo, InvestorRepo investorRepo, OrderRepo orderRepo, KafkaRequestReply kafkaRequestReply) {
        this.investmentRepo = investmentRepo;
        this.investorRepo = investorRepo;
        this.orderRepo = orderRepo;
        this.kafkaRequestReply = kafkaRequestReply;
    }

    // Преобразование из InvestmentModel в InvestmentModelDTO
    private InvestmentModelDTO convertToDto(InvestmentModel investment) {
        return new InvestmentModelDTO(
                investment.getId(),
                investment.getInvestor() != null ? investment.getInvestor().getId() : null,
                investment.getOrder() != null ? investment.getOrder().getId() : null,
                investment.getIsPaid(),
                investment.getIsActive(),
                investment.getIsAlive(),
                investment.getAmount(),
                investment.getInvestmentDate()
        );
    }

    // Преобразование из InvestmentModelDTO в InvestmentModel
    private InvestmentModel convertToEntity(InvestmentModelDTO investmentDTO) {
        InvestmentModel investment = new InvestmentModel();
        investment.setId(investmentDTO.getId());
        investment.setIsPaid(investmentDTO.getIsPaid());
        investment.setIsActive(investmentDTO.getIsActive());
        investment.setIsAlive(investmentDTO.getIsAlive());
        investment.setAmount(investmentDTO.getAmount());
        investment.setInvestmentDate(investmentDTO.getInvestmentDate());
        return investment;
    }

    public List<InvestmentModelDTO> findAll() {
        logger.info("FIND_ALL: Retrieving all investments from the database.");
        List<InvestmentModel> investments = investmentRepo.findAll();
        if (investments.isEmpty()) {
            throw new ResourceNotFoundException("No investments found");
        }
        return investments.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public InvestmentModelDTO findById(Long id) {
        logger.info("FIND_BY_ID: Searching for investment with ID: {}", id);
        InvestmentModel investment = investmentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Investment with id " + id + " not found"));
        return convertToDto(investment);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public InvestmentModelDTO create(InvestmentModelDTO investmentDTO, Long cardId) {
        logger.info("CREATE: Creating a new investment for investor ID: {} and card ID: {}", investmentDTO.getInvestorId(), cardId);

        InvestorModel investor = investorRepo.findById(investmentDTO.getInvestorId()).orElseThrow(() -> new ResourceNotFoundException("CREATE. couldn't find an investor by ID: " + investmentDTO.getInvestorId()));
        if (Boolean.FALSE.equals(investor.getIsChecked())) {
            throw new IllegalArgumentException("Investor has not passed verification.");
        }

        CardModel card = investor.getCards().stream()
                .filter(x -> x.getId().equals(cardId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("The card could not be found"));

        InvestmentModel investment = convertToEntity(investmentDTO);
        investment.setInvestor(investor);
        investment.setIsPaid(false);
        investment.setIsActive(false);
        investment.setIsAlive(false);
        investment.setOrder(orderRepo.findById(investmentDTO.getOrderId()).orElseThrow(() -> new ResourceNotFoundException("CREATE, couldn't find an order by ID: " + investmentDTO.getOrderId())));
        investment.setInvestmentDate(System.currentTimeMillis());


        logger.info("CREATE: Sending message to Kafka topic 'onelab.payment-api.payment-by-card'");

        try {
            CompletableFuture<KafkaMessage> response = kafkaRequestReply.sendRequest(card.getCardNumber(),"onelab.payment-api.payment-by-card", "onelab.entity-api.response");
            KafkaMessage result = response.get();
            String resultBody = result.getBody();

            logger.info("CREATE: Received message: {}", resultBody);

            if (result.getRequestResult() == KafkaMessage.RequestResult.FAILED || resultBody == null) {
                throw new UnpaidException("Payment failed: " + result.getBody());
            } else {
                logger.info("CREATE: Setting investment as paid.");
                investment.setIsPaid(true);
                InvestmentModel savedInvestment = investmentRepo.save(investment);
                return convertToDto(savedInvestment);
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new UnpaidException("Operation interrupted. Please try again later.");
        } catch (ExecutionException e) {
            throw new UnpaidException("Operation interrupted. Please try again later.");
        }
    }

    public InvestmentModelDTO logicalDelete(Long id) {
        logger.info("LOGICAL_DELETE: Logically deleting investment ID {}.", id);
        InvestmentModel investment = investmentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Investment not found"));
        investment.setIsAlive(false);
        InvestmentModel updatedInvestment = investmentRepo.save(investment);
        return convertToDto(updatedInvestment);
    }

    public InvestmentModelDTO setActive(Long id) {
        logger.info("SET_ACTIVE: Setting investment ID {} as active.", id);
        InvestmentModel investment = investmentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Investment not found"));
        investment.setIsActive(true);
        InvestmentModel updatedInvestment = investmentRepo.save(investment);
        return convertToDto(updatedInvestment);
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
