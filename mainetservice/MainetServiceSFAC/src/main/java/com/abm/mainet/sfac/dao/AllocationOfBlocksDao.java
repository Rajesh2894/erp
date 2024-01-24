/**
 * 
 */
package com.abm.mainet.sfac.dao;

import java.util.List;

import com.abm.mainet.sfac.domain.BlockAllocationEntity;

/**
 * @author pooja.maske
 *
 */
public interface AllocationOfBlocksDao {

	/**
	 * @param orgTypeId
	 * @param organizationNameId
	 * @param allocationYearId
	 * @param orgId
	 * @return
	 */
	List<BlockAllocationEntity> getBlockDetailsByIds(Long orgTypeId, Long organizationNameId, Long allocationYearId,Long sdb1,Long sdb2,Long sdb3);

	/**
	 * @param orgId
	 * @param masId
	 * @return
	 */
	List<BlockAllocationEntity> findBlockDetails(Long orgId, Long masId);

	/**
	 * @param orgId
	 * @param masId
	 * @return
	 */
	List<BlockAllocationEntity> findBlockDetailsForCbbo(Long orgId, Long masId);
	
	

}
