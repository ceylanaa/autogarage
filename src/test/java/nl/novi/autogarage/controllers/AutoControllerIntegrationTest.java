package nl.novi.autogarage.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// hier log ik echt in via /auth/login zodat de jwt filter ook getest wordt
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AutoControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private String haalTokenOp() throws Exception {
        String result = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"gebruikersnaam\": \"kassamedewerker\", \"wachtwoord\": \"wachtwoord123\"}"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        JsonNode node = objectMapper.readTree(result);
        return node.get("token").asText();
    }

    @Test
    void getAlleAutos_metToken_geeft200() throws Exception {
        String token = haalTokenOp();

        mockMvc.perform(get("/autos").header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].kenteken").value("AB-123-C"));
    }

    @Test
    void createAuto_valideBody_geeft201() throws Exception {
        String token = haalTokenOp();
        String json = """
                {
                  "kenteken": "ZZ-111-ZZ",
                  "merk": "Ford",
                  "model": "Fiesta",
                  "klantId": 1
                }
                """;

        mockMvc.perform(post("/autos")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.kenteken").value("ZZ-111-ZZ"))
                .andExpect(jsonPath("$.klantNaam").value("Jan de Vries"));
    }

    @Test
    void createAuto_onbekendeKlant_geeft404() throws Exception {
        String token = haalTokenOp();
        String json = "{\"kenteken\": \"QQ-222-QQ\", \"merk\": \"Ford\", \"model\": \"Ka\", \"klantId\": 999}";

        mockMvc.perform(post("/autos")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound());
    }
}
