package com.abm.mainet.adh.service;

import java.util.List;

import com.abm.mainet.adh.domain.ADHBillMasEntity;
import com.abm.mainet.common.master.dto.ContractAgreementSummaryDTO;

/**
 * @author cherupelli.srikanth
 * @since 11 November 2019
 */
public interface IADHBillMasService {

    void saveADHBillMas(List<ADHBillMasEntity> billMasEntityList);

    void updateRLBillMas(ADHBillMasEntity adhBillMas);

    public List<ADHBillMasEntity> finByContractId(final Long contId, final Long orgId, String paidFlag, String bmType);

    public ContractAgreementSummaryDTO getReceiptAmountDetailsForBillPayment(Long contId,
	    ContractAgreementSummaryDTO contractAgreementSummaryDTO, Long orgId, List<ADHBillMasEntity> adhBillMasList);
}
