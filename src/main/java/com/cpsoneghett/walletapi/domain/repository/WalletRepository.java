package com.cpsoneghett.walletapi.domain.repository;

import com.cpsoneghett.walletapi.domain.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Optional<Wallet> findByPrivateKey(String privateKey);
}
