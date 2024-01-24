package com.abm.mainet.buildingplan.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.buildingplan.domain.ProfessionalRegistrationEntity;

public interface ProfessionalRegistrationRepo extends CrudRepository<ProfessionalRegistrationEntity, Long> {

	@Query("select pr from ProfessionalRegistrationEntity pr where pr.applicationId=:applicationId and pr.orgId=:orgId")
	ProfessionalRegistrationEntity getDetailByAppIdAndOrgId(@Param("applicationId") Long applicationId,
			@Param("orgId") Long orgId);

	@Modifying
	@Query("update TbCfcApplicationMstEntity am set am.apmAppRejFlag =:approvalStatus ,am.apmApprovedBy =:approvedBy where am.apmApplicationId =:applicationId")
	void updateAgencyApprovalWorkflow(@Param("approvalStatus") String approvalStatus,
			@Param("approvedBy") Long approvedBy, @Param("applicationId") Long applicationId);
	
	@Modifying
	@Transactional
	@Query("delete from CFCAttachment cfc where cfc.applicationId =:applicationId and cfc.serviceId=:serviceId and cfc.orgid=:orgId")
	void deleteDocument(@Param("applicationId")Long applicationId,@Param("serviceId") Long serviceId ,@Param("orgId") Long orgId);


}
