package com.cpsoneghett.walletapi.domain.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "ASSET")
@EntityListeners(AuditingEntityListener.class)
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_wallet")
    private Wallet wallet;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_token", referencedColumnName = "id")
    private Token token;

    private BigDecimal quantity;

    private BigDecimal price;

    @Column(name = "`value`")
    private BigDecimal value;

    @CreatedDate
    @Column(name = "dt_created", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "dt_updated")
    private LocalDateTime updatedAt;

    public Asset() {

    }

    public Asset(Token token, Wallet wallet, BigDecimal quantity, BigDecimal price, BigDecimal value) {

    }

    public Long getId() {
        return id;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
