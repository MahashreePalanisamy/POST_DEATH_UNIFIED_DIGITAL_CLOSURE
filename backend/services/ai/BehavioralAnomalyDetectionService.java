package com.postdeath.services.ai;

import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class BehavioralAnomalyDetectionService {
    
    public Double calculateAnomalyScore(String aadhar, Map<String, Object> behavior) {
        double suspiciousActivities = countSuspiciousActivities(behavior);
        double frequencyDeviations = calculateFrequencyDeviation(behavior);
        double geographicAnomalies = detectGeographicAnomalies(behavior);
        
        return (suspiciousActivities * 0.5) + (frequencyDeviations * 0.3) + (geographicAnomalies * 0.2);
    }
    
    private double countSuspiciousActivities(Map<String, Object> behavior) {
        // Analyze for suspicious patterns
        return behavior.containsKey("multipleFailedLogins") ? 0.3 : 0.0;
    }
    
    private double calculateFrequencyDeviation(Map<String, Object> behavior) {
        // Detect unusual access patterns
        return behavior.containsKey("unusualAccessTime") ? 0.2 : 0.0;
    }
    
    private double detectGeographicAnomalies(Map<String, Object> behavior) {
        // Check for impossible locations
        return behavior.containsKey("rapidLocationChange") ? 0.15 : 0.0;
    }
}