package com.cpsoneghett.walletapi.domain.service.impl;

import com.cpsoneghett.walletapi.domain.dto.TokenUpdateRequestDto;
import com.cpsoneghett.walletapi.domain.dto.coincap.TokenDto;
import com.cpsoneghett.walletapi.domain.dto.coincap.TokenHistoryDto;
import com.cpsoneghett.walletapi.domain.dto.coincap.TokenHistoryResponseDto;
import com.cpsoneghett.walletapi.domain.dto.coincap.TokenListResponseDto;
import com.cpsoneghett.walletapi.domain.entity.Token;
import com.cpsoneghett.walletapi.domain.entity.TokenHistory;
import com.cpsoneghett.walletapi.domain.repository.TokenHistoryRepository;
import com.cpsoneghett.walletapi.domain.repository.TokenRepository;
import com.cpsoneghett.walletapi.domain.service.CoinCapService;
import com.cpsoneghett.walletapi.domain.service.TokenService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class TokenServiceImpl implements TokenService {

    private static final Logger logger = LoggerFactory.getLogger(TokenServiceImpl.class);

    private final TokenRepository tokenRepository;
    private final TokenHistoryRepository tokenHistoryRepository;
    private final CoinCapService coinCapService;

    public TokenServiceImpl(TokenRepository tokenRepository, TokenHistoryRepository tokenHistoryRepository, CoinCapService coinCapService) {
        this.tokenRepository = tokenRepository;
        this.tokenHistoryRepository = tokenHistoryRepository;
        this.coinCapService = coinCapService;
        logger.info("TokenServiceImpl initialized.");
    }

    @Override
    @PostConstruct
    public void add() {
        logger.info(">>> Starting to add tokens to the database <<<");

        List<Token> tokenList = tokenRepository.findAll();

        if (tokenList.isEmpty()) {
            logger.info("No tokens found in the database. Importing tokens from CoinCap...");

            try {
                TokenListResponseDto coinCapTokenList = coinCapService.getAllAssets();
                coinCapTokenList.data().forEach(token -> tokenList.add(new Token(token)));

                tokenRepository.saveAll(tokenList);
                logger.info("Successfully imported {} tokens from CoinCap to the database.", tokenList.size());
            } catch (Exception e) {
                logger.error("Failed to import tokens from CoinCap: {}", e.getMessage(), e);
            }
        } else {
            logger.warn("Tokens were already imported. Skipping the import process.");
        }
    }

    @Override
    public void updateTokens(TokenUpdateRequestDto request) {
        logger.info(">>> Starting to update tokens to the database <<<");

        List<Token> tokenList = new ArrayList<>();
        String[] tokens = request.tokens().split(",");

        for (String token : tokens)
            tokenList.add(tokenRepository.findBySearchParam(token).get());

        if (tokenList.isEmpty()) {
            logger.info("!!! No specific tokens passed to update. Getting all tokens from the database !!!");
            tokenList = tokenRepository.findAll();
        }

        Map<String, Token> tokenMap = tokenList.stream()
                .collect(Collectors.toMap(Token::getIdName, token -> token));

        try {
            TokenListResponseDto coinCapTokenList = coinCapService.getAllAssets();
            List<TokenDto> newTokens = coinCapTokenList.data();

            for (TokenDto tokenDto : newTokens) {
                String tokenIdName = tokenDto.id();

                if (tokenMap.containsKey(tokenIdName)) {
                    Token existingToken = tokenMap.get(tokenIdName);

                    existingToken.setSupply(new BigDecimal(tokenDto.supply()));
                    existingToken.setMaxSupply(new BigDecimal(tokenDto.maxSupply()));
                    existingToken.setMarketCapUsd(new BigDecimal(tokenDto.marketCapUsd()));
                    existingToken.setVolumeUsd24Hr(new BigDecimal(tokenDto.volumeUsd24Hr()));
                    existingToken.setPriceUsd(new BigDecimal(tokenDto.priceUsd()));
                    existingToken.setVolumeUsd24Hr(new BigDecimal(tokenDto.volumeUsd24Hr()));
                    existingToken.setVwap24Hr(new BigDecimal(tokenDto.vwap24Hr()));
                }
            }

            tokenRepository.saveAll(tokenList);
            logger.info("Successfully updated tokens in the database.");

        } catch (Exception e) {
            logger.error("Failed to update tokens: {}", e.getMessage(), e);
        }
    }

    @Override
    public void updateTokensHistoryList() {
        logger.info(">>> Starting the process to update token history <<<");

        List<Token> tokenList = tokenRepository.findAll();
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        logger.info("Found {} tokens to process.", tokenList.size());

        for (Token token : tokenList) {
            logger.info("Starting asynchronous processing for token: {}", token.getIdName());
            CompletableFuture<Void> future = processTokenHistory(token);
            futures.add(future);
        }

        // Wait for all tasks to complete
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        logger.info("Token history update process completed for all tokens.");
    }

    @Async
    public CompletableFuture<Void> processTokenHistory(Token token) {
        logger.info("Processing token: {}", token.getIdName());

        try {
            TokenHistoryResponseDto coinCapTokenHistory = coinCapService.getTokenHistory(token.getIdName());
            logger.info("Retrieved {} history entries from CoinCap for token: {}", coinCapTokenHistory.data().size(), token.getIdName());

            Set<Timestamp> existingTimestamps = tokenHistoryRepository.findAllByIdToken(token.getId())
                    .stream()
                    .map(TokenHistory::getCoinCapTimestamp)
                    .collect(Collectors.toSet());
            logger.info("Found {} existing timestamps in the database for token: {}", existingTimestamps.size(), token.getIdName());

            List<TokenHistoryDto> newHistoryDtos = coinCapTokenHistory.data()
                    .stream()
                    .filter(dto -> !existingTimestamps.contains(dto.time()))
                    .toList();
            logger.info("Identified {} new entries to be added to the database for token: {}", newHistoryDtos.size(), token.getIdName());

            List<TokenHistory> newHistories = newHistoryDtos.stream()
                    .map(dto -> new TokenHistory(
                            token.getId(),
                            new BigDecimal(dto.priceUsd()),
                            dto.time()))
                    .toList();

            tokenHistoryRepository.saveAll(newHistories);
            logger.info("Saved {} new token history entries for token: {}", newHistories.size(), token.getIdName());

        } catch (Exception e) {
            logger.error("Error processing token: {}. Message: {}", token.getIdName(), e.getMessage(), e);
        }

        return CompletableFuture.completedFuture(null);
    }
}
