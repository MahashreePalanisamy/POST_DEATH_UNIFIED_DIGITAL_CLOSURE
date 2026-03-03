package com.postdeath.repositories;

import com.postdeath.entities.DigitalAsset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DigitalAssetRepository extends JpaRepository<DigitalAsset, Long> {
    List<DigitalAsset> findByUserId(Long userId);
    List<DigitalAsset> findByAssetType(DigitalAsset.AssetType assetType);
    List<DigitalAsset> findByInstitutionName(String institutionName);
    List<DigitalAsset> findByStatus(DigitalAsset.AssetStatus status);
}