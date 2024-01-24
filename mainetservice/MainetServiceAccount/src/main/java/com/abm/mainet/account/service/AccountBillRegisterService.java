package com.abm.mainet.account.service;

import java.util.List;

public interface AccountBillRegisterService {

    /**
     * @param FromDate
     * @param ToDate
     * @return
     */
    List<Object[]> getBillRegisterDetails(Long orgId, String fromDate, String toDate,Long billTyp);

    List<Object[]> getOutStandingBillRegisterDetails(Long orgId, String fromdate, Long dpDeptid,Long accountHeadId,String allHeads);

}
