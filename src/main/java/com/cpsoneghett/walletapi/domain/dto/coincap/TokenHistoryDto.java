package com.cpsoneghett.walletapi.domain.dto.coincap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.sql.Timestamp;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TokenHistoryDto(String priceUsd,
                              Timestamp time,
                              String circulatingSupply,
                              Date date) {


}
