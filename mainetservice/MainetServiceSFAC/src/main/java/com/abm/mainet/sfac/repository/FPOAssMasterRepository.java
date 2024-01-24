/**
 * 
 */
package com.abm.mainet.sfac.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.sfac.domain.FPOAssessmentMasterEntity;

/**
 * @author pooja.maske
 *
 */
@Repository
public interface FPOAssMasterRepository extends JpaRepository<FPOAssessmentMasterEntity, Long>{

	/**
	 * @param applicationId
	 * @return
	 */
	FPOAssessmentMasterEntity findByApplicationId(Long applicationId);

	/**
	 * @param assId
	 * @param assStatus
	 * @param remark
	 */
	@Modifying
	@Query("Update FPOAssessmentMasterEntity f set f.assStatus=:assStatus, f.remark=:remark where f.assId=:assId")
	void updateApprovalStatusAndRemark(@Param("assId") Long assId,@Param("assStatus") String assStatus,@Param("remark") String remark);

	/**
	 * @param fpoId
	 * @param assStatus
	 * @return
	 */
	List<FPOAssessmentMasterEntity> findByFpoIdAndAssStatus(Long fpoId, String assStatus);


	/**
	 * @param assId
	 * @return
	 */
	FPOAssessmentMasterEntity findByAssId(Long assId);

}
