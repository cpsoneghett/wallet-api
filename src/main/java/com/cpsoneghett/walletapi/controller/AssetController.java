package com.cpsoneghett.walletapi.controller;

import com.cpsoneghett.walletapi.domain.dto.coincap.TokenListResponseDto;
import com.cpsoneghett.walletapi.domain.dto.coincap.TokenSingleResponseDto;
import com.cpsoneghett.walletapi.domain.service.AssetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/asset")
public class AssetController {

    private final AssetService assetService;

    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    @GetMapping("/all")
    public ResponseEntity<TokenListResponseDto> listAllAssets() {
        TokenListResponseDto response = assetService.getAllAssets();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{assetId}")
    public ResponseEntity<TokenSingleResponseDto> getAsset(@PathVariable String assetId) {
        TokenSingleResponseDto response = assetService.getAsset(assetId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
