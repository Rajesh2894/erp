package com.abm.mainet.common.master.service;

import java.util.List;

import com.abm.mainet.common.integration.acccount.dto.TaxDefinationDto;

/**
 * 
 * @author Jeetendra.Pal
 *
 */
public interface ITaxDefinationService {

	/**
	 * used to save Update Tax Defination
	 * 
	 * @param taxDefinationList
	 * @param removeFileById
	 * @return
	 */
	void saveUpdateTaxDefinationList(List<TaxDefinationDto> taxDefinationList, List<Long> removeTaxId);

	/**
	 * used to get Tax Defination Dto
	 * 
	 * @param orgId
	 * @return
	 */
	List<TaxDefinationDto> getTaxDefinationList(Long orgId);

	/**
	 * used to get Tax Defination Dto By Id
	 * 
	 * @param taxDefId
	 * @return TaxDefinationDto
	 */
	TaxDefinationDto getTaxDefinitionById(Long taxDefId);

	/**
	 * used to get Tax Definitions By Id and Pan applicable or not
	 * 
	 * @param taxId
	 * @param panApp
	 * @param orgId
	 * @return List<TaxDefinationDto>
	 */
	List<TaxDefinationDto> findByAllGridSearchData(Long taxId, String panApp, Long orgId);

}
