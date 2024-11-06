package service;

import org.example.entity.model.CardModel;
import org.example.entity.model.InvestmentModel;
import org.example.entity.model.InvestorModel;
import org.example.entity.model.exception.ResourceNotFoundException;
import org.example.entity.repository.InvestmentRepo;
import org.example.entity.service.InvestmentService;
import org.example.entity.service.InvestorService;
import org.example.kafka.KafkaRequestReply;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InvestmentServiceTest {

    @Mock
    private InvestmentRepo investmentRepo;

    @Mock
    private InvestorService investorService;

    @Mock
    private KafkaRequestReply kafkaRequestReply;

    @Mock
    private Logger logger;

    @InjectMocks
    private InvestmentService investmentService;

    private InvestmentModel investmentModel;
    private InvestorModel investorModel;
    private CardModel cardModel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        investorModel = new InvestorModel();
        investorModel.setId(1L);
        investorModel.setIsChecked(true);

        cardModel = new CardModel();
        cardModel.setId(1L);
        cardModel.setCardNumber("1234-5678-9876-5432");
        investorModel.setCards(List.of(cardModel));

        investmentModel = new InvestmentModel();
        investmentModel.setId(1L);
        investmentModel.setInvestor(investorModel);
        investmentModel.setAmount(1000d);
        investmentModel.setIsPaid(false);
        investmentModel.setIsActive(false);
        investmentModel.setIsAlive(false);
    }

    @Test
    void testFindAll_whenInvestmentsExist_shouldReturnInvestments() {
        when(investmentRepo.findAll()).thenReturn(List.of(investmentModel));

        List<InvestmentModel> result = investmentService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(investmentModel, result.get(0));
        verify(investmentRepo, times(1)).findAll();
    }

    @Test
    void testFindAll_whenNoInvestmentsExist_shouldThrowException() {
        when(investmentRepo.findAll()).thenReturn(Collections.emptyList());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> investmentService.findAll());
        assertEquals("No investments found", exception.getMessage());
        verify(investmentRepo, times(1)).findAll();
    }

    @Test
    void testFindById_whenInvestmentExists_shouldReturnInvestment() {
        when(investmentRepo.findById(1L)).thenReturn(Optional.of(investmentModel));

        InvestmentModel result = investmentService.findById(1L);

        assertNotNull(result);
        assertEquals(investmentModel, result);
        verify(investmentRepo, times(1)).findById(1L);
    }

    @Test
    void testFindById_whenInvestmentDoesNotExist_shouldThrowException() {
        when(investmentRepo.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> investmentService.findById(1L));
        assertEquals("Investment with id 1 not found", exception.getMessage());
        verify(investmentRepo, times(1)).findById(1L);
    }


    @Test
    void testCreate_whenInvestorNotVerified_shouldThrowException() {
        investorModel.setIsChecked(false);
        when(investorService.findById(1L)).thenReturn(investorModel);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> investmentService.create(investmentModel, 1L));
        assertEquals("Investor has not passed verification.", exception.getMessage());
        verify(investorService, times(1)).findById(1L);
        verify(investmentRepo, never()).save(investmentModel);
    }


    @Test
    void testLogicalDelete_whenInvestmentExists_shouldLogicallyDeleteInvestment() {
        when(investmentRepo.findById(1L)).thenReturn(Optional.of(investmentModel));
        when(investmentRepo.save(investmentModel)).thenReturn(investmentModel);

        InvestmentModel result = investmentService.logicalDelete(1L);

        assertNotNull(result);
        assertFalse(result.getIsAlive());
        verify(investmentRepo, times(1)).save(investmentModel);
    }

    @Test
    void testLogicalDelete_whenInvestmentDoesNotExist_shouldThrowException() {
        when(investmentRepo.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> investmentService.logicalDelete(1L));
        assertEquals("Investment not found", exception.getMessage());
        verify(investmentRepo, times(1)).findById(1L);
    }

    @Test
    void testSetActive_whenInvestmentExists_shouldSetInvestmentActive() {
        when(investmentRepo.findById(1L)).thenReturn(Optional.of(investmentModel));
        when(investmentRepo.save(investmentModel)).thenReturn(investmentModel);

        InvestmentModel result = investmentService.setActive(1L);

        assertNotNull(result);
        assertTrue(result.getIsActive());
        verify(investmentRepo, times(1)).save(investmentModel);
    }

    @Test
    void testSetActive_whenInvestmentDoesNotExist_shouldThrowException() {
        when(investmentRepo.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> investmentService.setActive(1L));
        assertEquals("Investment not found", exception.getMessage());
        verify(investmentRepo, times(1)).findById(1L);
    }

    @Test
    void testDelete_whenInvestmentExists_shouldDeleteInvestment() {
        when(investmentRepo.existsById(1L)).thenReturn(true);

        investmentService.delete(1L);

        verify(investmentRepo, times(1)).deleteById(1L);
    }

    @Test
    void testDelete_whenInvestmentDoesNotExist_shouldThrowException() {
        when(investmentRepo.existsById(1L)).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> investmentService.delete(1L));
        assertEquals("Investment with id 1 not found", exception.getMessage());
        verify(investmentRepo, times(1)).existsById(1L);
    }
}
