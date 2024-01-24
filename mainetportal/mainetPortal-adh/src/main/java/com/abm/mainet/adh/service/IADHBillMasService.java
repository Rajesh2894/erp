package com.abm.mainet.adh.service;

import com.abm.mainet.common.dto.ContractAgreementSummaryDTO;

public interface IADHBillMasService {

	ContractAgreementSummaryDTO findByContractNo(Long orgid, String contractNo);

	ContractAgreementSummaryDTO updateBillPayment(ContractAgreementSummaryDTO contractAgreementSummaryDTO);

}
