package com.cpsoneghett.walletapi.domain.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "TOKEN_HISTORY")
@EntityListeners(AuditingEntityListener.class)
public class TokenHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_token")
    private Long idToken;

    private BigDecimal price;

    @CreatedDate
    @Column(name = "dt_created", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "DT_COINCAP_TIMESTAMP")
    private Timestamp coinCapTimestamp;

    public TokenHistory() {
    }

    public TokenHistory(Long idToken, BigDecimal price, Timestamp coinCapTimestamp) {
        this.idToken = idToken;
        this.price = price;
        this.coinCapTimestamp = coinCapTimestamp;
    }

    public Timestamp getCoinCapTimestamp() {
        return coinCapTimestamp;
    }
}
