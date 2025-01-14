package com.cpsoneghett.walletapi.domain.dto;

import java.math.BigDecimal;

public record AssetRequestDto(String privateKey, String token, BigDecimal amount) {
}
