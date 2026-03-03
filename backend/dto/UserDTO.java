package com.postdeath.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long userId;
    private String aadharNumber;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String status;
    private Boolean digiLockerLinked;
    private LocalDateTime createdAt;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegistrationDTO {
    private String aadharNumber;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String dateOfBirth;
    private String address;
    private String city;
    private String state;
    private String postalCode;
    private byte[] facialBiometric;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class UserDeathNotificationDTO {
    private Long userId;
    private String aadharNumber;
    private LocalDateTime dateOfDeath;
    private String deathCertificateNumber;
    private String executorAadhar;
}