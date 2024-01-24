/**
 * 
 */
package com.abm.mainet.rnl.service;

import com.abm.mainet.common.master.dto.ContractMastDTO;

/**
 * @author divya.marshettiwar
 *
 */
public interface ITransferOfLeaseService {

	ContractMastDTO searchContractDetails(String contNo, Long orgid);

	void updateTransferOfLeaseData(ContractMastDTO contractMasterDto, Double appreciationRate, Long vendorList);


}
