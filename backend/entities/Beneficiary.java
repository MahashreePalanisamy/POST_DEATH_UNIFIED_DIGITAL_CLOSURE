package com.postdeath.entities;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "beneficiaries")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Beneficiary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long beneficiaryId;
    
    @ManyToOne
    @JoinColumn(name = "will_id", nullable = false)
    private Will will;
    
    @Column(nullable = false)
    private String beneficiaryName;
    
    @Column(nullable = false)
    private String relationship;
    
    @Column(nullable = false)
    private Double sharePercentage;
    
    private String aadharNumber;
    
    private String email;
    
    private String phoneNumber;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BeneficiaryStatus status;
    
    private String contingentBeneficiary;
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    public enum BeneficiaryStatus {
        PENDING, VERIFIED, CLAIMED, REJECTED, DECEASED
    }
}