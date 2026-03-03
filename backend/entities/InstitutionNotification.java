package com.postdeath.entities;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "institution_notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InstitutionNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;
    
    @ManyToOne
    @JoinColumn(name = "estate_execution_id", nullable = false)
    private EstateExecution estateExecution;
    
    @Column(nullable = false)
    private String institutionName;
    
    @Column(nullable = false)
    private String institutionType;
    
    @Column(nullable = false)
    private String accountNumber;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private NotificationStatus notificationStatus;
    
    @Column(nullable = false)
    private LocalDateTime sentAt;
    
    private LocalDateTime acknowledgedAt;
    
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String acknowledgmentDetails;
    
    public enum NotificationStatus {
        PENDING, SENT, ACKNOWLEDGED, PROCESSING, COMPLETED, FAILED
    }
}