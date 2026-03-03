package com.postdeath.entities;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "heir_mappings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HeirMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long heirMappingId;
    
    @ManyToOne
    @JoinColumn(name = "heir_id", nullable = false)
    private User heir;
    
    @ManyToOne
    @JoinColumn(name = "deceased_user_id")
    private User deceasedUser;
    
    @Column(nullable = false)
    private String relationship;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private VerificationStatus verificationStatus;
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    private LocalDateTime verifiedAt;
    
    private String verifiedBy;
    
    public enum VerificationStatus {
        PENDING, VERIFIED, REJECTED, EXPIRED
    }
}