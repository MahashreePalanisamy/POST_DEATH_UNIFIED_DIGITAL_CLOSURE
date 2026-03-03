package com.postdeath.controllers;

import com.postdeath.dto.DigitalAssetDTO;
import com.postdeath.dto.DigitalAssetRegistrationDTO;
import com.postdeath.dto.AssetListingDTO;
import com.postdeath.entities.DigitalAsset;
import com.postdeath.services.DigitalAssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/assets")
@CrossOrigin(origins = "*")
public class DigitalAssetController {
    
    @Autowired
    private DigitalAssetService assetService;
    
    @PostMapping("/register")
    public ResponseEntity<DigitalAssetDTO> registerAsset(@RequestBody DigitalAssetRegistrationDTO dto) {
        DigitalAsset asset = assetService.registerAsset(dto);
        return new ResponseEntity<>(assetService.convertToDTO(asset), HttpStatus.CREATED);
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<AssetListingDTO> getAssetsByUserId(@PathVariable Long userId) {
        List<DigitalAsset> assets = assetService.getAssetsByUserId(userId);
        Double totalValue = assetService.getTotalAssetValue(userId);
        
        AssetListingDTO listing = AssetListingDTO.builder()
                .userId(userId)
                .totalAssets(assets.size())
                .totalValue(totalValue)
                .assets(assets.stream().map(assetService::convertToDTO).collect(Collectors.toList()))
                .build();
        
        return new ResponseEntity<>(listing, HttpStatus.OK);
    }
    
    @PostMapping("/{assetId}/verify")
    public ResponseEntity<String> verifyAsset(@PathVariable Long assetId) {
        assetService.verifyAsset(assetId);
        return new ResponseEntity<>("Asset verified successfully", HttpStatus.OK);
    }
}