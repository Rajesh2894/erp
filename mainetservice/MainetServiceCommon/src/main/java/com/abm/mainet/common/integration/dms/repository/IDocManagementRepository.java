package com.abm.mainet.common.integration.dms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.integration.dms.domain.DocManagementEntity;

@Repository
public interface IDocManagementRepository extends JpaRepository<DocManagementEntity, Long> {

	@Procedure("PR_RM_MAIN")
	void updateRecord(Long orgId);
}
