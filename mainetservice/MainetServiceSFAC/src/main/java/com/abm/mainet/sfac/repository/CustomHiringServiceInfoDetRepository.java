package com.abm.mainet.sfac.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.sfac.domain.CustomHiringServiceInfoDetailEntity;
import com.abm.mainet.sfac.domain.FPOProfileManagementMaster;

@Repository
public interface CustomHiringServiceInfoDetRepository extends JpaRepository<CustomHiringServiceInfoDetailEntity, Long>{

	List<CustomHiringServiceInfoDetailEntity> findByFpoProfileMgmtMaster(
			FPOProfileManagementMaster fpoProfileManagemntMaster);

}
