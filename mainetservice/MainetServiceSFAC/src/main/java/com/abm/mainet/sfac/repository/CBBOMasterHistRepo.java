/**
 * 
 */
package com.abm.mainet.sfac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.sfac.domain.CBBOMasterHistory;

/**
 * @author pooja.maske
 *
 */
@Repository
public interface CBBOMasterHistRepo extends JpaRepository<CBBOMasterHistory, Long>{

	/**
	 * @param cbboId
	 * @param appStatus
	 * @param remark
	 */
	@Modifying
	@Query("Update CBBOMasterHistory h set  h.appStatus=:appStatus , h.remark=:remark where h.cbboId=:cbboId")
	void updateApprovalStatusAndRemark(@Param("cbboId") Long cbboId,@Param("appStatus") String appStatus,@Param("remark") String remark);

}
