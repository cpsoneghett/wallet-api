package com.cpsoneghett.walletapi.domain.dto.coincap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TokenDto(String id,
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
