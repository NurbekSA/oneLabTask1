package controller;


import org.example.Main;
import org.example.entity.model.BusinessModel;
import org.example.entity.service.BusinessService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = Main.class) // Specify the main application class here
@AutoConfigureMockMvc
class BusinessControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BusinessService businessService;

    @Test
    @WithMockUser(username = "Nurbek")
    void testGetAll() throws Exception {
        BusinessModel business = new BusinessModel();
        business.setId(1L);
        business.setCompanyName("Test Business");

        when(businessService.findAll()).thenReturn(List.of(business));

        mockMvc.perform(get("/api/business"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].companyName").value("Test Business"));
    }

    @Test
    @WithMockUser(username = "Nurbek")
    void testGetById_Found() throws Exception {
        BusinessModel business = new BusinessModel();
        business.setId(1L);
        business.setCompanyName("Test Business"); // Use the correct field name

        when(businessService.findById(1L)).thenReturn(business);

        mockMvc.perform(get("/api/business/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyName").value("Test Business")); // Updated JSON path
    }

    @Test
    @WithMockUser(username = "Nurbek")
    void testCreate() throws Exception {
        BusinessModel business = new BusinessModel();
        business.setId(1L);
        business.setCompanyName("New Business");

        when(businessService.create(any(BusinessModel.class))).thenReturn(business);

        mockMvc.perform(post("/api/business")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"New Business\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyName").value("New Business"));
    }
}
