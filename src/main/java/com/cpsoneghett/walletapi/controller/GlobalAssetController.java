package com.cpsoneghett.walletapi.controller;

import com.cpsoneghett.walletapi.domain.service.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/token")
public class GlobalAssetController {

    private final TokenService tokenService;

    public GlobalAssetController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/add-all")
    public ResponseEntity<String> addGlobalTokens() {
        tokenService.add();
        return new ResponseEntity<>("Global Assets added", HttpStatus.OK);
    }
}
