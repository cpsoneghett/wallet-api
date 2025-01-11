package com.cpsoneghett.walletapi.domain.dto.coincap;

import java.sql.Timestamp;
import java.util.List;

public record TokenHistoryResponseDto(List<TokenHistoryDto> data, Timestamp timestamp) {
}
