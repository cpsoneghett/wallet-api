package com.cpsoneghett.walletapi.domain.service.impl;

import com.cpsoneghett.walletapi.domain.dto.coincap.TokenHistoryRequestDto;
import com.cpsoneghett.walletapi.domain.dto.coincap.TokenHistoryResponseDto;
import com.cpsoneghett.walletapi.domain.dto.coincap.TokenListResponseDto;
import com.cpsoneghett.walletapi.domain.dto.coincap.TokenSingleResponseDto;
import com.cpsoneghett.walletapi.domain.repository.TokenRepository;
import com.cpsoneghett.walletapi.domain.service.AssetService;
import com.cpsoneghett.walletapi.domain.service.CoinCapService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AssetServiceImpl implements AssetService {

    private final CoinCapService coinCapService;
    private final TokenRepository globalAssetRepository;

    public AssetServiceImpl(CoinCapService coinCapService, TokenRepository globalAssetRepository) {
        this.coinCapService = coinCapService;
        this.globalAssetRepository = globalAssetRepository;
    }

    @Override
    public TokenHistoryResponseDto getAssetHistory(String assetName, TokenHistoryRequestDto request) {

        if (request == null)
            request = new TokenHistoryRequestDto(null, null);

        return coinCapService.getTokenHistory(assetName, request.interval(), request.period());
    }

    @Override
    public TokenListResponseDto getAllAssets() {
        return coinCapService.getAllAssets();
    }

    @Override
    public TokenSingleResponseDto getAsset(String assetId) {
        return coinCapService.getAssetById(assetId);
    }

    @Override
    public void updateAssetHistory() {
        System.out.println("AssetHistory Updated - " + LocalDateTime.now());
    }
}
