package com.cpsoneghett.walletapi.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AssetDto(String id,
                       String rank,
                       String symbol,
                       String name,
                       String supply,
                       String maxSupply,
                       String marketCapUsd,
                       String volumeUsd24Hr,
                       String priceUsd,
                       String changePercent24Hr,
                       String vwap24Hr) {
}
