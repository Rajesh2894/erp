package com.abm.mainet.bnd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.bnd.domain.BirthRegistrationCorrection;
import com.abm.mainet.bnd.dto.BirthRegistrationDTO;

@Repository
public interface BirthRegCorrectionRepository extends JpaRepository<BirthRegistrationCorrection, Long> {

	List<BirthRegistrationDTO> findByOrgId(Long orgId);

	@Modifying
	@Query("update BirthRegistrationCorrection d set d.BirthWFStatus=:wfStatus where d.brId=:brId and d.orgId=:orgId")
	void updateWorkFlowStatus(@Param("brId")Long brId,@Param("orgId")Long orgId,@Param("wfStatus")String wfStatus);
	
	
	@Modifying
	@Query("update BirthRegistrationCorrection d set d.corrAuthRemark=:corrAuthRemark where d.brCorrId=:brCorrId and d.orgId=:orgId")
	void updateBirthCorrectionRemark(@Param("brCorrId") Long brCorrId, @Param("orgId") Long orgId,@Param("corrAuthRemark") String corrAuthRemark);
	
	
}
