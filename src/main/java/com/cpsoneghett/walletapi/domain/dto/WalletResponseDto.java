package com.cpsoneghett.walletapi.domain.dto;

import java.math.BigDecimal;
import java.util.List;

public record WalletResponseDto(String id, BigDecimal total, List<AssetDto> assets) {
}
