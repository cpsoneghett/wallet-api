package com.cpsoneghett.walletapi.domain.dto.coincap;

import java.sql.Timestamp;

public record TokenSingleResponseDto(TokenDto data, Timestamp timestamp) {
}
