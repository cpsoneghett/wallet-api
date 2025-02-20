package com.cpsoneghett.walletapi.domain.entity;

import com.cpsoneghett.walletapi.domain.dto.coincap.TokenDto;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "TOKEN")
@EntityListeners(AuditingEntityListener.class)
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_name")
    private String idName;

    @Column(name = "nr_rank")
    private Integer rank;

    private String symbol;

    private String name;

    private BigDecimal supply;

    @Column(name = "max_supply")
    private BigDecimal maxSupply;

    @Column(name = "market_cap_usd")
    private BigDecimal marketCapUsd;

    @Column(name = "volume_usd_24h")
    private BigDecimal volumeUsd24Hr;

    @Column(name = "price_usd")
    private BigDecimal priceUsd;

    @Column(name = "change_percent_24h")
    private BigDecimal changePercent24Hr;

    @Column(name = "vwap_24h")
    private BigDecimal vwap24Hr;

    @CreatedDate
    @Column(name = "dt_created", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "dt_updated", nullable = false)
    private LocalDateTime updatedAt;

    public Token() {
    }

    public Token(TokenDto dto) {
        this.idName = dto.id();
        this.symbol = dto.symbol();
        this.name = dto.name();

        this.rank = dto.rank() == null ? null : Integer.valueOf(dto.rank());
        this.supply = dto.supply() == null ? null : new BigDecimal(dto.supply());
        this.maxSupply = dto.maxSupply() == null ? null : new BigDecimal(dto.maxSupply());
        this.marketCapUsd = dto.marketCapUsd() == null ? null : new BigDecimal(dto.marketCapUsd());
        this.volumeUsd24Hr = dto.volumeUsd24Hr() == null ? null : new BigDecimal(dto.volumeUsd24Hr());
        this.priceUsd = dto.priceUsd() == null ? null : new BigDecimal(dto.priceUsd());
        this.changePercent24Hr = dto.changePercent24Hr() == null ? null : new BigDecimal(dto.changePercent24Hr());
        this.vwap24Hr = dto.vwap24Hr() == null ? null : new BigDecimal(dto.vwap24Hr());
    }

    public Long getId() {
        return id;
    }

    public String getIdName() {
        return idName;
    }

    public void setIdName(String idName) {
        this.idName = idName;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getSupply() {
        return supply;
    }

    public void setSupply(BigDecimal supply) {
        this.supply = supply;
    }

    public BigDecimal getMaxSupply() {
        return maxSupply;
    }

    public void setMaxSupply(BigDecimal maxSupply) {
        this.maxSupply = maxSupply;
    }

    public BigDecimal getMarketCapUsd() {
        return marketCapUsd;
    }

    public void setMarketCapUsd(BigDecimal marketCapUsd) {
        this.marketCapUsd = marketCapUsd;
    }

    public BigDecimal getVolumeUsd24Hr() {
        return volumeUsd24Hr;
    }

    public void setVolumeUsd24Hr(BigDecimal volumeUsd24Hr) {
        this.volumeUsd24Hr = volumeUsd24Hr;
    }

    public BigDecimal getPriceUsd() {
        return priceUsd;
    }

    public void setPriceUsd(BigDecimal priceUsd) {
        this.priceUsd = priceUsd;
    }

    public BigDecimal getChangePercent24Hr() {
        return changePercent24Hr;
    }

    public void setChangePercent24Hr(BigDecimal changePercent24Hr) {
        this.changePercent24Hr = changePercent24Hr;
    }

    public BigDecimal getVwap24Hr() {
        return vwap24Hr;
    }

    public void setVwap24Hr(BigDecimal vwap24Hr) {
        this.vwap24Hr = vwap24Hr;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
