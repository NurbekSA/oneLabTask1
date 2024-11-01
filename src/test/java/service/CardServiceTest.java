package service;

import org.example.model.CardModel;
import org.example.model.InvestorModel;
import org.example.repository.CardRepo;
import org.example.service.CardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.OffsetDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CardServiceTest {

    @Mock
    private CardRepo cardRepo;

    @InjectMocks
    private CardService cardService;

    private CardModel cardModel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cardModel = new CardModel();
        cardModel.setId(1L);
        cardModel.setInvestor(new InvestorModel()); // инициализируйте InvestorModel, если необходимо
        cardModel.setCardNumber("1234-5678-9876-5432");
        cardModel.setCardholderName("John Doe");
        cardModel.setExpiryDate("12/25");
        cardModel.setCreatedAt(System.currentTimeMillis());
        cardModel.setUpdatedAt(System.currentTimeMillis());
    }

    @Test
    void testFindById_Found() {
        when(cardRepo.findById(1L)).thenReturn(Optional.of(cardModel));

        ResponseEntity<CardModel> response = cardService.findById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testFindById_NotFound() {
        when(cardRepo.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<CardModel> response = cardService.findById(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testCreate() {
        when(cardRepo.save(any(CardModel.class))).thenReturn(cardModel);

        ResponseEntity<?> response = cardService.create(cardModel);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void testFindById_ReturnsCorrectData() {
        when(cardRepo.findById(1L)).thenReturn(Optional.of(cardModel));

        ResponseEntity<CardModel> response = cardService.findById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("1234-5678-9876-5432", response.getBody().getCardNumber());
    }

    @Test
    void testCreate_SetsTimestamps() {
        when(cardRepo.save(any(CardModel.class))).thenAnswer(invocation -> {
            CardModel model = invocation.getArgument(0);
            model.setCreatedAt(System.currentTimeMillis());
            model.setUpdatedAt(System.currentTimeMillis());
            return model;
        });

        ResponseEntity<?> response = cardService.create(cardModel);
        CardModel savedCard = (CardModel) response.getBody();
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(savedCard.getCreatedAt());
        assertNotNull(savedCard.getUpdatedAt());
    }

    @Test
    void testCreate_VerifySaveInvocation() {
        cardService.create(cardModel);
        verify(cardRepo).save(cardModel);
    }

    @Test
    void testFindById_InvalidId() {
        ResponseEntity<CardModel> response = cardService.findById(-1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testFindById_NullId() {
        ResponseEntity<CardModel> response = cardService.findById(null);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}
