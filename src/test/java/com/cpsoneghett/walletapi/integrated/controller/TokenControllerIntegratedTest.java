package com.cpsoneghett.walletapi.integrated.controller;

import com.cpsoneghett.walletapi.domain.dto.TokenUpdateRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class TokenControllerIntegratedTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    public void waitUntilStart() throws InterruptedException {
        Thread.sleep(15000);
    }

    @Test
    void testAddGlobalTokens() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/token/add-all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Global Assets added"));
    }

    @Test
    void testUpdateTokens_WithValidRequest() throws Exception {

        TokenUpdateRequestDto requestPayload = new TokenUpdateRequestDto("BTC,ETH");

        ObjectMapper mapper = new ObjectMapper();
        String jsonPayload = mapper.writeValueAsString(requestPayload);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/token/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isOk())
                .andExpect(content().string("Tokens updated."));
    }

    @Test
    void testUpdateTokens_WithEmptyRequest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/token/update")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Bad Request"));

    }
}
