package com.abm.mainet.sfac.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.sfac.domain.EquityGrantMasterEntity;

@Repository
public interface EquityGrantMasterRepository extends JpaRepository<EquityGrantMasterEntity, Long>{

	List<EquityGrantMasterEntity> findByFpoName(Long fpoId);

	List<EquityGrantMasterEntity> findByFpoNameAndAppStatus(Long fpoId, String status);

	@Modifying
	@Query("update EquityGrantMasterEntity d set d.appStatus=:appStatus where d.egId=:egId")
	void updateApprovalStatusAndRemark(@Param("egId") Long egId,
			@Param("appStatus") String status);
	

	EquityGrantMasterEntity findByAppNumber(Long valueOf);

}
