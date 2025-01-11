package com.cpsoneghett.walletapi.domain.repository;

import com.cpsoneghett.walletapi.domain.entity.TokenHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface TokenHistoryRepository extends JpaRepository<TokenHistory, Long> {

    boolean existsByIdTokenAndCoinCapTimestamp(Long idToken, Timestamp coinCapTimestamp);

    List<TokenHistory> findAllByIdToken(Long idToken);

    TokenHistory findFirstByIdTokenOrderByCoinCapTimestampDesc(@Param("idToken") Long idToken);

}
