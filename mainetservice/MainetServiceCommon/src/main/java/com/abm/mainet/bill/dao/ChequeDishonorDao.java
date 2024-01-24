package com.abm.mainet.bill.dao;

import java.util.Date;
import java.util.List;

import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.acccount.domain.TbSrcptModesDetEntity;

/**
 * @author Rahul.Yadav
 *
 */
public interface ChequeDishonorDao {

    /**
     * @param chequeId
     * @param orgid
     * @param chequeNo
     * @param receiptNo
     * @param toDate
     * @param fromDate
     * @param deptId
     * @return
     */
    List<TbServiceReceiptMasEntity> fetchChequePaymentData(List<Long> chequeId, long orgid, Long deptId, Date fromDate,
            Date toDate, Long receiptNo, Long chequeNo, Long bankId);

    /**
     * @param feeDet
     */
    void updateFeeDet(TbSrcptModesDetEntity feeDet);

    /**
     * @param rdModesid
     * @param orgid
     * @return
     */
    TbSrcptModesDetEntity fetchReceiptFeeDetails(Long rdModesid, Long orgid);

    void updateLoiNotPaid(String rmLoiNo, Long orgid);

    /**
     * @param chequeId
     * @param orgid
     * @param chequeNo
     * @param receiptNo
     * @param toDate
     * @param fromDate
     * @param deptId
     * @return
     */
    List<TbServiceReceiptMasEntity> fetchChequePaymentDataWithAccount(List<Long> chequeId, long orgid, Long deptId, Date fromDate,
            Date toDate, Long receiptNo, Long chequeNo, Long bankId);
    
    
    List<TbSrcptModesDetEntity> fetchReceiptFeeDetails(List<Long> rdModesid, Long orgid);

    List<Object[]> fetchPaymentMode(Long orgId, String date1);
}
