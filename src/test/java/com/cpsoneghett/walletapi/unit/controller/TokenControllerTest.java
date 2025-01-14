package com.cpsoneghett.walletapi.unit.controller;

import com.cpsoneghett.walletapi.controller.TokenController;
import com.cpsoneghett.walletapi.domain.service.TokenService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
public class TokenControllerTest {

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private TokenController tokenController;

    @Test
    void testAddGlobalTokens() {
        doNothing().when(tokenService).add();

        ResponseEntity<String> response = tokenController.addGlobalTokens();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Global Assets added", response.getBody());
    }
}
