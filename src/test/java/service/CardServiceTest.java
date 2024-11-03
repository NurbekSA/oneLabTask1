package service;

import org.example.entity.model.CardModel;
import org.example.entity.model.exception.ResourceNotFoundException;
import org.example.entity.repository.CardRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import org.example.entity.service.CardService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CardServiceTest {

    @Mock
    private CardRepo cardRepo;

    @InjectMocks
    private CardService cardService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById_whenCardExists_shouldReturnCard() {
        Long id = 1L;
        CardModel cardModel = new CardModel();
        when(cardRepo.findById(id)).thenReturn(Optional.of(cardModel));

        CardModel result = cardService.findById(id);

        assertNotNull(result);
        verify(cardRepo, times(1)).findById(id);
    }

    @Test
    void testFindById_whenCardDoesNotExist_shouldThrowException() {
        Long id = 1L;
        when(cardRepo.findById(id)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> cardService.findById(id));
        assertEquals("Card with id " + id + " not found", exception.getMessage());
        verify(cardRepo, times(1)).findById(id);
    }

    @Test
    void testCreate_shouldSaveAndReturnCardModel() {
        CardModel cardModel = new CardModel();
        when(cardRepo.save(cardModel)).thenReturn(cardModel);

        CardModel result = cardService.create(cardModel);

        assertNotNull(result);
        assertEquals(cardModel, result);
        verify(cardRepo, times(1)).save(cardModel);
    }
}
