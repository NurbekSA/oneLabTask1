package controller;


import org.example.Main;
import org.example.entity.model.InvestorModel;
import org.example.entity.service.InvestorService;
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
class InvestorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InvestorService investorService;

    @Test
    @WithMockUser(username = "Nurbek")
    void testGetInvestorById_Found() throws Exception {
        InvestorModel investor = new InvestorModel();
        investor.setId(1L);
        investor.setFio("John Doe");
        investor.setScore(1000d);

        when(investorService.findById(1L)).thenReturn(investor);

        mockMvc.perform(get("/api/investor/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fio").value("John Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.score").value(1000));
    }

    @Test
    @WithMockUser(username = "Nurbek")
    void testCreateInvestor() throws Exception {
        InvestorModel investor = new InvestorModel();
        investor.setId(1L);
        investor.setFio("John Doe");
        investor.setScore(1000d);

        when(investorService.create(any(InvestorModel.class))).thenReturn(investor);

        mockMvc.perform(post("/api/investor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"fio\": \"John Doe\", \"score\": 1000}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fio").value("John Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.score").value(1000d));
    }
}