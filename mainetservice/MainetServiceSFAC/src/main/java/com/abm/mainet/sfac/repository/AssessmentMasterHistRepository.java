/**
 * 
 */
package com.abm.mainet.sfac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.sfac.domain.AssessmentMasterHist;

/**
 * @author pooja.maske
 *
 */
@Repository
public interface AssessmentMasterHistRepository extends JpaRepository<AssessmentMasterHist, Long>{

	/**
	 * @param assId
	 * @param assStatus
	 * @param remark
	 * @param flagu
	 */
	@Modifying
	@Query("Update AssessmentMasterHist a set a.assStatus=:assStatus, a.remark=:remark,a.historyStatus=:flagu where a.assId=:assId ")
	void updateApprovalStatusAndRemark(@Param("assId") Long assId, @Param("assStatus") String assStatus, @Param("remark") String remark,@Param("flagu") String flagu);

}
