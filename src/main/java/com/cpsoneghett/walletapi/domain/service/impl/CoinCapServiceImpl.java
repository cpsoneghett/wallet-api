package com.cpsoneghett.walletapi.domain.service.impl;

import com.cpsoneghett.walletapi.domain.dto.AssetsResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CoinCapServiceImpl {

    @Value("${COIN_CAP_BASE_URL}")
    private String COIN_CAP_BASE_URL;

    private RestTemplate restTemplate;

    public CoinCapServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public AssetsResponseDto getAllAssets() {
        return restTemplate.getForObject(COIN_CAP_BASE_URL + "assets", AssetsResponseDto.class);
    }


}
