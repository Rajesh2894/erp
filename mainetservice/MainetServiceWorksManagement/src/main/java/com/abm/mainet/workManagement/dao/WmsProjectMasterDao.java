package com.abm.mainet.workManagement.dao;

import java.util.List;

import com.abm.mainet.workManagement.domain.TbWmsProjectMaster;

public interface WmsProjectMasterDao {

	/**
	 * method used for get Project Master List
	 * 
	 * @param startDate
	 * @param endDate
	 * @param projectName
	 * @param projCode
	 * @param orgId
	 * @return List<TbWmsProjectMaster>
	 */
	List<TbWmsProjectMaster> getProjectMasterList(Long sourceCode, Long sourceName, String projectName, String projCode,
			long orgId, Long dpDeptId, Long projStatus);

	/**
	 * method used for get All Project Association By MileStone
	 * 
	 * @param orgId
	 * @param mileStoneFlag
	 * @return list of object
	 */
	List<Object[]> getAllProjectAssociationByMileStone(Long orgId, String mileStoneFlag);

}
