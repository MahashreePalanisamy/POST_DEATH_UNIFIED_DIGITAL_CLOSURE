package com.postdeath.controllers;

import com.postdeath.dto.WillDTO;
import com.postdeath.dto.WillCreationDTO;
import com.postdeath.entities.Will;
import com.postdeath.services.WillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wills")
@CrossOrigin(origins = "*")
public class WillController {
    
    @Autowired
    private WillService willService;
    
    @PostMapping("/create")
    public ResponseEntity<WillDTO> createWill(@RequestBody WillCreationDTO dto) {
        Will will = willService.createWill(dto);
        return new ResponseEntity<>(willService.convertToDTO(will), HttpStatus.CREATED);
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<WillDTO> getLatestWill(@PathVariable Long userId) {
        Will will = willService.getLatestWill(userId);
        return new ResponseEntity<>(willService.convertToDTO(will), HttpStatus.OK);
    }
    
    @PostMapping("/{willId}/validate")
    public ResponseEntity<String> validateWill(@PathVariable Long willId) {
        willService.validateWill(willId);
        return new ResponseEntity<>("Will validated successfully", HttpStatus.OK);
    }
    
    @PostMapping("/{willId}/execute")
    public ResponseEntity<String> executeWill(@PathVariable Long willId) {
        willService.executeWill(willId);
        return new ResponseEntity<>("Will executed successfully", HttpStatus.OK);
    }
}