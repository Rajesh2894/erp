/**
 * 
 */
package com.abm.mainet.water.service;

import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.water.ui.model.DuplicateBillReceiptModel;

/**
 * @author akshata.bhat
 *
 */
public interface DuplicateBillPrintingService {

	public void saveData(DuplicateBillReceiptModel duplicatePaymentReceiptModel, CommonChallanDTO offlineDTO);
	
}
