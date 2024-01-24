package com.abm.mainet.common.dao;

import java.util.Date;
import java.util.List;

import com.abm.mainet.common.domain.FinancialYear;

public interface IFinancialYearDAO {

    public abstract List<FinancialYear> getFinanceYear();

    public abstract Long getFinanceYearId(Date date);

    public abstract FinancialYear getFinincialYearsById(Long finId, Long orgId);

    /**
     * @param date
     * @return
     */
    public abstract Object[] getFinacialYearByDate(Date date);

    List<FinancialYear> getFinanceYearListFromGivenDate(Long orgId, Long finYearId, Date currentDate);

    Date getMinFinanceYear(Long orgId);

    public abstract List<FinancialYear> getFinanceYearListAfterGivenDate(long orgid, Long faYearId, Date currDate);
    
    List<FinancialYear> getFinanceYearList(List<Long> finIdList);
    
    List<FinancialYear> getFinanceYearListAfterGivenDateForNewBills(long orgid, Long faYearId, Date currDate);

}