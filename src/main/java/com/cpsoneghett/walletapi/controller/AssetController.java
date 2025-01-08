package com.cpsoneghett.walletapi.controller;

import com.cpsoneghett.walletapi.domain.dto.AssetsResponseDto;
import com.cpsoneghett.walletapi.domain.service.impl.CoinCapServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/asset")
public class AssetController {

    private CoinCapServiceImpl coinCapService;

    public AssetController(CoinCapServiceImpl coinCapService) {
        this.coinCapService = coinCapService;
    }

    @GetMapping("/all")
    public ResponseEntity<AssetsResponseDto> listAllAssets() {
        AssetsResponseDto response = coinCapService.getAllAssets();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssetsResponseDto> getAsset(@PathVariable String id) {
        AssetsResponseDto response = coinCapService.getAllAssets();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
