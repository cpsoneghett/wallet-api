package com.cpsoneghett.walletapi.domain.dto;

import java.time.LocalDateTime;

public record WalletCreateResponseDto(String email, String publicAddress, String privateKey, LocalDateTime createdAt) {
}
