package com.postdeath.services;

import com.postdeath.entities.Will;
import com.postdeath.dto.WillDTO;
import com.postdeath.dto.WillCreationDTO;
import com.postdeath.dto.BeneficiaryDTO;
import com.postdeath.repositories.WillRepository;
import com.postdeath.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
public class WillService {
    
    @Autowired
    private WillRepository willRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private NLPService nlpService;
    
    public Will createWill(WillCreationDTO dto) {
        String nlpSummary = nlpService.analyzeWillDocument(dto.getWillContent());
        
        Will will = Will.builder()
                .deceasedUser(userRepository.findById(dto.getDeceasedUserId())
                        .orElseThrow(() -> new RuntimeException("User not found")))
                .willTitle(dto.getWillTitle())
                .willContent(dto.getWillContent())
                .willDocument(dto.getWillDocument())
                .status(Will.WillStatus.DRAFT)
                .witnessName1(dto.getWitnessName1())
                .witnessName2(dto.getWitnessName2())
                .documentNLPSummary(nlpSummary)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        
        return willRepository.save(will);
    }
    
    public Will getLatestWill(Long userId) {
        return willRepository.findFirstByDeceasedUserIdOrderByCreatedAtDesc(userId)
                .orElseThrow(() -> new RuntimeException("No will found for user"));
    }
    
    public void validateWill(Long willId) {
        Will will = willRepository.findById(willId)
                .orElseThrow(() -> new RuntimeException("Will not found"));
        
        will.setStatus(Will.WillStatus.VALIDATED);
        will.setUpdatedAt(LocalDateTime.now());
        
        willRepository.save(will);
    }
    
    public void executeWill(Long willId) {
        Will will = willRepository.findById(willId)
                .orElseThrow(() -> new RuntimeException("Will not found"));
        
        will.setStatus(Will.WillStatus.EXECUTED);
        will.setExecutionDate(LocalDateTime.now());
        will.setUpdatedAt(LocalDateTime.now());
        
        willRepository.save(will);
    }
    
    public WillDTO convertToDTO(Will will) {
        return WillDTO.builder()
                .willId(will.getWillId())
                .deceasedUserId(will.getDeceasedUser().getUserId())
                .willTitle(will.getWillTitle())
                .status(will.getStatus().toString())
                .createdAt(will.getCreatedAt())
                .documentNLPSummary(will.getDocumentNLPSummary())
                .beneficiaries(will.getBeneficiaries().stream()
                        .map(b -> BeneficiaryDTO.builder()
                                .beneficiaryId(b.getBeneficiaryId())
                                .beneficiaryName(b.getBeneficiaryName())
                                .relationship(b.getRelationship())
                                .sharePercentage(b.getSharePercentage())
                                .status(b.getStatus().toString())
                                .contingentBeneficiary(b.getContingentBeneficiary())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}