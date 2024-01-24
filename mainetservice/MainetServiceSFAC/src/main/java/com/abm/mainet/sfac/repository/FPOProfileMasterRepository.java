package com.abm.mainet.sfac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.sfac.domain.FPOMasterEntity;
import com.abm.mainet.sfac.domain.FPOProfileManagementMaster;

@Repository
public interface FPOProfileMasterRepository extends JpaRepository<FPOProfileManagementMaster, Long>{
	
 FPOProfileManagementMaster	findByFpoMasterEntity(FPOMasterEntity entity);

}
