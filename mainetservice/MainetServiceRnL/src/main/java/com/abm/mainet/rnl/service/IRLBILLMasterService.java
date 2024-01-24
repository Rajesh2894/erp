package com.abm.mainet.rnl.service;

import java.util.Date;
import java.util.List;

import com.abm.mainet.common.domain.ContractInstalmentDetailEntity;
import com.abm.mainet.common.master.dto.ContractAgreementSummaryDTO;
import com.abm.mainet.common.master.dto.ContractInstalmentDetailDTO;
import com.abm.mainet.rnl.domain.RLBillMaster;

public interface IRLBILLMasterService {

    List<RLBillMaster> finByContractId(Long contId, Long orgId, String paidFlag, String bmType);

    void save(List<RLBillMaster> billMasters);

    void updateRLBillMas(RLBillMaster rlBillMaster);
    void updateRLPartialBillMas(RLBillMaster rlBillMaster);

    ContractAgreementSummaryDTO getReceiptAmountDetailsForBillPayment(Long contId,
            ContractAgreementSummaryDTO contractAgreementSummaryDTO, Long orgId);

	List<ContractInstalmentDetailDTO> findAdjRecords(Long contId, String yFlag, String string);

	Double getBalanceAmountByContractId(Long contId, Long orgId, String flag);

	Double getTotalAmountByContractId(Long contId, Long orgId, String flag);

	void updateRLBillMaster(Long contId, String status, Date suspendDate, Long empId, Long orgId);

	List<RLBillMaster> finByContractIdPaidFlag(Long contId, Long orgId, String paidFlag, String bmType);

}
