package com.cpsoneghett.walletapi.domain.entity;

import com.cpsoneghett.walletapi.domain.dto.AssetDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "GLOBAL_ASSET")
public class GlobalAsset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_name")
    private String idName;
    private Long rank;
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

    public GlobalAsset() {
    }

    public GlobalAsset(AssetDto dto) {
        this.name = dto.id();
        this.symbol = dto.symbol();
        this.name = dto.name();

        this.rank = Long.getLong(dto.rank());
        this.supply = new BigDecimal(dto.supply());
        this.maxSupply = new BigDecimal(dto.maxSupply());
        this.marketCapUsd = new BigDecimal(dto.marketCapUsd());
        this.volumeUsd24Hr = new BigDecimal(dto.volumeUsd24Hr());
        this.priceUsd = new BigDecimal(dto.priceUsd());
        this.changePercent24Hr = new BigDecimal(dto.changePercent24Hr());
        this.vwap24Hr = new BigDecimal(dto.vwap24Hr());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdName() {
        return idName;
    }

    public void setIdName(String idName) {
        this.idName = idName;
    }

    public Long getRank() {
        return rank;
    }

    public void setRank(Long rank) {
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
}
