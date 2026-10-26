package nl.novi.autogarage.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class KlantControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser(roles = "KASSAMEDEWERKER")
    void getAlleKlanten_metRol_geeft200() throws Exception {
        mockMvc.perform(get("/klanten"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", greaterThanOrEqualTo(3)));
    }

    @Test
    @WithMockUser(roles = "KASSAMEDEWERKER")
    void createKlant_valideBody_geeft201() throws Exception {
        String json = """
                {
                  "naam": "Test Klant",
                  "telefoonnummer": "0600000000",
                  "emailadres": "test@mail.nl"
                }
                """;

        mockMvc.perform(post("/klanten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.naam").value("Test Klant"));
    }

    @Test
    @WithMockUser(roles = "KASSAMEDEWERKER")
    void createKlant_zonderNaam_geeft400() throws Exception {
        mockMvc.perform(post("/klanten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"telefoonnummer\": \"0600000000\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAlleKlanten_zonderLogin_geeft403() throws Exception {
        mockMvc.perform(get("/klanten"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "MONTEUR")
    void getAlleKlanten_verkeerdeRol_geeft403() throws Exception {
        mockMvc.perform(get("/klanten"))
                .andExpect(status().isForbidden());
    }
}
