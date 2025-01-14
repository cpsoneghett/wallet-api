package com.cpsoneghett.walletapi.domain.service.impl;

import com.cpsoneghett.walletapi.domain.dto.coincap.TokenHistoryResponseDto;
import com.cpsoneghett.walletapi.domain.dto.coincap.TokenListResponseDto;
import com.cpsoneghett.walletapi.domain.dto.coincap.TokenSingleResponseDto;
import com.cpsoneghett.walletapi.domain.service.CoinCapService;
import com.cpsoneghett.walletapi.domain.service.GlobalParameterService;
import com.cpsoneghett.walletapi.exception.CoinCapApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class CoinCapServiceImpl implements CoinCapService {

    private static final Logger logger = LoggerFactory.getLogger(CoinCapServiceImpl.class);

    private final RestTemplate restTemplate;
    private final GlobalParameterService globalParameterService;


    public CoinCapServiceImpl(RestTemplate restTemplate, GlobalParameterService globalParameterService) {
        this.restTemplate = restTemplate;
        this.globalParameterService = globalParameterService;
    }

    @Override
    public TokenListResponseDto getAllAssets() {
        try {
            return restTemplate.getForObject(buildUrl(null, "assets"), TokenListResponseDto.class);
        } catch (RestClientException e) {
            logger.error("Error fetching all assets from CoinCap API", e);
            throw new CoinCapApiException("Failed to fetch all assets", e);
        }
    }

    @Override
    public TokenSingleResponseDto getAssetById(String assetId) {
        try {
            return restTemplate.getForObject(buildUrl(null, "assets/" + assetId), TokenSingleResponseDto.class);
        } catch (RestClientException e) {
            logger.error("Error fetching asset with id {} from CoinCap API", assetId, e);
            throw new CoinCapApiException("Failed to fetch asset by ID", e);
        }
    }

    @Override
    public TokenHistoryResponseDto getTokenHistory(String assetName, String interval, String period, String baseUrl) {
        // Use dynamic parameters from global service if not provided
        if (interval == null || interval.isEmpty()) {
            interval = globalParameterService.getValueByKey("COIN_CAP_ASSET_TIME_INTERVAL");
        }

        if (period == null || period.isEmpty()) {
            period = globalParameterService.getValueByKey("COIN_CAP_ASSET_PERIOD");
        }

        return fetchTokenHistory(assetName, interval, period, baseUrl);
    }

    private TokenHistoryResponseDto fetchTokenHistory(String assetName, String interval, String period, String baseUrl) {
        try {
            String url = buildUrl(baseUrl, "assets/" + assetName + "/history?interval=" + period + interval);
            return restTemplate.getForObject(url, TokenHistoryResponseDto.class);
        } catch (RestClientException e) {
            logger.error("Error fetching history for asset {} from CoinCap API", assetName, e);
            throw new CoinCapApiException("Failed to fetch token history", e);
        }
    }

    public String buildUrl(String baseUrl, String path) {
        if (baseUrl == null || baseUrl.isEmpty())
            baseUrl = globalParameterService.getValueByKey("COIN_CAP_BASE_URL");
        return baseUrl + path;
    }

}
