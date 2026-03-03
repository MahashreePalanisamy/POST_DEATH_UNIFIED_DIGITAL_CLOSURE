package com.postdeath.entities;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "wills")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Will {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long willId;
    
    @ManyToOne
    @JoinColumn(name = "deceased_user_id", nullable = false)
    private User deceasedUser;
    
    @Column(nullable = false)
    private String willTitle;
    
    @Lob
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String willContent;
    
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] willDocument;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private WillStatus status;
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    
    private LocalDateTime executionDate;
    
    private String witnessName1;
    
    private String witnessName2;
    
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String legalNotes;
    
    @OneToMany(mappedBy = "will", cascade = CascadeType.ALL)
    private List<Beneficiary> beneficiaries;
    
    @OneToMany(mappedBy = "will", cascade = CascadeType.ALL)
    private List<AssetDistribution> assetDistributions;
    
    @Column(nullable = false)
    private String documentNLPSummary;
    
    public enum WillStatus {
        DRAFT, VALIDATED, ACTIVE, EXECUTED, DISPUTED
    }
}