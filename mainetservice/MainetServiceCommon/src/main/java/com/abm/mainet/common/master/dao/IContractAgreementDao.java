package com.abm.mainet.common.master.dao;

import java.util.Date;
import java.util.List;

import com.abm.mainet.common.domain.ContractDetailEntity;
import com.abm.mainet.common.master.dto.ContractAgreementSummaryDTO;

public interface IContractAgreementDao {
    List<Object[]> getContractAgreementSummary(Long orgId, String contractNo,
            Date contractDate, Long deptId, Long venderId, String viewClosedCon, String renewal);

    List<Object[]> findPrintContractAgreementByContId(Long orgId, Long contId);

    List<Object[]> findByContractNo(Long orgId, String contNo);

    ContractDetailEntity saveUpdateContractDetailEntity(ContractDetailEntity contractDetailEntity);

    void updateContractDetailActiveFlag(Long contdId, Long empId);

    /**
     * 
     * @param orgId
     * @param contractNo
     * @param contractDate
     * @param deptId
     * @param venderId
     * @param viewClosedCon
     * @return
     */
    List<Object[]> getContractFilterData(Long orgId, String contractNo,
            Date contractDate, String viewClosedCon);

	List<Object[]> getBillDetailbyContAndMobile(String contNo, String mobileNo, Long orgId);

	List<Object[]> getRLContractAgreementSummaryData(Long orgId, String contractNo, Date contractFormDate, Long deptId,
			String viewClosedCon, String renewal, Long estateId, Long propId);
}
