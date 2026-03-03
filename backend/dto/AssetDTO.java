package com.postdeath.dto;

import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DigitalAssetDTO {
    private Long assetId;
    private Long userId;
    private String assetName;
    private String assetType;
    private String assetValue;
    private String institutionName;
    private String status;
    private Boolean verified;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DigitalAssetRegistrationDTO {
    private Long userId;
    private String assetName;
    private String assetType;
    private String assetValue;
    private String accountNumber;
    private String institutionName;
    private String description;
    private String encryptedCredentials;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssetListingDTO {
    private Long userId;
    private Integer totalAssets;
    private Double totalValue;
    private List<DigitalAssetDTO> assets;
}