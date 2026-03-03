package com.postdeath.entities;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "digital_assets")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DigitalAsset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long assetId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String assetName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AssetType assetType;

    @Column(nullable = false)
    private String assetValue;

    @Column(nullable = false)
    private String accountNumber;

    @Column(nullable = false)
    private String institutionName;

    private String institutionCode;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AssetStatus status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    private LocalDateTime lastVerifiedAt;

    @Column(nullable = false)
    private Boolean verified;

    private String encryptedCredentials;

    @OneToOne(mappedBy = "digitalAsset")
    private AssetDistribution assetDistribution;

    public enum AssetType {
        BANK_ACCOUNT, INSURANCE_POLICY, MUTUAL_FUND, CRYPTOCURRENCY,
        DIGITAL_PROPERTY, SOCIAL_MEDIA, EMAIL_ACCOUNT, INVESTMENT,
        LOAN, VEHICLE, PROPERTY, OTHER
    }

    public enum AssetStatus {
        REGISTERED, VERIFIED, NOTIFIED, CLAIMED, TRANSFERRED, CLOSED
    }
}