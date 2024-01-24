/**
 * 
 */
package com.abm.mainet.workManagement.service;

import java.util.List;



import com.abm.mainet.workManagement.dto.MeasurementBookTaxDetailsDto;

/**
 * @author Saiprasad.Vengurlekar
 *
 */
public interface MeasurementBookTaxDetailsService {

	/**
	 * use to save ,update and delete measurement book Tax Details
	 * 
	 * @param MeasurementBookTaxDetailsDto
	 * @param MbTaxDetIds
	 */
	void saveUpdateMbTaxDetails(List<MeasurementBookTaxDetailsDto > mbTaxDto,List<Long> removeMbTaxDetIds);
	
	/**
	 * use to get measurement book Tax Details
	 * 
	 * @param MeasurementBookTaxDetailsDto
	 */
	List<MeasurementBookTaxDetailsDto > getMbTaxDetails(Long mbId,Long orgId);
}
