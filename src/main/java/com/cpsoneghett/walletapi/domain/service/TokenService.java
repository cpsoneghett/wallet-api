package com.cpsoneghett.walletapi.domain.service;

import com.cpsoneghett.walletapi.domain.dto.TokenUpdateRequestDto;
import com.cpsoneghett.walletapi.domain.dto.coincap.TokenListResponseDto;
import com.cpsoneghett.walletapi.domain.dto.coincap.TokenSingleResponseDto;

public interface TokenService {

    void add();

    void updateTokens(TokenUpdateRequestDto tokenList);

    void updateTokensHistoryList();

    TokenListResponseDto getAllTokens();

    TokenSingleResponseDto getToken(String tokenId);
}
