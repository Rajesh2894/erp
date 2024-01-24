package com.abm.mainet.socialsecurity.service;

import java.util.List;

import com.abm.mainet.socialsecurity.domain.ConfigurationMasterEntity;
import com.abm.mainet.socialsecurity.ui.dto.ConfigurationMasterDto;

/**
 * @author rahul.chaubey
 * @since 11 Jan 2020
 */

public interface ConfigurationMasterService {

	/**
	 * This method will save form content in the database
	 * 
	 * @param ConfigurationMasterDto
	 */
	void saveConfigurationMaster(ConfigurationMasterDto masterDto);

	/**
	 * This method will update form content in the database
	 * 
	 * @param ConfigurationMasterDto
	 */
	void updateConfigurationMaster(ConfigurationMasterDto masterDto);

	/**
	 * This method will find the configured scheme in the database
	 * 
	 * @param orgId
	 * @param schemeId
	 */
	ConfigurationMasterDto findSchemeById(Long configurationId, Long orgId);

	/**
	 * This method will find the configured scheme in the database
	 * searchData
	 * @param orgId
	 * @param schemeId
	 */
	List<ConfigurationMasterDto> loadData(Long orgId);
	
	List<ConfigurationMasterDto> searchData(Long schemeMstId, Long orgId);
	
	
	List<ConfigurationMasterDto> getData(Long configurationId,Long schemeMstId, Long orgId);
	
	List<Object []> unconfiguredList(Long orgId, Long depId, final Long activeStatusId,
			final String notActualFlag);
	
	
	boolean validateOnSave(Long schemeMstId, Long orgId, boolean workflowFlag);

	ConfigurationMasterDto getConfigMstDataBySchemeId(Long schemeMstId, Long orgId);
}
