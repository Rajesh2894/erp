package com.abm.mainet.sfac.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.sfac.domain.CreditGrantDetEntity;
import com.abm.mainet.sfac.domain.FPOProfileManagementMaster;

@Repository
public interface CreditGrandDetRepository extends JpaRepository<CreditGrantDetEntity, Long>{

	List<CreditGrantDetEntity> findByFpoProfileMgmtMaster(FPOProfileManagementMaster fpoProfileManagemntMaster);

	

}
