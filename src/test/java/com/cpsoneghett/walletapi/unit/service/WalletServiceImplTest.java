package com.cpsoneghett.walletapi.unit.service;

import com.cpsoneghett.walletapi.domain.dto.WalletCreateRequestDto;
import com.cpsoneghett.walletapi.domain.dto.WalletCreateResponseDto;
import com.cpsoneghett.walletapi.domain.dto.WalletEvaluationResponseDto;
import com.cpsoneghett.walletapi.domain.dto.WalletResponseDto;
import com.cpsoneghett.walletapi.domain.dto.coincap.TokenDto;
import com.cpsoneghett.walletapi.domain.dto.coincap.TokenSingleResponseDto;
import com.cpsoneghett.walletapi.domain.entity.Token;
import com.cpsoneghett.walletapi.domain.entity.Wallet;
import com.cpsoneghett.walletapi.domain.repository.TokenRepository;
import com.cpsoneghett.walletapi.domain.repository.WalletRepository;
import com.cpsoneghett.walletapi.domain.service.CoinCapService;
import com.cpsoneghett.walletapi.domain.service.impl.WalletServiceImpl;
import org.bitcoinj.crypto.MnemonicException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WalletServiceImplTest {

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private CoinCapService coinCapService;

    @InjectMocks
    private WalletServiceImpl walletService;

    public static TokenSingleResponseDto createBTCMock() {
        TokenDto btcTokenDto = new TokenDto(
                "bitcoin",
                "1",
                "BTC",
                "Bitcoin",
                "19000000",
                "21000000",
                "1200000000000",
                "35000000000",
                "60000",
                "2.5",
                "58000"
        );
        return new TokenSingleResponseDto(btcTokenDto, new Timestamp(System.currentTimeMillis()));
    }

    public static TokenSingleResponseDto createETHMock() {
        TokenDto ethTokenDto = new TokenDto(
                "ethereum",
                "2",
                "ETH",
                "Ethereum",
                "120000000",
                null,
                "500000000000",
                "15000000000",
                "4000",
                "1.8",
                "3900"
        );
        return new TokenSingleResponseDto(ethTokenDto, new Timestamp(System.currentTimeMillis()));
    }

    @Test
    void testCreateWallet() throws Exception {
        WalletCreateRequestDto request = new WalletCreateRequestDto("test@example.com");
        Wallet mockWallet = new Wallet("publicKey", "privateKey");
        mockWallet.setEmail("test@example.com");

        when(walletRepository.save(any(Wallet.class))).thenReturn(mockWallet);

        WalletCreateResponseDto response = walletService.create(request);

        assertNotNull(response);
        assertEquals("test@example.com", response.email());
        assertEquals("publicKey", response.publicAddress());
        assertEquals("privateKey", response.privateKey());
    }

    @Test
    void testGetWallet() {
        String privateKey = "testPrivateKey";
        Wallet mockWallet = new Wallet("publicKey", privateKey);
        mockWallet.setEmail("test@example.com");
        mockWallet.setTotal(new BigDecimal("100000"));

        when(walletRepository.findByPrivateKey(privateKey)).thenReturn(Optional.of(mockWallet));

        WalletResponseDto response = walletService.get(privateKey);

        assertNotNull(response);
        assertEquals("publicKey", response.id());
        assertEquals(new BigDecimal("100000"), response.total());
    }

    @Test
    void testEvaluateAssets() {
        String assetsInput = "BTC,1.0,50000\nETH,2.0,2000";

        Token mockBTC = new Token();
        mockBTC.setSymbol("BTC");
        mockBTC.setName("Bitcoin");
        mockBTC.setPriceUsd(new BigDecimal("60000"));
        mockBTC.setIdName("bitcoin");

        Token mockETH = new Token();
        mockETH.setSymbol("ETH");
        mockETH.setName("Ethereum");
        mockETH.setPriceUsd(new BigDecimal("2500"));
        mockETH.setIdName("ethereum");

        when(tokenRepository.findBySearchParam("BTC")).thenReturn(Optional.of(mockBTC));
        when(tokenRepository.findBySearchParam("ETH")).thenReturn(Optional.of(mockETH));
        when(coinCapService.getAssetById("bitcoin")).thenReturn(createBTCMock());
        when(coinCapService.getAssetById("ethereum")).thenReturn(createETHMock());

        WalletEvaluationResponseDto response = walletService.evaluate(assetsInput);

        assertNotNull(response);
        assertEquals(new BigDecimal("68000.0"), response.total());
        assertEquals("ETH", response.bestAsset());
        assertEquals(new BigDecimal("100.00"), response.bestPerformance());
        assertEquals("BTC", response.worstAsset());
        assertEquals(new BigDecimal("20.00"), response.worstPerformance());
    }

    @Test
    void testCreateWalletThrowsExceptionForInvalidRequest() {
        WalletCreateRequestDto request1 = new WalletCreateRequestDto(null);
        WalletCreateRequestDto request2 = new WalletCreateRequestDto("");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> walletService.create(null));

        assertNotNull(exception);
        assertEquals("Request is invalid", exception.getMessage());


        exception = assertThrows(IllegalArgumentException.class, () -> walletService.create(request1));

        assertNotNull(exception);
        assertEquals("Request is invalid", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> walletService.create(request2));

        assertNotNull(exception);
        assertEquals("Request is invalid", exception.getMessage());
    }
}
