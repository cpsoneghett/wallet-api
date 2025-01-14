package com.cpsoneghett.walletapi.domain.entity;

import com.cpsoneghett.walletapi.domain.dto.AssetDto;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "WALLET")
@EntityListeners(AuditingEntityListener.class)
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false, unique = true, length = 50)
    private String email;

    @Column(name = "public_address", nullable = false, unique = true, length = 50)
    private String publicAddress;

    @Column(name = "private_key", nullable = false, unique = true, length = 50)
    private String privateKey;

    private BigDecimal total;

    @CreatedDate
    @Column(name = "dt_created", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "dt_updated")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "wallet")
    private Set<Asset> assets;

    public Wallet() {
    }

    public Wallet(String publicAddress, String privateKey) {
        this.publicAddress = publicAddress;
        this.privateKey = privateKey;
        this.total = BigDecimal.ZERO;
        this.assets = new HashSet<>();
    }

    public Long getId() {
        return id;
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

    public void setPublicAddress(String publicAddress) {
        this.publicAddress = publicAddress;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Set<Asset> getAssets() {
        return assets;
    }

    public void addAsset(Asset asset) {
        this.assets.add(asset);
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<AssetDto> assetsToDto() {

        return assets.stream()
                .map(asset -> new AssetDto(
                        asset.getToken().getSymbol(),
                        asset.getQuantity(),
                        asset.getPrice(),
                        asset.getValue()
                ))
                .toList();
    }

}
