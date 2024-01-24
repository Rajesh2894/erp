package com.abm.mainet.bnd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.bnd.domain.TbBdDeathregCorr;

@Repository
public interface DeathRegCorrectionRepository extends JpaRepository<TbBdDeathregCorr, Long>{
	
	List<TbBdDeathregCorr> findByorgId(Long orgId);

	@Modifying
	@Query("update TbBdDeathregCorr d set d.DeathWFStatus=:wfStatus where d.drId=:drID and d.orgId=:orgId")
	void updateWorkFlowStatus(@Param("drID")Long drID,@Param("orgId")Long orgId,@Param("wfStatus")String wfStatus);
	
	@Modifying
	@Query("update TbBdDeathregCorr d set d.corrAuthRemark=:corrAuthRemark where d.drCorrId=:drCorrId and d.orgId=:orgId")
	void updateDeathCorrectionRemark(@Param("drCorrId") Long drCorrId, @Param("orgId") Long orgId,@Param("corrAuthRemark") String corrAuthRemark);
	

}
