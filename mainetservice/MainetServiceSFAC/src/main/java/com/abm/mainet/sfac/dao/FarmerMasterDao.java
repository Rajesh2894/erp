/**
 * 
 */
package com.abm.mainet.sfac.dao;

import java.util.List;

import com.abm.mainet.sfac.domain.FarmerMasterEntity;

/**
 * @author pooja.maske
 *
 */
public interface FarmerMasterDao {

	/**
	 * @param frmId
	 * @param frmFPORegNo
	 * @return
	 */
	List<FarmerMasterEntity> getFarmerDetailsByIds(Long frmId, String frmFPORegNo);

}
