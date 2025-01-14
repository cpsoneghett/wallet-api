package com.cpsoneghett.walletapi.domain.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ASSET_HISTORY")
@EntityListeners(AuditingEntityListener.class)
public class AssetHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_asset")
    private Long idAsset;

    private BigDecimal quantity;

    private BigDecimal price;

    @CreatedDate
    @Column(name = "dt_created", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public AssetHistory() {

    }

    public AssetHistory(Long idAsset, BigDecimal quantity, BigDecimal price) {
        this.idAsset = idAsset;
        this.quantity = quantity;
        this.price = price;
    }


}
