package com.cpsoneghett.walletapi.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "WALLET")
@EntityListeners(AuditingEntityListener.class)
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @Column(name = "public_address")
    private String publicAddress;

    @Column(name = "private_key")
    private String privateKey;

    private BigDecimal total;

    @CreatedDate
    @Column(name = "dt_created", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "dt_updated")
    private LocalDateTime updatedAt;

    public Wallet() {
    }

    public Wallet(String publicAddress, String privateKey) {
        this.publicAddress = publicAddress;
        this.privateKey = privateKey;
        this.total = BigDecimal.ZERO;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPublicAddress() {
        return publicAddress;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
