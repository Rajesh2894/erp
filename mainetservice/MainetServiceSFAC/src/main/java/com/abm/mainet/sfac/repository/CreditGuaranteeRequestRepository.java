package com.abm.mainet.sfac.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.sfac.domain.CreditGuaranteeCGFMasterEntity;
import com.abm.mainet.sfac.domain.EquityGrantMasterEntity;

@Repository
public interface CreditGuaranteeRequestRepository extends JpaRepository<CreditGuaranteeCGFMasterEntity, Long>{

	List<CreditGuaranteeCGFMasterEntity> findByFpoIdAndAppStatus(Long fpoId, String status);

	List<CreditGuaranteeCGFMasterEntity> findByFpoId(Long fpoId);

	CreditGuaranteeCGFMasterEntity findByApplicationNumber(Long valueOf);

}
