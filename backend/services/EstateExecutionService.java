package com.postdeath.services;

import com.postdeath.entities.*;
import com.postdeath.dto.EstateExecutionDTO;
import com.postdeath.dto.EstateExecutionInitiationDTO;
import com.postdeath.dto.EstateStatusDTO;
import com.postdeath.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EstateExecutionService {
    
    @Autowired
    private EstateExecutionRepository executionRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private DigitalAssetRepository assetRepository;
    
    @Autowired
    private WillRepository willRepository;
    
    public EstateExecution initiateEstateExecution(EstateExecutionInitiationDTO dto) {
        EstateExecution execution = EstateExecution.builder()
                .executor(userRepository.findById(dto.getExecutorId())
                        .orElseThrow(() -> new RuntimeException("Executor not found")))
                .deceasedName(dto.getDeceasedName())
                .deceasedAadhar(dto.getDeceasedAadhar())
                .deathNotificationDate(dto.getDeathNotificationDate())
                .executionStage(EstateExecution.ExecutionStage.INITIATED)
                .executionNotes(dto.getExecutionNotes())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        
        return executionRepository.save(execution);
    }
    
    public void updateExecutionStage(Long executionId, EstateExecution.ExecutionStage stage) {
        EstateExecution execution = executionRepository.findById(executionId)
                .orElseThrow(() -> new RuntimeException("Execution not found"));
        
        execution.setExecutionStage(stage);
        execution.setUpdatedAt(LocalDateTime.now());
        
        executionRepository.save(execution);
    }
    
    public EstateStatusDTO getExecutionStatus(Long executionId) {
        EstateExecution execution = executionRepository.findById(executionId)
                .orElseThrow(() -> new RuntimeException("Execution not found"));
        
        User deceasedUser = userRepository.findByAadharNumber(execution.getDeceasedAadhar())
                .orElseThrow(() -> new RuntimeException("Deceased user not found"));
        
        List<DigitalAsset> assets = assetRepository.findByUserId(deceasedUser.getUserId());
        int verifiedAssets = (int) assets.stream()
                .filter(DigitalAsset::getVerified)
                .count();
        
        Will will = willRepository.findFirstByDeceasedUserIdOrderByCreatedAtDesc(deceasedUser.getUserId())
                .orElse(null);
        
        int totalHeirs = will != null ? will.getBeneficiaries().size() : 0;
        int verifiedHeirs = will != null ? 
                (int) will.getBeneficiaries().stream()
                        .filter(b -> b.getStatus() == Beneficiary.BeneficiaryStatus.VERIFIED)
                        .count() : 0;
        
        double progressPercentage = calculateProgress(execution.getExecutionStage());
        
        return EstateStatusDTO.builder()
                .executionId(executionId)
                .executionStage(execution.getExecutionStage().toString())
                .verifiedAssets(verifiedAssets)
                .totalAssets(assets.size())
                .verifiedHeirs(verifiedHeirs)
                .totalHeirs(totalHeirs)
                .progressPercentage(progressPercentage)
                .build();
    }
    
    private double calculateProgress(EstateExecution.ExecutionStage stage) {
        switch (stage) {
            case INITIATED: return 10.0;
            case VERIFICATION_PENDING: return 20.0;
            case IDENTITY_VERIFIED: return 35.0;
            case ASSET_LISTED: return 50.0;
            case HEIR_VALIDATION: return 70.0;
            case DISTRIBUTION_IN_PROGRESS: return 85.0;
            case COMPLETED: return 100.0;
            default: return 0.0;
        }
    }
}