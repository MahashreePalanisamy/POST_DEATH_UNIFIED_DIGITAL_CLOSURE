package com.postdeath.entities;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "asset_distributions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssetDistribution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long distributionId;
    
    @ManyToOne
    @JoinColumn(name = "will_id", nullable = false)
    private Will will;
    
    @OneToOne
    @JoinColumn(name = "digital_asset_id")
    private DigitalAsset digitalAsset;
    
    @Column(nullable = false)
    private String assetName;
    
    @Column(nullable = false)
    private String assetType;
    
    @Column(nullable = false)
    private Double assetValue;
    
    @Column(nullable = false)
    private Double distributionPercentage;
    
    @Column(nullable = false)
    private String allocatedBeneficiary;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DistributionStatus status;
    
    private LocalDateTime distributedDate;
    
    private LocalDateTime createdAt;
    
    public enum DistributionStatus {
        PENDING, ALLOCATED, DISTRIBUTED, DISPUTED, REJECTED
    }
}