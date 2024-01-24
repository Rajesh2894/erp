package com.abm.mainet.cfc.challan.dao;

import com.abm.mainet.cfc.challan.dto.ChallanDetailsDTO;

public interface IChallanAtULBCounterDAO {

    public abstract Object[] queryChallanDetails(Long challanNo, Long applicationNo, String payStaus, long orgId);

    public abstract boolean updateChallanDetails(ChallanDetailsDTO challanDetails);

    /**
     * @param applicationNo
     * @param challanNo
     * @param orgid
     */
    public abstract void inactiveOldChallan(Long applicationNo, Long challanNo,
            long orgid);

}