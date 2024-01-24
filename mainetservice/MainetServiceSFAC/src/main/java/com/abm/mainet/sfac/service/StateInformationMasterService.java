/**
 * 
 */
package com.abm.mainet.sfac.service;

import java.util.List;

import com.abm.mainet.sfac.dto.StateInformationDto;

/**
 * @author pooja.maske
 *
 */
public interface StateInformationMasterService {

	/**
	 * @param mastDto
	 * @return
	 */
	StateInformationDto saveAndUpdateApplication(StateInformationDto mastDto);

	/**
	 * @param state
	 * @param district
	 * @param orgid
	 */
	List<StateInformationDto> getStateInfoDetailsByIds(Long state, Long district, Long orgId);

	/**
	 * @param stId
	 * @return
	 */
	StateInformationDto findById(Long stId);

	/**
	 * @param sdb2
	 * @param orgid
	 * @return
	 */
	StateInformationDto getStateInfoByDistId(Long sdb2, Long orgId);

	/**
	 * @param district
	 * @return
	 */
	boolean checkSpecialCateExist(Long district);

}
