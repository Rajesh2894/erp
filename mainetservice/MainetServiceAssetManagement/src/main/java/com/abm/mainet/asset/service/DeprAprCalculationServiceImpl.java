package com.abm.mainet.asset.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.abm.mainet.asset.ui.dto.DeprAprFactorsDTO;
import com.abm.mainet.asset.ui.dto.DeprAprScheduleDTO;

/**
 * @author sarojkumar.yadav
 *
 */

@Service
public class DeprAprCalculationServiceImpl implements IDeprAprCalculationService {
	
	private static final Logger LOGGER = Logger.getLogger(DeprAprCalculationServiceImpl.class);

	/**
	 * Calculate asset depreciation by Straight Line Method for complete year
	 * 
	 * @param factorsDTO
	 */
	@Override
	public DeprAprScheduleDTO calculateForYear(final DeprAprFactorsDTO factorsDTO) {
		DeprAprScheduleDTO scheduleDTO = new DeprAprScheduleDTO();
		scheduleDTO.setValueAtStart(factorsDTO.getInitialCost());
		BigDecimal depreciation = null;
		/*if (factorsDTO.getRate() != null) {
			scheduleDTO.setDeprRate(factorsDTO.getRate());
			depreciation = (factorsDTO.getInitialCost().multiply(factorsDTO.getRate())).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
		} else {*/
			scheduleDTO.setDeprRate(null);
			if (factorsDTO.getSalvageValue() == null) {
				factorsDTO.setSalvageValue(BigDecimal.ZERO);
			}
			depreciation = (factorsDTO.getInitialCost().subtract(factorsDTO.getSalvageValue()))
					.divide(factorsDTO.getLife(), 2, RoundingMode.HALF_UP);
		//}
		scheduleDTO.setDeprExpense(depreciation);
		LOGGER.info("Depreciation factor DTO after calculating depreciation for an asset for a year" + scheduleDTO);
		return scheduleDTO;
	}

	/**
	 * Calculate asset depreciation by Straight Line Method for a given period of
	 * month
	 * 
	 * @param factorsDTO
	 * @param months
	 */
	@Override
	public DeprAprScheduleDTO calculateForPeriod(final DeprAprFactorsDTO factorsDTO, final Integer months) {
		DeprAprScheduleDTO scheduleDTO = new DeprAprScheduleDTO();
		scheduleDTO.setValueAtStart(factorsDTO.getInitialCost());
		BigDecimal depreciation = null;
		if (months.intValue() == 12) {
			scheduleDTO = calculateForYear(factorsDTO);
		} else {
			/*if (factorsDTO.getRate() != null) {
				scheduleDTO.setDeprRate(factorsDTO.getRate());
				depreciation = (factorsDTO.getInitialCost().multiply(factorsDTO.getRate())).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
			} else {*/
				scheduleDTO.setDeprRate(null);
				if (factorsDTO.getSalvageValue() == null) {
					factorsDTO.setSalvageValue(BigDecimal.ZERO);
				}
				depreciation = (factorsDTO.getInitialCost().subtract(factorsDTO.getSalvageValue()))
						.divide(factorsDTO.getLife(), 2, RoundingMode.HALF_UP);
			//}
			depreciation = (depreciation.multiply(new BigDecimal(months))).divide(new BigDecimal(12), 2, RoundingMode.HALF_UP);
			scheduleDTO.setDeprExpense(depreciation);
			LOGGER.info("Depreciation factor DTO after calculating depreciation for an asset for a period of time" + scheduleDTO);
		}
		return scheduleDTO;
	}

}
