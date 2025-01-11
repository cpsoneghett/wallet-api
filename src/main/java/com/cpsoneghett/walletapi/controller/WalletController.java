package com.cpsoneghett.walletapi.controller;

import com.cpsoneghett.walletapi.domain.dto.WalletCreateRequestDto;
import com.cpsoneghett.walletapi.domain.dto.WalletCreateResponseDto;
import com.cpsoneghett.walletapi.domain.service.WalletService;
import org.bitcoinj.crypto.MnemonicException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/wallet")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping("/create")
    public ResponseEntity<WalletCreateResponseDto> create(@RequestBody WalletCreateRequestDto request) throws MnemonicException.MnemonicLengthException {
        return new ResponseEntity<>(walletService.create(request), HttpStatus.CREATED);
    }
}
