package com.abm.mainet.lqp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.lqp.domain.QueryRegistrationMaster;

@Repository
public interface QueryRegistrationRepository extends JpaRepository<QueryRegistrationMaster, Long> {
	
	List<QueryRegistrationMaster> findByOrgId(Long orgId);
	
	QueryRegistrationMaster findByQuestionId(String qustnId);
	
	List<QueryRegistrationMaster> findByOrgIdAndStatusIn(Long orgId,List<String> statusList);
	
	@Query(value="SELECT WFTASK_ACTORID FROM TB_WORKFLOW_TASK WHERE "
			+ "REFERENCE_ID = :referenceId AND TASK_STATUS = 'PENDING'",
			nativeQuery=true)
	String getActorIds(@Param("referenceId") String referenceId);
}
