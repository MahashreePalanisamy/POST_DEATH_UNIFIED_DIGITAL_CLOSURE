import com.postdeath.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HeirMappingRepository extends JpaRepository<HeirMapping, Long> {
    List<HeirMapping> findByHeirId(Long heirId);
    List<HeirMapping> findByDeceasedUserId(Long deceasedUserId);
}

@Repository
public interface BehavioralAnomalyRepository extends JpaRepository<BehavioralAnomaly, Long> {
    List<BehavioralAnomaly> findBySubjectAadhar(String aadhar);
    List<BehavioralAnomaly> findByStatus(BehavioralAnomaly.AnomalyStatus status);
}

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    List<AuditLog> findByUserId(Long userId);
    List<AuditLog> findByEntityType(String entityType);
}