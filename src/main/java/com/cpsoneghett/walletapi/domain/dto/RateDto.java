package com.cpsoneghett.walletapi.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RateDto(String id,
                      String symbol,
                      String currencySymbol,
                      String type,
                      String rateUsd) {
}
