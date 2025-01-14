package com.cpsoneghett.walletapi.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record WalletEvaluationResponseDto(BigDecimal total,
                                          @JsonProperty("best_asset") String bestAsset,
                                          @JsonProperty("best_performance") BigDecimal bestPerformance,
                                          @JsonProperty("worst_asset") String worstAsset,
                                          @JsonProperty("worst_performance") BigDecimal worstPerformance) {
}
