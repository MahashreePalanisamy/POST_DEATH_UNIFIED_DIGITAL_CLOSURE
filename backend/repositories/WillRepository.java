package com.postdeath.repositories;

import com.postdeath.entities.Will;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface WillRepository extends JpaRepository<Will, Long> {
    List<Will> findByDeceasedUserId(Long deceasedUserId);
    Optional<Will> findFirstByDeceasedUserIdOrderByCreatedAtDesc(Long deceasedUserId);
    List<Will> findByStatus(Will.WillStatus status);
}