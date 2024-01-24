package com.abm.mainet.workManagement.dao;

import java.util.Date;
import java.util.List;

import com.abm.mainet.workManagement.domain.SchemeMaster;

public interface SchemeMasterDao {

	/**
	 * save and Update scheme master details in database
	 *
	 */
	SchemeMaster saveSchemeMaster(SchemeMaster masterEntity);

	SchemeMaster getSchemeMasterBySchemeId(Long wmSchId);

	/**
	 * get all scheme master list on condition start date to end date and name
	 * 
	 * @param startDate
	 * @param endDate
	 * @param wmSchNameEng
	 * @param orgId
	 */
	List<SchemeMaster> getSchemeMasterList(Long sourceCode, Long sourceName, String wmSchNameEng, long orgId);

	/**
	 * check for active duplicate scheme code
	 * 
	 * @param wmSchCode
	 */
	String checkDuplicateSchemeCode(String wmSchCode, Long orgId);

	void inactiveSchemeMasterChildRecords(Long updatedBy, List<Long> removeChildIds);

}
