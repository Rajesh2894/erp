package com.abm.mainet.brms.rest.account.service;

import org.springframework.http.ResponseEntity;

import com.abm.mainet.rule.account.datamodel.TDSCalculation;

/**
 * 
 * @author Vivek.Kumar
 * @since  14/04/2017
 */
public interface TDSCalculationService {

	
	ResponseEntity<?> validate(TDSCalculation tds);
	
	ResponseEntity<?> calculateTdsRate(TDSCalculation factModel);
}
