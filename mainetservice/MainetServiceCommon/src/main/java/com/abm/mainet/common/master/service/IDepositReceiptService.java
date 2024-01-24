package com.abm.mainet.common.master.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.TbDepositDto;
import com.abm.mainet.common.integration.acccount.dto.TbServiceReceiptMasBean;

/**
 * @author jeetedra.pal
 *
 */
public interface IDepositReceiptService {

    /**
     * use to save ,update Deposit Receipt Entry
     * 
     * @param TbDepositDto
     */
    void saveAndUpdateDepositReceiptEntry(TbDepositDto tbDepositDto);

    /**
     * use to save ,update Deposit Receipt Entry
     * 
     * @param CommonChallanDTO
     */
    TbServiceReceiptMasBean saveReceiptEntry(CommonChallanDTO requestDto);

    /**
     * used to get Deposit Refund List
     * 
     * @param depositeNo
     * @param depositeType
     * @param depositDate
     * @param vendorId
     * @param depositAmount
     * @param accountHead
     * @param orgId
     * @return
     */
    List<TbDepositDto> getDepositRefundList(Long depositeNo, Long depositeType, Date depositDate, Long vendorId,
            BigDecimal depositAmount, Long accountHead, Long orgId);

    /**
     * used to get Deposit RefundBy Id
     * 
     * @param depositId
     * @return
     */
    TbDepositDto getDepositRefundById(Long depositId);
    
    
    /**
     * use to save  Deposit Receipt Entry
     * 
     * @param TbDepositDto
     */
    void saveDepositReceiptEntry(TbDepositDto tbDepositDto);

}
