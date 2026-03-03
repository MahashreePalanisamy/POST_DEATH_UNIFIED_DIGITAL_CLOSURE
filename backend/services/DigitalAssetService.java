package com.postdeath.services;

import com.postdeath.entities.DigitalAsset;
import com.postdeath.dto.DigitalAssetDTO;
import com.postdeath.dto.DigitalAssetRegistrationDTO;
import com.postdeath.repositories.DigitalAssetRepository;
import com.postdeath.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DigitalAssetService {
    
    @Autowired
    private DigitalAssetRepository assetRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public DigitalAsset registerAsset(DigitalAssetRegistrationDTO dto) {
        DigitalAsset asset = DigitalAsset.builder()
                .user(userRepository.findById(dto.getUserId())
                        .orElseThrow(() -> new RuntimeException("User not found")))
                .assetName(dto.getAssetName())
                .assetType(DigitalAsset.AssetType.valueOf(dto.getAssetType()))
                .assetValue(dto.getAssetValue())
                .accountNumber(dto.getAccountNumber())
                .institutionName(dto.getInstitutionName())
                .description(dto.getDescription())
                .status(DigitalAsset.AssetStatus.REGISTERED)
                .verified(false)
                .encryptedCredentials(dto.getEncryptedCredentials())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        
        return assetRepository.save(asset);
    }
    
    public List<DigitalAsset> getAssetsByUserId(Long userId) {
        return assetRepository.findByUserId(userId);
    }
    
    public void verifyAsset(Long assetId) {
        DigitalAsset asset = assetRepository.findById(assetId)
                .orElseThrow(() -> new RuntimeException("Asset not found"));
        
        asset.setVerified(true);
        asset.setStatus(DigitalAsset.AssetStatus.VERIFIED);
        asset.setLastVerifiedAt(LocalDateTime.now());
        asset.setUpdatedAt(LocalDateTime.now());
        
        assetRepository.save(asset);
    }
    
    public DigitalAssetDTO convertToDTO(DigitalAsset asset) {
        return DigitalAssetDTO.builder()
                .assetId(asset.getAssetId())
                .userId(asset.getUser().getUserId())
                .assetName(asset.getAssetName())
                .assetType(asset.getAssetType().toString())
                .assetValue(asset.getAssetValue())
                .institutionName(asset.getInstitutionName())
                .status(asset.getStatus().toString())
                .verified(asset.getVerified())
                .build();
    }
    
    public Double getTotalAssetValue(Long userId) {
        List<DigitalAsset> assets = getAssetsByUserId(userId);
        return assets.stream()
                .mapToDouble(a -> {
                    try {
                        return Double.parseDouble(a.getAssetValue());
                    } catch (NumberFormatException e) {
                        return 0;
                    }
                })
                .sum();
    }
}