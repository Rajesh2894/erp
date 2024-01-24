package com.abm.mainet.workManagement.service;

import java.util.List;

import com.abm.mainet.workManagement.dto.SchemeMasterDTO;

/**
 * @author vishwajeet.kumar
 * @since 5 dec 2017
 */
public interface SchemeMasterService {

	/**
	 * this service is used for create Scheme Master
	 * 
	 * @param schemeMasterDTO
	 */
	void saveSchemeMaster(SchemeMasterDTO schemeMasterDTO);

	/**
	 * this service is used for check duplicate Scheme Code
	 * 
	 * @param wmSchCode
	 * @return
	 */
	String checkDuplicateSchemeCode(String wmSchCode, Long orgId);

	/**
	 * this service is used for get all scheme master
	 * 
	 * @param startDate
	 * @param endDate
	 * @param wmSchNameEng
	 * @param orgid
	 */

	List<SchemeMasterDTO> getSchemeMasterList(Long sourceCode, Long sourceName, String wmSchNameEng, long orgid);

	/**
	 * this service is used for get data on the basis of SchemeId
	 * 
	 * @param wmSchId
	 */

	SchemeMasterDTO getSchemeMasterBySchemeId(Long wmSchId);

	/**
	 * this service is used for scheme Soft Delete only flag set "Y" or "N"
	 * 
	 * @param wmSchId
	 */

	void deleteSchemeMasterById(Long wmSchId);

	/**
	 * this service is used for Update Scheme Master
	 * 
	 * @param schemeMaster
	 * @param removeIds
	 */

	void updateSchemeMaster(SchemeMasterDTO schemeMaster, List<Long> removeIds, List<Long> removeFileById);

	/**
	 * this service is used for get data on the basis of SchemeId association with
	 * project master
	 * 
	 * @param wmSchId
	 */

	SchemeMasterDTO getSchmMastToProject(Long wmSchId);

	/**
	 * this service is used to generate Scheme Code
	 * 
	 * @param orgId
	 * @return
	 */
	 String generateSchemeCode(Long orgId);

}
