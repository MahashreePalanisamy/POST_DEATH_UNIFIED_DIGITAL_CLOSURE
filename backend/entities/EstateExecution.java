package com.postdeath.entities;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "estate_executions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstateExecution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long executionId;
    
    @ManyToOne
    @JoinColumn(name = "executor_id", nullable = false)
    private User executor;
    
    @Column(nullable = false)
    private String deceasedName;
    
    @Column(nullable = false)
    private String deceasedAadhar;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ExecutionStage executionStage;
    
    @Column(nullable = false)
    private LocalDateTime deathNotificationDate;
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    
    private LocalDateTime completedAt;
    
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String executionNotes;
    
    @OneToMany(mappedBy = "estateExecution", cascade = CascadeType.ALL)
    private List<InstitutionNotification> institutionNotifications;
    
    public enum ExecutionStage {
        INITIATED, VERIFICATION_PENDING, IDENTITY_VERIFIED, ASSET_LISTED,
        HEIR_VALIDATION, DISTRIBUTION_IN_PROGRESS, COMPLETED, DISPUTED
    }
}