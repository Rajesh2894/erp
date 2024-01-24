/**
 * 
 */
package com.abm.mainet.sfac.dao;

import java.util.List;

import com.abm.mainet.sfac.domain.IAMasterEntity;

/**
 * @author pooja.maske
 *
 */
public interface IAMasterDao {

	/**
	 * @param iaName
	 * @param allocationYear
	 * @param orgId
	 */
	List<IAMasterEntity> getIaDetailsByIds(Long IAName, Long allocationYear, Long orgId);

}
