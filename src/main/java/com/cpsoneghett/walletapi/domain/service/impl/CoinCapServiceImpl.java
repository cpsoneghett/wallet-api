package com.cpsoneghett.walletapi.domain.service.impl;

import com.cpsoneghett.walletapi.domain.dto.coincap.TokenHistoryResponseDto;
import com.cpsoneghett.walletapi.domain.dto.coincap.TokenListResponseDto;
import com.cpsoneghett.walletapi.domain.dto.coincap.TokenSingleResponseDto;
import com.cpsoneghett.walletapi.domain.service.CoinCapService;
import com.cpsoneghett.walletapi.domain.service.GlobalParameterService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CoinCapServiceImpl implements CoinCapService {

    private final RestTemplate restTemplate;
    private final GlobalParameterService globalParameterService;


    public CoinCapServiceImpl(RestTemplate restTemplate, GlobalParameterService globalParameterService) {
        this.restTemplate = restTemplate;
        this.globalParameterService = globalParameterService;
    }

    public TokenListResponseDto getAllAssets() {
        return restTemplate.getForObject(getCoinCapBaseUrl() + "assets", TokenListResponseDto.class);
    }

    @Override
    public TokenSingleResponseDto getAssetById(String assetId) {
        return restTemplate.getForObject(getCoinCapBaseUrl() + "assets/" + assetId, TokenSingleResponseDto.class);
    }

    @Override
    public TokenHistoryResponseDto getTokenHistory(String assetName, String interval, String period) {

        if (interval == null || interval.isEmpty())
            interval = globalParameterService.getValueByKey("COIN_CAP_ASSET_TIME_INTERVAL");

        if (period == null || period.isEmpty())
            period = globalParameterService.getValueByKey("COIN_CAP_ASSET_PERIOD");

        StringBuilder historyUrl = new StringBuilder();
        historyUrl.append(getCoinCapBaseUrl())
                .append("assets/")
                .append(assetName)
                .append("/history?interval=")
                .append(period)
                .append(interval);

        return restTemplate.getForObject(historyUrl.toString(), TokenHistoryResponseDto.class);
    }

    @Override
    public TokenHistoryResponseDto getTokenHistory(String assetName) {

        String interval = globalParameterService.getValueByKey("COIN_CAP_ASSET_TIME_INTERVAL");
        String period = globalParameterService.getValueByKey("COIN_CAP_ASSET_PERIOD");

        StringBuilder historyUrl = new StringBuilder();
        historyUrl.append(getCoinCapBaseUrl())
                .append("assets/")
                .append(assetName)
                .append("/history?interval=")
                .append(period)
                .append(interval);

        return restTemplate.getForObject(historyUrl.toString(), TokenHistoryResponseDto.class);
    }

    private String getCoinCapBaseUrl() {
        return globalParameterService.getValueByKey("COIN_CAP_BASE_URL");
    }

}
