package com.postdeath.repositories;

import com.postdeath.entities.EstateExecution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EstateExecutionRepository extends JpaRepository<EstateExecution, Long> {
    List<EstateExecution> findByExecutorId(Long executorId);
    List<EstateExecution> findByExecutionStage(EstateExecution.ExecutionStage stage);
    List<EstateExecution> findByDeceasedAadhar(String aadhar);
}