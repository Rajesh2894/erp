/**
 * 
 */
package com.abm.mainet.sfac.dao;

import java.util.List;

import com.abm.mainet.sfac.domain.StateInformationEntity;

/**
 * @author pooja.maske
 *
 */
public interface StateInformationDao {

	/**
	 * @param state
	 * @param district
	 * @param orgId
	 * @return
	 */
	List<StateInformationEntity> getStateInfoDetailsByIds(Long state, Long district, Long orgId);

}
