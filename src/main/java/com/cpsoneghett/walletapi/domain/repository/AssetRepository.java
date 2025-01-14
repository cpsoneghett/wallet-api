package com.cpsoneghett.walletapi.domain.repository;

import com.cpsoneghett.walletapi.domain.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {

    @Query("SELECT a FROM Asset a WHERE a.token.id = :idToken AND a.wallet.id = :idWallet")
    Optional<Asset> findByWalletAndToken(@Param("idWallet") Long idWallet, @Param("idToken") Long idToken);
}
