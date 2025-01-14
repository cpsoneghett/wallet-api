package com.cpsoneghett.walletapi.controller;

import com.cpsoneghett.walletapi.domain.dto.AssetRequestDto;
import com.cpsoneghett.walletapi.domain.dto.WalletCreateRequestDto;
import com.cpsoneghett.walletapi.domain.dto.WalletCreateResponseDto;
import com.cpsoneghett.walletapi.domain.dto.WalletEvaluationResponseDto;
import com.cpsoneghett.walletapi.domain.dto.WalletResponseDto;
import com.cpsoneghett.walletapi.domain.enums.OperationType;
import com.cpsoneghett.walletapi.domain.service.WalletService;
import org.bitcoinj.crypto.MnemonicException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{privateKey}")
    public ResponseEntity<WalletResponseDto> showInfo(@PathVariable String privateKey)  {
        return new ResponseEntity<>(walletService.get(privateKey), HttpStatus.CREATED);
    }

    @PostMapping("/add-asset")
    public ResponseEntity<WalletResponseDto> addAssetToWallet(@RequestBody AssetRequestDto request) {
        return new ResponseEntity<>(walletService.addAsset(request, OperationType.CREATE), HttpStatus.OK);
    }

    @PutMapping("/update-asset")
    public ResponseEntity<WalletResponseDto> updateAssetInWallet(@RequestBody AssetRequestDto request) {
        return new ResponseEntity<>(walletService.addAsset(request, OperationType.UPDATE), HttpStatus.OK);
    }

    @PostMapping("/evaluate")
    public ResponseEntity<WalletEvaluationResponseDto> updateAssetInWallet(@RequestBody String assets) {
        return new ResponseEntity<>(walletService.evaluate(assets), HttpStatus.OK);
    }
}
