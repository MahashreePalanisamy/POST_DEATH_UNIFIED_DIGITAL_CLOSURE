package com.postdeath.entities;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    
    @Column(nullable = false, unique = true)
    private String aadharNumber;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    private String firstName;
    
    @Column(nullable = false)
    private String lastName;
    
    @Column(nullable = false)
    private String phoneNumber;
    
    @Column(nullable = false)
    private String dateOfBirth;
    
    @Column(nullable = false)
    private String address;
    
    @Column(nullable = false)
    private String city;
    
    @Column(nullable = false)
    private String state;
    
    @Column(nullable = false)
    private String postalCode;
    
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] facialBiometric;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus status;
    
    @Column(nullable = false)
    private Boolean digiLockerLinked;
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    
    private LocalDateTime dateOfDeath;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<DigitalAsset> digitalAssets;
    
    @OneToMany(mappedBy = "deceasedUser", cascade = CascadeType.ALL)
    private List<Will> wills;
    
    @OneToMany(mappedBy = "executor", cascade = CascadeType.ALL)
    private List<EstateExecution> estateExecutions;
    
    @OneToMany(mappedBy = "heir", cascade = CascadeType.ALL)
    private List<HeirMapping> heirMappings;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<AuditLog> auditLogs;
    
    public enum UserStatus {
        ACTIVE, INACTIVE, DECEASED, SUSPENDED
    }
}