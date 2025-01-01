
import org.apache.coyote.Response;
import org.example.model.InvestmentModel;
import org.example.repository.InvestmentRepo;
import org.example.repository.InvestorRepo;
import org.example.service.InvestmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class InvestmentServiceTest {

    @Mock
    private InvestmentRepo investmentRepo;

    @Mock
    private InvestorRepo investorRepo;

    @InjectMocks
    private InvestmentService investmentService;

    private InvestmentModel investment;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        investment = new InvestmentModel();
        investment.setId(1L);
        investment.setInvestmentDate(System.currentTimeMillis());
    }

    @Test
    void testFindAll() {
        when(investmentRepo.findAll()).thenReturn(Collections.singletonList(investment));

        ResponseEntity<?> response = investmentService.findAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(investmentRepo, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(investmentRepo.findById(1L)).thenReturn(Optional.of(investment));

        ResponseEntity<?> response = investmentService.findById(1L);

        assertNotNull(response.getBody());
        assertEquals(investment, (InvestmentModel) response.getBody());
        verify(investmentRepo, times(1)).findById(1L);
    }

    @Test
    void testCreate() {
        when(investmentRepo.save(any(InvestmentModel.class))).thenReturn(investment);

        ResponseEntity<?> response = investmentService.create(investment);

        assertNotNull(response.getBody());
        assertEquals(investment, (InvestmentModel) response.getBody());
        verify(investmentRepo, times(1)).save(investment);
    }

    @Test
    void testUpdate() {
        when(investmentRepo.existsById(1L)).thenReturn(true);
        when(investmentRepo.save(any(InvestmentModel.class))).thenReturn(investment);

        InvestmentModel updatedInvestment = new InvestmentModel();
        updatedInvestment.setId(1L);
        updatedInvestment.setInvestmentDate(System.currentTimeMillis());

        ResponseEntity<?> response = investmentService.update(1L, updatedInvestment);

        assertNotNull(response.getBody());
        assertEquals(investment, (InvestmentModel) response.getBody());
        verify(investmentRepo, times(1)).save(updatedInvestment);
    }

    @Test
    void testUpdate_NotFound() {
        when(investmentRepo.existsById(1L)).thenReturn(false);

        InvestmentModel updatedInvestment = new InvestmentModel();
        updatedInvestment.setId(1L);

        ResponseEntity<?> response = investmentService.update(1L, updatedInvestment);

        assertNull(response.getBody());
        verify(investmentRepo, never()).save(any(InvestmentModel.class));
    }

    @Test
    void testDelete() {
        investmentService.delete(1L);

        verify(investmentRepo, times(1)).deleteById(1L);
    }


}
