package com.abm.mainet.common.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.dao.IFinancialYearDAO;
import com.abm.mainet.common.domain.FinancialYear;

/**
 * @author vikrant.thakur
 */
@Service
public class FinancialYearService implements IFinancialYearService {

    @Autowired
    private IFinancialYearDAO financialYearDAO;

    @Transactional
    @Override
    public List<FinancialYear> getAllFinincialYear() {
        return financialYearDAO.getFinanceYear();
    }

    @Transactional
    @Override
    public List<FinancialYear> getFinanceYearListFromGivenDate(Long orgId, Long finYearId, Date currentDate) {
        return financialYearDAO.getFinanceYearListFromGivenDate(orgId, finYearId, currentDate);
    }

    @Override
    @Transactional(readOnly = true)
    public FinancialYear getFinincialYearsById(final Long finId, final Long orgId) {
        return financialYearDAO.getFinincialYearsById(finId, orgId);
    }

    @Override
    @Transactional(readOnly = true)
    public Long getFinanceYearId(final Date date) {
        final Long financeYearId = financialYearDAO.getFinanceYearId(date);

        return financeYearId;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.web.common.service.IFinancialYearService#getFinacialYearByDate(java.util.Date)
     */
    @Override
    public Object[] getFinacialYearByDate(final Date date) {
        return financialYearDAO.getFinacialYearByDate(date);
    }

    @Override
    public Date getMinFinanceYear(Long orgId) {
        return financialYearDAO.getMinFinanceYear(orgId);

    }

    @Override
    @Transactional(readOnly = true)
    public List<FinancialYear> getFinanceYearListAfterGivenDate(long orgid, Long faYearId, Date currDate) {
        return financialYearDAO.getFinanceYearListAfterGivenDate(orgid, faYearId, currDate);
    }

	@Override
	public List<FinancialYear> getFinYearByFinIdList(List<Long> finIdList) {
		return financialYearDAO.getFinanceYearList(finIdList);
	}
	
	@Override
    @Transactional(readOnly = true)
    public List<FinancialYear> getFinanceYearListAfterGivenDateForNewBills(long orgid, Long faYearId, Date currDate) {
        return financialYearDAO.getFinanceYearListAfterGivenDateForNewBills(orgid, faYearId, currDate);
    }

}
