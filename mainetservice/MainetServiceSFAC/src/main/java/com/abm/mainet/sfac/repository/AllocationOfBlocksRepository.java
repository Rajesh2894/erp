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

import com.abm.mainet.sfac.domain.BlockAllocationDetailEntity;
import com.abm.mainet.sfac.domain.BlockAllocationEntity;

/**
 * @author pooja.maske
 *
 */

@Repository
public interface AllocationOfBlocksRepository extends JpaRepository<BlockAllocationEntity, Long> {

	/**
	 * @param orgTypeId
	 * @param organizationNameId
	 * @param allocationYearId
	 * @return
	 */
	@Query("Select b from BlockAllocationEntity b  where b.orgTypeId=:orgTypeId  and b.organizationNameId=:organizationNameId and b.allocationYearId=:allocationYearId and b.orgId=:orgId")
	List<BlockAllocationEntity> getBlockDetailsByIds(@Param("orgTypeId") Long orgTypeId,
			@Param("organizationNameId") Long organizationNameId, @Param("allocationYearId") Long allocationYearId,
			@Param("orgId") Long orgId);

	/**
	 * @param orgNameId
	 * @param allYearId
	 * @param sdb1
	 * @param orgId
	 * @param orgId2
	 * @return
	 */
	@Query("Select b from BlockAllocationEntity b  where b.orgTypeId=:orgTypeId and  b.organizationNameId=:orgNameId and  b.allocationYearId=:allYearId  and  b.orgId=:orgId")
	List<BlockAllocationEntity> getAllBlockDetailsByStateId(@Param("orgTypeId") Long orgTypeId,
			@Param("orgNameId") Long orgNameId, @Param("allYearId") Long allYearId, @Param("orgId") Long orgId);

	/**
	 * @param orgId
	 * @return
	 */

	@Query("Select b from  BlockAllocationEntity b  where b.orgId=:orgId and b.orgTypeId=:orgTypeId")
	List<BlockAllocationEntity> fetchAllBlocks(@Param("orgId") Long orgId, @Param("orgTypeId") Long orgTypeId);

	/**
	 * @param orgId
	 * @return
	 */
	@Query("Select distinct d.sdb3 from  BlockAllocationDetailEntity d, BlockAllocationEntity b  where d.orgId=:orgId and b.blockId=d.blockMasterEntity.blockId")
	List<Long> getAllocatedBlockList(@Param("orgId") Long orgId);

	/**
	 * @param blockId
	 * @param applicationId
	 * @param status
	 */
	@Modifying
	@Query("update BlockAllocationEntity d set d.status=:status,d.applicationId=:applicationId where d.blockId=:blockId and d.orgId=:orgId")
	void updateStatusAndApplId(@Param("blockId") Long blockId, @Param("applicationId") Long applicationId,
			@Param("status") String status, @Param("orgId") Long orgId);

	/**
	 * @param applId
	 * @return
	 */
	@Query("Select b from BlockAllocationEntity b where b.applicationId=:applId")
	 BlockAllocationEntity fetchBlockDetailsbyAppId(@Param("applId") Long applId);

	/**
	 * @param applicationId
	 * @param status
	 */
	@Modifying
	@Query("update BlockAllocationEntity d set d.status=:status,d.authRemark=:authRemark where d.applicationId=:applicationId and d.blockId=:blockId")
	void updateApprovalStatusAndRemark(@Param("blockId") Long blockId, @Param("applicationId") Long applicationId,
			@Param("status") String status, @Param("authRemark") String authRemark);

	/**
	 * @return
	 */
	@Query("Select  b from BlockAllocationEntity b ")
	List<BlockAllocationEntity> findBlockDetails();

	/**
	 * @param blockId
	 * @return
	 */
	@Query("Select  b from BlockAllocationEntity b where b.blockId=:blockId")
	BlockAllocationEntity getDetailsById(@Param("blockId") Long blockId);

	/**
	 * @param masId
	 * @return
	 */
	@Query("Select case when count(f)>0 THEN true ELSE false END from BlockAllocationEntity f where f.organizationNameId=:masId")
	Boolean checkMasIdPresent(@Param("masId") Long masId);

	/**
	 * @param orgTypeId
	 * @param orgNameId
	 * @param allocationYearId
	 * @return
	 */
	@Query("Select case when count(f)>0 THEN true ELSE false END from BlockAllocationEntity f where f.orgTypeId=:orgTypeId and f.organizationNameId=:orgNameId and f.allocationYearId=:allocationYearId")
	Boolean checKDataExist(@Param("orgTypeId") Long orgTypeId, @Param("orgNameId") Long orgNameId,
			@Param("allocationYearId") Long allocationYearId);

	/**
	 * @param blockId
	 * @return
	 */
	@Query("Select count(b) from BlockAllocationDetailEntity b where b.blockMasterEntity.blockId=:blockId and b.status in('A','C')")
	Long getAllocatedBlockCount(@Param("blockId") Long blockId);

	/**
	 * @param bdId
	 * @param applicationId
	 * @param status
	 */
	@Modifying
	@Query("update BlockAllocationDetailEntity d set d.status=:status where d.applicationId=:applicationId and d.bdId=:bdId")
	void updateStatusInDetailEntity(@Param("bdId") Long bdId,@Param("applicationId") Long applicationId,@Param("status") String status);

	/**
	 * @param iaAlcYear
	 * @param masId
	 * @return
	 */
	@Query("Select d from BlockAllocationDetailEntity d, BlockAllocationEntity b where  d.blockMasterEntity.blockId=b.blockId and b.organizationNameId=:iaId and d.cbboId=:masId and b.status in ('A','C') ")
	List<BlockAllocationDetailEntity> findBlockDetByYrAndMasId(@Param("iaId") Long iaId,@Param("masId") Long masId);

}
