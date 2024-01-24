/**
 * 
 */
package com.abm.mainet.sfac.service;

import java.util.List;

import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.sfac.dto.BlockAllocationDto;
import com.abm.mainet.sfac.ui.model.AllocationOfBlocksModel;
import com.abm.mainet.sfac.ui.model.ChangeofBlockApprovalModel;
import com.abm.mainet.sfac.ui.model.ChangeofBlockModel;

/**
 * @author pooja.maske
 *
 */
public interface AllocationOfBlocksService {

	/**
	 * @param blockAllocationDtoList
	 * @param allocationOfBlocksModel
	 */
/*	BlockAllocationDto saveBlockDetails(List<BlockAllocationDto> blockAllocationDtoList,
			AllocationOfBlocksModel allocationOfBlocksModel);*/

	/**
	 * @param orgTypeId
	 * @param organizationNameId
	 * @param allocationYearId
	 * @param orgId
	 * @return 
	 */
	List<BlockAllocationDto> getBlockDetailsByIds(Long orgTypeId, Long organizationNameId, Long allocationYearId, Long orgId,Long sdb1,Long sdb2,Long sdb3);

	/**
	 * @param sdb1
	 * @param orgId
	 * @param orgId2 
	 * @param sdb12 
	 * @return
	 */
	List<BlockAllocationDto> getAllBlockDetailsByStateId(Long orgTypeId ,Long orgNameId, Long allYearId, Long orgId);

	

	/**
	 * @param masDto
	 * @return
	 */

	
	/**
	 * @param orgId
	 * @return
	 */
	List<BlockAllocationDto> getCBBOBlockListByOrgId(Long orgId,Long orgTypeId);

	/**
	 * @param orgId
	 * @return
	 */
	List<LookUp> getNotAllocatedBlockList(Long distId,Long orgId);

	/**
	 * @param masDto
	 * @param blockAllocationDtoList
	 * @return
	 */
	BlockAllocationDto saveChangedBlockDetails(BlockAllocationDto masDto, BlockAllocationDto newlyAllocatedBlock ,ChangeofBlockModel model);

	/**
	 * @param valueOf
	 * @return
	 */
	BlockAllocationDto fetchBlockDetailsbyAppId(Long applId);

	/**
	 * @param masDto
	 * @param allocatedBlockDtoList
	 * @param changeofBlockApprovalModel
	 * @return
	 */
   void updateApprovalStatusAndRemark(BlockAllocationDto masDto,BlockAllocationDto oldMasDto,
			ChangeofBlockApprovalModel changeofBlockApprovalModel);

	/**
	 * @param enclosureRemoveById
	 * @param empId
	 */
	void deleteContractDocFileById(List<Long> enclosureRemoveById, Long empId);

	/**
	 * @param mastDto
	 * @param allocationOfBlocksModel
	 * @return 
	 */
	BlockAllocationDto saveBlockDetails(BlockAllocationDto mastDto, AllocationOfBlocksModel allocationOfBlocksModel);

	/**
	 * @param blockId
	 * @return
	 */
	BlockAllocationDto getDetailById(Long blockId,Long cbboId);

	/**
	 * @param orgid
	 * @return
	 */
	List<BlockAllocationDto> getAllBlockDetailSummary(Long orgId,Long masId);

	/**
	 * @param masId
	 * @return
	 */
	boolean checkMasIdPresent(Long masId);

	/**
	 * @param orgTypeId
	 * @param orgNameId
	 * @param allocationYearId
	 * @return
	 */
	boolean checKDataExist(Long orgTypeId, Long orgNameId, Long allocationYearId);

	
}
