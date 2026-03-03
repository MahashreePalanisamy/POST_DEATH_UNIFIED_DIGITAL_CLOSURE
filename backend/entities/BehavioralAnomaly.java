package com.postdeath.entities;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "behavioral_anomalies")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BehavioralAnomaly {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long anomalyId;
    
    @Column(nullable = false)
    private String subjectAadhar;
    
    @Column(nullable = false)
    private String anomalyType;
    
    @Column(nullable = false)
    private Double riskScore;
    
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String anomalyDetails;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AnomalyStatus status;
    
    @Column(nullable = false)
    private LocalDateTime detectedAt;
    
    private LocalDateTime resolvedAt;
    
    private String resolutionNotes;
    
    public enum AnomalyStatus {
        DETECTED, FLAGGED, INVESTIGATING, RESOLVED, FALSE_POSITIVE
    }
}