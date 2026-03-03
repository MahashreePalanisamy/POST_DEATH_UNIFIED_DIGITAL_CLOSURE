package com.postdeath.repositories;

import com.postdeath.entities.Beneficiary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Long> {
    List<Beneficiary> findByWillId(Long willId);
    List<Beneficiary> findByAadharNumber(String aadharNumber);
    List<Beneficiary> findByStatus(Beneficiary.BeneficiaryStatus status);
}