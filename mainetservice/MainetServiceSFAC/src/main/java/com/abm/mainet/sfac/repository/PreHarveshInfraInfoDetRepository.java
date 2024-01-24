package com.abm.mainet.sfac.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.sfac.domain.FPOProfileManagementMaster;
import com.abm.mainet.sfac.domain.PreHarveshInfraInfoDetailEntity;

@Repository
public interface PreHarveshInfraInfoDetRepository extends JpaRepository<PreHarveshInfraInfoDetailEntity, Long>{

	List<PreHarveshInfraInfoDetailEntity> findByFpoProfileMgmtMaster(
			FPOProfileManagementMaster fpoProfileManagemntMaster);

}
