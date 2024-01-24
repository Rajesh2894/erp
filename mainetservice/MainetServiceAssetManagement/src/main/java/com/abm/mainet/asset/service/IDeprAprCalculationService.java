/**
 * 
 */
package com.abm.mainet.asset.service;

import com.abm.mainet.asset.ui.dto.DeprAprFactorsDTO;
import com.abm.mainet.asset.ui.dto.DeprAprScheduleDTO;

public interface IDeprAprCalculationService {
	DeprAprScheduleDTO calculateForYear(DeprAprFactorsDTO factorsDTO);
	DeprAprScheduleDTO calculateForPeriod(DeprAprFactorsDTO factorsDTO, Integer months);
}
