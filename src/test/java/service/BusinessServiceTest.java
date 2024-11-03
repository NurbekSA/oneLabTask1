package service;

import org.example.entity.model.BusinessModel;
import org.example.entity.model.exception.ResourceNotFoundException;
import org.example.entity.repository.BusinessRepo;
import org.example.entity.service.BusinessService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BusinessServiceTest {

    @Mock
    private BusinessRepo businessRepo;

    @InjectMocks
    private BusinessService businessService;

    private BusinessModel sampleBusiness;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleBusiness = new BusinessModel(null, null, "Example Corp", "123456789", "123 Business St", "Credit Card",
                "Technology", "John Doe", "123456789012", "+77011234567", "contact@example.com", "sector",
                System.currentTimeMillis(), System.currentTimeMillis(), System.currentTimeMillis());
    }

    @Test
    void testFindAll_whenRecordsExist_shouldReturnRecords() {
        List<BusinessModel> businesses = new ArrayList<>();
        businesses.add(sampleBusiness);
        when(businessRepo.findAll()).thenReturn(businesses);

        List<BusinessModel> result = businessService.findAll();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(businessRepo, times(1)).findAll();
    }

    @Test
    void testFindAll_whenNoRecordsExist_shouldThrowException() {
        when(businessRepo.findAll()).thenReturn(new ArrayList<>());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> businessService.findAll());
        assertEquals("No business records found", exception.getMessage());
        verify(businessRepo, times(1)).findAll();
    }

    @Test
    void testFindById_whenRecordExists_shouldReturnRecord() {
        Long id = 1L;
        when(businessRepo.findById(id)).thenReturn(Optional.of(sampleBusiness));

        BusinessModel result = businessService.findById(id);

        assertNotNull(result);
        assertEquals("Example Corp", result.getDirectorNumber());
        verify(businessRepo, times(1)).findById(id);
    }

    @Test
    void testFindById_whenRecordDoesNotExist_shouldThrowException() {
        Long id = 1L;
        when(businessRepo.findById(id)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> businessService.findById(id));
        assertEquals("Business with id " + id + " not found", exception.getMessage());
        verify(businessRepo, times(1)).findById(id);
    }

    @Test
    void testCreate_shouldSaveAndReturnBusinessModel() {
        when(businessRepo.save(sampleBusiness)).thenReturn(sampleBusiness);

        BusinessModel result = businessService.create(sampleBusiness);

        assertNotNull(result);
        verify(businessRepo, times(1)).save(sampleBusiness);
    }

    @Test
    void testCreate_whenBusinessModelIsNull_shouldThrowIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> businessService.create(null));
        assertEquals("Business model cannot be null", exception.getMessage());
    }
}
