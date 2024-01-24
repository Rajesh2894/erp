package com.abm.mainet.rnl.service;

import java.util.List;

import com.abm.mainet.rnl.dto.ContractAgreementSummaryDTO;

public interface IRLBILLMasterService {
	
	List<ContractAgreementSummaryDTO> fetchSummaryData(Long orgId);

	ContractAgreementSummaryDTO fetchSearchData(String contNo, String propertyContractNo, Long orgId);

	ContractAgreementSummaryDTO updateBillPayment(ContractAgreementSummaryDTO dto);

}
