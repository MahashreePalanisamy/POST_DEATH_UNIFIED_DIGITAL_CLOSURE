package com.postdeath.repositories;

import com.postdeath.entities.InstitutionNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface InstitutionNotificationRepository extends JpaRepository<InstitutionNotification, Long> {
    List<InstitutionNotification> findByEstateExecutionId(Long executionId);
    List<InstitutionNotification> findByInstitutionType(String type);
    List<InstitutionNotification> findByNotificationStatus(InstitutionNotification.NotificationStatus status);
}