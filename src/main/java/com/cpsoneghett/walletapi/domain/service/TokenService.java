package com.cpsoneghett.walletapi.domain.service;

import com.cpsoneghett.walletapi.domain.dto.TokenUpdateRequestDto;
import com.cpsoneghett.walletapi.domain.entity.Token;

import java.util.List;

public interface TokenService {

    void add();
    void updateTokens(TokenUpdateRequestDto tokenList);

    void updateTokensHistoryList();
}
