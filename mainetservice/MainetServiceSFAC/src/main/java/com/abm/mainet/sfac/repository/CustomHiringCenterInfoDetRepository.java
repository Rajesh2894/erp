package com.abm.mainet.sfac.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.sfac.domain.CustomHiringCenterInfoDetailEntity;
import com.abm.mainet.sfac.domain.FPOProfileManagementMaster;

@Repository
public interface CustomHiringCenterInfoDetRepository extends JpaRepository<CustomHiringCenterInfoDetailEntity, Long>{

	List<CustomHiringCenterInfoDetailEntity> findByFpoProfileMgmtMaster(
			FPOProfileManagementMaster fpoProfileManagemntMaster);

}
