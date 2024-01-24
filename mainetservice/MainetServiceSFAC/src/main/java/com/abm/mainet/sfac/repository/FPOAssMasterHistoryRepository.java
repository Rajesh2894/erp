/**
 * 
 */
package com.abm.mainet.sfac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.sfac.domain.FPOAssessmentMasterHistory;

/**
 * @author pooja.maske
 *
 */
@Repository
public interface FPOAssMasterHistoryRepository extends JpaRepository<FPOAssessmentMasterHistory, Long> {

	/**
	 * @param assId
	 * @param assStatus
	 * @param remark
	 * @param flagu
	 */
	@Modifying
	@Query("Update FPOAssessmentMasterHistory f set f.assStatus=:assStatus, f.remark=:remark, f.historyStatus=:flagu where f.assId=:assId")
	void updateApprovalStatusAndRemark(@Param("assId") Long assId,@Param("assStatus") String assStatus,@Param("remark") String remark, @Param("flagu") String flagu);

}
