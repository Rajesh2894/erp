package com.abm.mainet.sfac.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.sfac.domain.FPOProfileFarmerSummaryDetEntity;
import com.abm.mainet.sfac.domain.FPOProfileManagementMaster;

@Repository
public interface FPOProfileFarmerSummaryDetRepository  extends JpaRepository<FPOProfileFarmerSummaryDetEntity, Long>{

	List<FPOProfileFarmerSummaryDetEntity> findByFpoProfileMgmtMaster(
			FPOProfileManagementMaster fpoProfileManagemntMaster);

}
