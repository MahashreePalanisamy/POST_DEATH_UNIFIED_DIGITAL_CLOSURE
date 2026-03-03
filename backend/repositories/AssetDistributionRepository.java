package com.postdeath.repositories;

import com.postdeath.entities.AssetDistribution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AssetDistributionRepository extends JpaRepository<AssetDistribution, Long> {
    List<AssetDistribution> findByWillId(Long willId);
    List<AssetDistribution> findByStatus(AssetDistribution.DistributionStatus status);
    List<AssetDistribution> findByAllocatedBeneficiary(String beneficiary);
}