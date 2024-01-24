package com.abm.mainet.bill.service;

import java.util.List;

import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.acccount.dto.TbServiceReceiptMasBean;
import com.abm.mainet.common.integration.acccount.dto.TbSrcptModesDetBean;
import com.abm.mainet.common.integration.dto.TbBillMas;

public interface BillGenerationService {

    /**
     * has to override for revert bills while cheque dishonor
     * @param feedetailDto
     * @param ipAdress
     * @param userId
     * @return
     */
    boolean revertBills(TbServiceReceiptMasBean feedetailDto, Long userId, String ipAdress);

    /**
     * this method used in getting bill for account posting while bill generation for multiple bills
     * @param uniquePrimaryKey
     * @param orgid
     * @return
     */
    List<TbBillMas> fetchListOfBillsByPrimaryKey(List<Long> uniquePrimaryKey, Long orgid);

    /**
     * after successful account posting,posting flag gets updated in current bill
     * @param bmIdNo
     * @param flag
     * @return
     */
    boolean updateAccountPostingFlag(List<Long> bmIdNo, String flag);

    /**
     * this returns latest bill for adjustment service +ve or -ve on current bill
     * @param uniqueSearchNumber
     * @param orgId
     * @return
     */
    List<TbBillMas> fetchCurrentBill(String uniqueSearchNumber, Long orgId);

    /**
     * this method updates the current adjusted bill against +ve or -ve adjustment
     * @param uniqueSearchNumber
     * @param orgId
     * @return
     */
    List<TbBillMas> updateAdjustedCurrentBill(List<TbBillMas> bill);

	TbSrcptModesDetBean getDishonorCharges(Long deptId, Organisation org, TbSrcptModesDetBean chequeModeDto);
	
	boolean revertBills(TbSrcptModesDetBean feedetailDto, Long userId, String ipAdress);
	

    List<TbBillMas> fetchBillsListByBmId(List<Long> uniquePrimaryKey, Long orgid);
    
    String getPropNoByOldPropNo(String oldPropNo, Long orgId);

}
