package com.cpsoneghett.walletapi.domain.dto;

import java.sql.Timestamp;
import java.util.List;

public record AssetsResponseDto(List<AssetDto> data, Timestamp timestamp) {
}
