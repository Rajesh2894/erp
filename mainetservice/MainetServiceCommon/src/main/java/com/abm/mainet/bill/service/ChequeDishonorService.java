package com.abm.mainet.bill.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.acccount.domain.TbSrcptModesDetEntity;
import com.abm.mainet.common.integration.acccount.dto.TbServiceReceiptMasBean;
import com.abm.mainet.common.integration.acccount.dto.TbSrcptModesDetBean;

/**
 * @author Rahul.Yadav
 *
 */
public interface ChequeDishonorService {

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
    List<TbServiceReceiptMasBean> fetchChequePaymentData(List<Long> chequeId, long orgid, Long deptId, Date fromDate, Date toDate,
            Long receiptNo, Long chequeNo, Long bankId);

    /**
     * @param rdModesid
     * @param orgid
     * @return
     */
    TbSrcptModesDetEntity fetchReceiptFeeDetails(Long rdModesid, Long orgid);

    /**
     * @param feeDet
     */
    void updateFeeDet(TbSrcptModesDetEntity feeDet);

    void updateLoiNotPaid(String rmLoiNo, Long orgid);

    Map<Long, String> getULBDepositBanks(long orgid, Long statusId);

    boolean revertBills(TbServiceReceiptMasBean feedetailDto, Long userId, String ipAdress);

	TbSrcptModesDetBean getDishonorCharges(Long deptId, Organisation org, TbSrcptModesDetBean chequeModeDto);
	
	/**
	 * @param mode Details
	 * @param userId
	 * @param ipAdress
	 * @param deptId
	 * @return
	 */
	boolean revertBills(TbSrcptModesDetBean modeDto, Long userId, String ipAdress ,Long deptId);
	
	/**
	 * @param rdModesIds
	 * @param orgId
	 * @return List TbSrcptModesDetEntity 
	 */
	List<TbSrcptModesDetEntity> fetchReceiptFeeDetails(List<Long> rdModesIds, Long orgId);
	
	void saveReceiptModeDetails(List<TbSrcptModesDetEntity> modeDetails);

	List<Object[]> fetchPaymentMode(Long orgId, String dateN);
}
