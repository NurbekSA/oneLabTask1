import org.example.Main;
import org.example.model.BusinessModel;
import org.example.repository.BusinessRepo;
import org.example.service.BusinessService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = Main.class)
class BusinessServiceTest {

    @Mock
    private BusinessRepo businessRepo;
    @InjectMocks
    private BusinessService businessService;

    private BusinessModel businessModel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        businessModel = new BusinessModel();
        businessModel.setId(1L);
        businessModel.setCompanyName("Test Company");
        businessModel.setCompanyBIN("123456789");
        businessModel.setAddress("123 Test Street");
        businessModel.setTypeOfPaymentSystem("Visa");
        businessModel.setSector("IT");
        businessModel.setDateOfBusinessStarted(1622505600000L);
        businessModel.setDirectorFIO("John Doe");
        businessModel.setDirectorIIN("987654321");
        businessModel.setDirectorNumber("1234567890");
        businessModel.setDirectorMail("john.doe@test.com");
        businessModel.setCreatedAt(System.currentTimeMillis());
        businessModel.setUpdatedAt(System.currentTimeMillis());
    }

    @Test
    void testFindAll() {
        when(businessRepo.findAll()).thenReturn(Collections.singletonList(businessModel));

        ResponseEntity<List<BusinessModel>> response = businessService.findAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }



    @Test
    void testFindById_NotFound() {
        when(businessRepo.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<BusinessModel> response = businessService.findById(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testCreate() {
        when(businessRepo.save(any(BusinessModel.class))).thenReturn(businessModel);

        ResponseEntity<?> response = businessService.create(businessModel);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
