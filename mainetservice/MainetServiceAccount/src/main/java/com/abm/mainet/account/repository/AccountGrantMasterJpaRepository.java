package com.abm.mainet.account.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.account.domain.AccountGrantMasterEntity;

public interface AccountGrantMasterJpaRepository extends JpaRepository<AccountGrantMasterEntity, Long> {
	AccountGrantMasterEntity findByOrgId(Long orgId);
	List<AccountGrantMasterEntity> findByGrntIdAndOrgId(Long grntId,Long orgId); 
	
	@Query("SELECT AGM FROM AccountGrantMasterEntity AGM WHERE AGM.grntId=:grantId AND AGM.orgId=:orgId")
	AccountGrantMasterEntity findByGrantIdAndOrgId(@Param("grantId") Long grntId,@Param("orgId") Long orgId);
	
}
