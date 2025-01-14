package com.cpsoneghett.walletapi.domain.repository;

import com.cpsoneghett.walletapi.domain.entity.AssetHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetHistoryRepository extends JpaRepository<AssetHistory, Long> {
}
