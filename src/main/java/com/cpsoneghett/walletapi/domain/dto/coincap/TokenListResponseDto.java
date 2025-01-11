package com.cpsoneghett.walletapi.domain.dto.coincap;

import java.sql.Timestamp;
import java.util.List;

public record TokenListResponseDto(List<TokenDto> data, Timestamp timestamp) {
}
