/**
 * 
 */
package com.abm.mainet.sfac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.sfac.domain.FPOMasterHistory;

/**
 * @author pooja.maske
 *
 */
@Repository
public interface FPOMasterHistoryRepository extends JpaRepository<FPOMasterHistory, Long>{

	/**
	 * @param fpoId
	 * @param appStatus
	 * @param remark
	 */
	@Modifying
	@Query("Update FPOMasterHistory h set  h.appStatus=:appStatus , h.remark=:remark where h.fpoId=:fpoId")
	void updateApprovalStatusAndRemark(@Param("fpoId") Long fpoId,@Param("appStatus") String appStatus,@Param("remark") String remark);

}
