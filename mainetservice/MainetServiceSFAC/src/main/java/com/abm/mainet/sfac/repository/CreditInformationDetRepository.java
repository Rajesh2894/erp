package com.abm.mainet.sfac.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.sfac.domain.CreditInformationDetEntity;
import com.abm.mainet.sfac.domain.FPOProfileManagementMaster;

@Repository
public interface CreditInformationDetRepository extends JpaRepository<CreditInformationDetEntity, Long> {

	List<CreditInformationDetEntity> findByFpoProfileMgmtMaster(FPOProfileManagementMaster fpoProfileManagemntMaster);

}
