package com.abm.mainet.cfc.challan.service;

import com.abm.mainet.cfc.challan.dto.ChallanDetailsDTO;
import com.abm.mainet.cfc.challan.dto.ChallanRegenerateDTO;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;

public interface IChallanAtULBCounterService {

    public abstract ChallanDetailsDTO getChallanDetails(
            Long challanNo, Long applicationNo, String payStatus, long orgId,
            long langId);

    public abstract TbServiceReceiptMasEntity updateChallanDetails(
            ChallanDetailsDTO challanDetails,
            CommonChallanDTO offlineMaster,
            Organisation orgnisation, Long taskId, Long empType, Long empId, String empName);

    /**
     * @param challanDetails
     * @param dto
     */
    public abstract CommonChallanDTO regenerateChallanAndInactiveOld(
            ChallanDetailsDTO challanDetails, ChallanRegenerateDTO dto);

    /**
     * @param challanDetail
     * @return
     * @throws LinkageError
     * @throws ClassNotFoundException
     */
    ChallanRegenerateDTO calculateChallanAmount(ChallanDetailsDTO challanDetail) throws ClassNotFoundException, LinkageError;

}