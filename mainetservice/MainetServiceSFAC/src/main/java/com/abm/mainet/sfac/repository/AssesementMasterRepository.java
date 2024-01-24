/**
 * 
 */
package com.abm.mainet.sfac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.sfac.domain.AssessmentMasterEntity;

/**
 * @author pooja.maske
 *
 */
@Repository
public interface AssesementMasterRepository extends JpaRepository<AssessmentMasterEntity, Long>{

	/**
	 * @param assId
	 * @param flag
	 */
	@Modifying
	@Query("Update AssessmentMasterEntity a set a.assStatus=:flag where a.assId=:assId ")
	void updateAssementStatus(@Param("assId") Long assId, @Param("flag") String flag);

	/**
	 * @param applcationId
	 * @return
	 */
	@Query("Select a from AssessmentMasterEntity a  where a.applicationId=:applcationId")
	AssessmentMasterEntity fetchAssessmentDetByAppId(@Param("applcationId") Long applcationId);

	/**
	 * @param assId
	 * @param assStatus
	 * @param remark
	 */
	@Modifying
	@Query("Update AssessmentMasterEntity a set a.assStatus=:assStatus, a.remark=:remark where a.assId=:assId ")
	void updateApprovalStatusAndRemark(@Param("assId") Long assId,@Param("assStatus") String assStatus,@Param("remark") String remark);

	/**
	 * @param assId
	 * @return
	 */
	@Query("Select a from AssessmentMasterEntity a  where a.assId=:assId")
	AssessmentMasterEntity fetchAssessmentDetByAssId(@Param("assId")  Long assId);

}
