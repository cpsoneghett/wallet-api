package com.cpsoneghett.walletapi.domain.service;

import com.cpsoneghett.walletapi.domain.dto.coincap.TokenHistoryRequestDto;
import com.cpsoneghett.walletapi.domain.dto.coincap.TokenHistoryResponseDto;
import com.cpsoneghett.walletapi.domain.dto.coincap.TokenListResponseDto;
import com.cpsoneghett.walletapi.domain.dto.coincap.TokenSingleResponseDto;

public interface AssetService {

    TokenHistoryResponseDto getAssetHistory(String assetName, TokenHistoryRequestDto request);

    TokenListResponseDto getAllAssets();

    TokenSingleResponseDto getAsset(String assetId);
    void updateAssetHistory();
}
