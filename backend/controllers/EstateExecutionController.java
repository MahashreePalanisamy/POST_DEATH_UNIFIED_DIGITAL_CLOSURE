package com.postdeath.controllers;

import com.postdeath.dto.EstateExecutionDTO;
import com.postdeath.dto.EstateExecutionInitiationDTO;
import com.postdeath.dto.EstateStatusDTO;
import com.postdeath.entities.EstateExecution;
import com.postdeath.services.EstateExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/estate")
@CrossOrigin(origins = "*")
public class EstateExecutionController {
    
    @Autowired
    private EstateExecutionService executionService;
    
    @PostMapping("/initiate")
    public ResponseEntity<EstateExecutionDTO> initiateEstateExecution(
            @RequestBody EstateExecutionInitiationDTO dto) {
        EstateExecution execution = executionService.initiateEstateExecution(dto);
        
        EstateExecutionDTO responseDTO = EstateExecutionDTO.builder()
                .executionId(execution.getExecutionId())
                .executorId(execution.getExecutor().getUserId())
                .deceasedName(execution.getDeceasedName())
                .executionStage(execution.getExecutionStage().toString())
                .deathNotificationDate(execution.getDeathNotificationDate())
                .createdAt(execution.getCreatedAt())
                .build();
        
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }
    
    @GetMapping("/{executionId}/status")
    public ResponseEntity<EstateStatusDTO> getExecutionStatus(@PathVariable Long executionId) {
        EstateStatusDTO status = executionService.getExecutionStatus(executionId);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }
    
    @PutMapping("/{executionId}/stage/{stage}")
    public ResponseEntity<String> updateExecutionStage(
            @PathVariable Long executionId,
            @PathVariable String stage) {
        executionService.updateExecutionStage(executionId, EstateExecution.ExecutionStage.valueOf(stage));
        return new ResponseEntity<>("Execution stage updated", HttpStatus.OK);
    }
}