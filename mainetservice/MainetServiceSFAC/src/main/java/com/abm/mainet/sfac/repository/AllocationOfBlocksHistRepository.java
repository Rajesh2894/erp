/**
 * 
 */
package com.abm.mainet.sfac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.sfac.domain.BlockAllocationHist;

/**
 * @author pooja.maske
 *
 */
@Repository
public interface AllocationOfBlocksHistRepository extends JpaRepository<BlockAllocationHist, Long>{

	/**
	 * @param blockId
	 * @param applicationId
	 * @param status
	 */
	@Modifying
	@Query("update BlockAllocationHist d set d.status=:status,d.applicationId=:applicationId where d.blockId=:blockId and d.orgId=:orgId")
	void updateStatusAndApplId(@Param("blockId") Long blockId, @Param("applicationId")  Long applicationId,@Param("status") String status,@Param("orgId") Long orgId);

	/**
	 * @param blockId
	 * @param applicationId
	 * @param status
	 * @param authRemark
	 */
	@Modifying
	@Query("update BlockAllocationHist d set d.status=:status,d.authRemark=:authRemark where d.applicationId=:applicationId and d.blockId=:blockId")
	void updateApprovalStatusAndRemHist(@Param("blockId") Long blockId,@Param("applicationId") Long applicationId,@Param("status") String status,@Param("authRemark") String authRemark);

	/**
	 * @param bdId
	 * @param applicationId
	 * @param status
	 */
	@Modifying
	@Query("update BlockAllocationDetailHist d set d.status=:status where d.applicationId=:applicationId and d.bdId=:bdId")
	void updateStatusInDetailHistEntity(@Param("bdId") Long bdId,@Param("applicationId") Long applicationId,@Param("status") String status);

}
