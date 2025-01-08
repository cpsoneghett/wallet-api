package com.cpsoneghett.walletapi.domain.repository;

import com.cpsoneghett.walletapi.domain.entity.GlobalAsset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlobalAssetRepository extends JpaRepository<GlobalAsset, Long> {
}
