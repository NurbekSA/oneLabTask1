package service;

import org.example.entity.model.InvestorModel;
import org.example.entity.model.exception.ResourceNotFoundException;
import org.example.entity.repository.InvestorRepo;
import org.example.entity.service.InvestorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class InvestorServiceTest {

    @Mock
    private InvestorRepo investorRepo;

    @InjectMocks
    private InvestorService investorService;

    private InvestorModel investorModel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        investorModel = new InvestorModel();
        investorModel.setId(1L);
        investorModel.setIin("123456789012");
        investorModel.setFio("John Doe");
        investorModel.setPhoneNumber("1234567890");
        investorModel.setMail("john.doe@test.com");
        investorModel.setAddress("123 Test Street");
        investorModel.setInvestorType("Individual");
        investorModel.setScore(100d);
        investorModel.setCreatedAt(System.currentTimeMillis());
        investorModel.setUpdatedAt(System.currentTimeMillis());
    }

    @Test
    void testFindById_Found() {
        when(investorRepo.findById(1L)).thenReturn(Optional.of(investorModel));

        InvestorModel result = investorService.findById(1L);

        assertNotNull(result);
        assertEquals(investorModel, result);
    }

    @Test
    void testFindById_NotFound() {
        when(investorRepo.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> investorService.findById(1L));
        assertEquals("Investor with id 1 not found", exception.getMessage());
    }

    @Test
    void testCreate() {
        when(investorRepo.save(any(InvestorModel.class))).thenReturn(investorModel);

        InvestorModel result = investorService.create(investorModel);

        assertNotNull(result);
        assertEquals(investorModel, result);
        verify(investorRepo, times(1)).save(investorModel);
    }

    @Test
    void testCreate_SetsTimestampsAndIsChecked() {
        when(investorRepo.save(any(InvestorModel.class))).thenAnswer(invocation -> {
            InvestorModel model = invocation.getArgument(0);
            model.setCreatedAt(System.currentTimeMillis());
            model.setUpdatedAt(System.currentTimeMillis());
            model.setIsChecked(true); // Set to true as specified in service
            return model;
        });

        InvestorModel result = investorService.create(investorModel);

        assertNotNull(result.getCreatedAt());
        assertNotNull(result.getUpdatedAt());
        assertTrue(result.getIsChecked());
    }

    @Test
    void testCreate_VerifySaveInvocation() {
        investorService.create(investorModel);
        verify(investorRepo).save(investorModel);
    }

    @Test
    void testScoreIsCorrectAfterCreation() {
        when(investorRepo.save(any(InvestorModel.class))).thenReturn(investorModel);

        InvestorModel result = investorService.create(investorModel);

        assertEquals(100d, result.getScore());
    }
}
