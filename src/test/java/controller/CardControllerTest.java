package controller;

import org.example.Main;
import org.example.entity.model.CardModel;
import org.example.entity.service.CardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
class CardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CardService cardService;

    @Test
    @WithMockUser(username = "Nurbek")
    void testGetCardById_Found() throws Exception {
        CardModel card = new CardModel();
        card.setId(1L);
        card.setCardNumber("1234-5678-9876-5432");

        when(cardService.findById(1L)).thenReturn(card);

        mockMvc.perform(get("/card/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cardNumber").value("1234-5678-9876-5432"));
    }


    @Test
    @WithMockUser(username = "Nurbek")
    void testCreateCard() throws Exception {
        CardModel card = new CardModel();
        card.setId(1L);
        card.setCardNumber("1234-5678-9876-5432");

        when(cardService.create(any(CardModel.class))).thenReturn(card);

        mockMvc.perform(post("/card")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"cardNumber\": \"1234-5678-9876-5432\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cardNumber").value("1234-5678-9876-5432"));
    }
}
