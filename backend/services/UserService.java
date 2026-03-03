package com.postdeath.services;

import com.postdeath.entities.User;
import com.postdeath.dto.UserDTO;
import com.postdeath.dto.UserRegistrationDTO;
import com.postdeath.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    public User registerUser(UserRegistrationDTO dto) {
        User user = User.builder()
                .aadharNumber(dto.getAadharNumber())
                .email(dto.getEmail())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .phoneNumber(dto.getPhoneNumber())
                .dateOfBirth(dto.getDateOfBirth())
                .address(dto.getAddress())
                .city(dto.getCity())
                .state(dto.getState())
                .postalCode(dto.getPostalCode())
                .facialBiometric(dto.getFacialBiometric())
                .status(User.UserStatus.ACTIVE)
                .digiLockerLinked(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        
        return userRepository.save(user);
    }
    
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    
    public User getUserByAadhar(String aadhar) {
        return userRepository.findByAadharNumber(aadhar)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    
    public UserDTO convertToDTO(User user) {
        return UserDTO.builder()
                .userId(user.getUserId())
                .aadharNumber(user.getAadharNumber())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .status(user.getStatus().toString())
                .digiLockerLinked(user.getDigiLockerLinked())
                .createdAt(user.getCreatedAt())
                .build();
    }
    
    public void markUserAsDeceased(Long userId, LocalDateTime dateOfDeath) {
        User user = getUserById(userId);
        user.setStatus(User.UserStatus.DECEASED);
        user.setDateOfDeath(dateOfDeath);
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }
    
    public List<UserDTO> getAllActiveUsers() {
        return userRepository.findByStatus(User.UserStatus.ACTIVE)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}