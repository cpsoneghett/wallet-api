package com.cpsoneghett.walletapi.domain.service;

import com.cpsoneghett.walletapi.domain.dto.coincap.TokenHistoryResponseDto;
import com.cpsoneghett.walletapi.domain.dto.coincap.TokenListResponseDto;
import com.cpsoneghett.walletapi.domain.dto.coincap.TokenSingleResponseDto;

public interface CoinCapService {

    TokenListResponseDto getAllAssets();

    TokenSingleResponseDto getAssetById(String assetId);

    TokenHistoryResponseDto getTokenHistory(String assetName, String interval, String period, String baseUrl);

}
