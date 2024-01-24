package com.abm.mainet.common.service;

import java.util.Date;
import java.util.List;

import com.abm.mainet.common.domain.FinancialYear;

public interface IFinancialYearService {

    public Long getFinanceYearId(Date date);

    public List<FinancialYear> getAllFinincialYear();

    public FinancialYear getFinincialYearsById(Long long1, Long orgId);

    /**
     * @param date
     * @return
     */
    public Object[] getFinacialYearByDate(Date date);

    List<FinancialYear> getFinanceYearListFromGivenDate(Long orgId, Long finYearId, Date currentDate);

    Date getMinFinanceYear(Long orgId);

    public List<FinancialYear> getFinanceYearListAfterGivenDate(long orgid, Long faYearId, Date currDate);
    
    List<FinancialYear> getFinYearByFinIdList(List<Long> finIdList);

    List<FinancialYear> getFinanceYearListAfterGivenDateForNewBills(long orgid, Long faYearId, Date currDate);
}
