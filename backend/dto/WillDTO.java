package com.postdeath.dto;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WillDTO {
    private Long willId;
    private Long deceasedUserId;
    private String willTitle;
    private String status;
    private LocalDateTime createdAt;
    private String documentNLPSummary;
    private List<BeneficiaryDTO> beneficiaries;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WillCreationDTO {
    private Long deceasedUserId;
    private String willTitle;
    private String willContent;
    private byte[] willDocument;
    private String witnessName1;
    private String witnessName2;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeneficiaryDTO {
    private Long beneficiaryId;
    private String beneficiaryName;
    private String relationship;
    private Double sharePercentage;
    private String status;
    private String contingentBeneficiary;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeneficiaryRegistrationDTO {
    private Long willId;
    private String beneficiaryName;
    private String relationship;
    private Double sharePercentage;
    private String aadharNumber;
    private String email;
    private String phoneNumber;
    private String contingentBeneficiary;
}