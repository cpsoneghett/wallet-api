package com.cpsoneghett.walletapi.domain.dto;

import java.math.BigDecimal;

public record AssetDto(String symbol, BigDecimal quantity, BigDecimal price, BigDecimal value) {
}
