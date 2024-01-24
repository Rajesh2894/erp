package com.abm.mainet.sfac.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.sfac.domain.DPREntryMasterEntity;

@Repository
public interface DPREntryMasterRepository extends JpaRepository<DPREntryMasterEntity, Long>{

	List<DPREntryMasterEntity> findByFpoIdAndIaId(Long fpoID, Long iaId);

	List<DPREntryMasterEntity> findByFpoId(Long fpoID);

	List<DPREntryMasterEntity> findByIaId(Long iaId);

	DPREntryMasterEntity findByApplicationNumber(Long appNumber);

}
