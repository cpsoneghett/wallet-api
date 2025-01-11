package com.cpsoneghett.walletapi.controller;

import com.cpsoneghett.walletapi.domain.dto.TokenUpdateRequestDto;
import com.cpsoneghett.walletapi.domain.dto.coincap.TokenHistoryRequestDto;
import com.cpsoneghett.walletapi.domain.service.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/token")
public class TokenController {

    private final TokenService tokenService;

    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/{assetId}/history")
    public ResponseEntity<String> getAssetHistory(@PathVariable String assetId, @RequestBody @Nullable TokenHistoryRequestDto request) {

        //TODO: implement history
        return new ResponseEntity<>(" ", HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateTokens(@RequestBody @Nullable TokenUpdateRequestDto request) {

        tokenService.updateTokens(request);
        return new ResponseEntity<>("Tokens updated.", HttpStatus.OK);
    }

}
