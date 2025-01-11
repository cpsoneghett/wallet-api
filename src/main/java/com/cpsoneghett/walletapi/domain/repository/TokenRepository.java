package com.cpsoneghett.walletapi.domain.repository;

import com.cpsoneghett.walletapi.domain.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    boolean existsByIdNameOrName(String idName, String name);

    @Query("SELECT t FROM Token t WHERE t.idName = :searchParam OR t.symbol = :searchParam OR t.name = :searchParam")
    Optional<Token> findBySearchParam(@Param("searchParam") String searchParam);
}
