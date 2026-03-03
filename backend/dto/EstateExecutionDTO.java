package com.postdeath.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstateExecutionDTO {
    private Long executionId;
    private Long executorId;
    private String deceasedName;
    private String executionStage;
    private LocalDateTime deathNotificationDate;
    private LocalDateTime createdAt;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstateExecutionInitiationDTO {
    private Long executorId;
    private String deceasedName;
    private String deceasedAadhar;
    private LocalDateTime deathNotificationDate;
    private String executionNotes;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstateStatusDTO {
    private Long executionId;
    private String executionStage;
    private Integer verifiedAssets;
    private Integer totalAssets;
    private Integer verifiedHeirs;
    private Integer totalHeirs;
    private Double progressPercentage;
}