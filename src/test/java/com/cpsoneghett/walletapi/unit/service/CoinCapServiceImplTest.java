package com.cpsoneghett.walletapi.unit.service;

import com.cpsoneghett.walletapi.domain.dto.coincap.*;
import com.cpsoneghett.walletapi.domain.service.GlobalParameterService;
import com.cpsoneghett.walletapi.domain.service.impl.CoinCapServiceImpl;
import com.cpsoneghett.walletapi.exception.CoinCapApiException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CoinCapServiceImplTest {

    private static final String COIN_CAP_BASE_URL = "https://api.coincap.io/v2/";
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private GlobalParameterService globalParameterService;
    @InjectMocks
    private CoinCapServiceImpl coinCapService;

    private String assetName;
    private String period;
    private String interval;

    @BeforeEach
    void setUp() {
        this.assetName = "bitcoin";
        this.period = "m";
        this.interval = "1";

    }

    @Test
    void testGetAllAssets() {
        TokenListResponseDto mockResponse = new TokenListResponseDto(
                List.of(new TokenDto("1", "1", "BTC", "Bitcoin", "21000000", "21000000", "1000000000", "100000", "40000", "5", "1000")),
                new Timestamp(System.currentTimeMillis())
        );
        when(restTemplate.getForObject(eq(COIN_CAP_BASE_URL + "assets"), eq(TokenListResponseDto.class)))
                .thenReturn(mockResponse);
        when(globalParameterService.getValueByKey("COIN_CAP_BASE_URL")).thenReturn(COIN_CAP_BASE_URL);

        TokenListResponseDto response = coinCapService.getAllAssets();

        assertNotNull(response);
        assertEquals(1, response.data().size());
        assertEquals("BTC", response.data().get(0).symbol());
        verify(restTemplate, times(1)).getForObject(eq(COIN_CAP_BASE_URL + "assets"), eq(TokenListResponseDto.class));
    }

    @Test
    void testGetAssetById() {
        String assetId = "1";
        TokenSingleResponseDto mockResponse = new TokenSingleResponseDto(
                new TokenDto("1", "1", "BTC", "Bitcoin", "21000000", "21000000", "1000000000", "100000", "40000", "5", "1000"),
                new Timestamp(System.currentTimeMillis())
        );
        when(restTemplate.getForObject(eq(COIN_CAP_BASE_URL + "assets/" + assetId), eq(TokenSingleResponseDto.class)))
                .thenReturn(mockResponse);
        when(globalParameterService.getValueByKey("COIN_CAP_BASE_URL")).thenReturn(COIN_CAP_BASE_URL);

        TokenSingleResponseDto response = coinCapService.getAssetById(assetId);

        assertNotNull(response);
        assertEquals("BTC", response.data().symbol());
        verify(restTemplate, times(1)).getForObject(eq(COIN_CAP_BASE_URL + "assets/" + assetId), eq(TokenSingleResponseDto.class));
    }

    @Test
    void testGetTokenHistoryWithParams() {
        TokenHistoryResponseDto mockResponse = new TokenHistoryResponseDto(
                List.of(new TokenHistoryDto("40000", new Timestamp(System.currentTimeMillis()), "21000000", new Date())),
                new Timestamp(System.currentTimeMillis())
        );

        when(restTemplate.getForObject(eq(COIN_CAP_BASE_URL + "assets/" + assetName + "/history?interval=m1"), eq(TokenHistoryResponseDto.class)))
                .thenReturn(mockResponse);

        TokenHistoryResponseDto response = coinCapService.getTokenHistory(assetName, interval, period, COIN_CAP_BASE_URL);

        assertNotNull(response);
        assertEquals(1, response.data().size());
        verify(restTemplate, times(1)).getForObject(eq(COIN_CAP_BASE_URL + "assets/" + assetName + "/history?interval=m1"), eq(TokenHistoryResponseDto.class));
    }

    @Test
    void testGetTokenHistoryWithDefaults() {
        TokenHistoryResponseDto mockResponse = new TokenHistoryResponseDto(
                List.of(new TokenHistoryDto("40000", new Timestamp(System.currentTimeMillis()), "21000000", new Date())),
                new Timestamp(System.currentTimeMillis())
        );

        when(restTemplate.getForObject(eq(COIN_CAP_BASE_URL + "assets/" + assetName + "/history?interval=m1"), eq(TokenHistoryResponseDto.class)))
                .thenReturn(mockResponse);
        when(globalParameterService.getValueByKey(eq("COIN_CAP_ASSET_TIME_INTERVAL"))).thenReturn(interval);
        when(globalParameterService.getValueByKey(eq("COIN_CAP_ASSET_PERIOD"))).thenReturn(period);

        TokenHistoryResponseDto response = coinCapService.getTokenHistory(assetName, null, null, COIN_CAP_BASE_URL);

        assertNotNull(response);
        assertEquals(1, response.data().size());
        verify(restTemplate, times(1)).getForObject(eq(COIN_CAP_BASE_URL + "assets/" + assetName + "/history?interval=m1"), eq(TokenHistoryResponseDto.class));
    }

    @Test
    void testGetTokenHistoryThrowsExceptionWhenRestClientFails() {
        // Arrange
        String assetName = "bitcoin";

        when(restTemplate.getForObject(eq(COIN_CAP_BASE_URL + "assets/" + assetName + "/history?interval=m1"), eq(TokenHistoryResponseDto.class)))
                .thenThrow(new RestClientException("API request failed"));

        CoinCapApiException exception = assertThrows(CoinCapApiException.class, () -> coinCapService.getTokenHistory(assetName, interval, period, COIN_CAP_BASE_URL));

        assertEquals("Failed to fetch token history", exception.getMessage());
    }

    @Test
    void testGetAllAssetsThrowsExceptionWhenRestClientFails() {

        when(coinCapService.buildUrl(null, COIN_CAP_BASE_URL)).thenReturn(COIN_CAP_BASE_URL);
        when(restTemplate.getForObject(eq(COIN_CAP_BASE_URL + "assets"), eq(TokenListResponseDto.class)))
                .thenThrow(new RestClientException("API request failed"));

        CoinCapApiException exception = assertThrows(CoinCapApiException.class, () -> coinCapService.getAllAssets());
        assertEquals("Failed to fetch all assets", exception.getMessage());
    }
}