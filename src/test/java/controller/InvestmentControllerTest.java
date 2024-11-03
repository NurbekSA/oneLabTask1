package controller;

import org.example.Main;
import org.example.entity.model.InvestmentModel;
import org.example.entity.service.InvestmentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
class InvestmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InvestmentService investmentService;

    @Test
    @WithMockUser(username = "Nurbek")
    void testGetAllInvestments() throws Exception {
        InvestmentModel investment = new InvestmentModel();
        investment.setId(1L);
        investment.setAmount(1000d);

        when(investmentService.findAll()).thenReturn(List.of(investment));

        mockMvc.perform(get("/api/investments"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].amount").value(1000));
    }

    @Test
    @WithMockUser(username = "Nurbek")
    void testGetInvestmentById_Found() throws Exception {
        InvestmentModel investment = new InvestmentModel();
        investment.setId(1L);
        investment.setAmount(1000d);

        when(investmentService.findById(1L)).thenReturn(investment);

        mockMvc.perform(get("/api/investments/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(1000));
    }

    @Test
    @WithMockUser(username = "Nurbek")
    void testCreateInvestment() throws Exception {
        InvestmentModel investment = new InvestmentModel();
        investment.setId(1L);
        investment.setAmount(1000d);

        when(investmentService.create(any(InvestmentModel.class), anyLong())).thenReturn(investment);

        mockMvc.perform(post("/api/investments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("cardId", "1")
                        .content("{\"amount\": 1000}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(1000));
    }

    @Test
    @WithMockUser(username = "Nurbek")
    void testActivateInvestment() throws Exception {
        InvestmentModel investment = new InvestmentModel();
        investment.setId(1L);
        investment.setIsActive(true);

        when(investmentService.setActive(1L)).thenReturn(investment);

        mockMvc.perform(put("/api/investments/1/activate"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isActive").value(true));
    }

    @Test
    @WithMockUser(username = "Nurbek")
    void testLogicalDeleteInvestment() throws Exception {
        InvestmentModel investment = new InvestmentModel();
        investment.setId(1L);
        investment.setIsAlive(false);

        when(investmentService.logicalDelete(1L)).thenReturn(investment);

        mockMvc.perform(put("/api/investments/1/logical-delete"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isAlive").value(false));
    }

    @Test
    @WithMockUser(username = "Nurbek")
    void testDeleteInvestment() throws Exception {
        Mockito.doNothing().when(investmentService).delete(1L);

        mockMvc.perform(delete("/api/investments/1"))
                .andExpect(status().isNoContent());
    }
}
