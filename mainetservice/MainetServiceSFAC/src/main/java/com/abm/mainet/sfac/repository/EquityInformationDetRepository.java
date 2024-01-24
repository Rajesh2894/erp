package com.abm.mainet.sfac.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.sfac.domain.EquityInformationDetEntity;
import com.abm.mainet.sfac.domain.FPOProfileManagementMaster;

@Repository
public interface EquityInformationDetRepository extends JpaRepository<EquityInformationDetEntity, Long> {

	List<EquityInformationDetEntity> findByFpoProfileMgmtMaster(FPOProfileManagementMaster fpoProfileManagemntMaster);

}
