package com.abm.mainet.common.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.domain.FinancialYear;

/**
 * @author vikrant.thakur
 *
 */
@Repository
public class FinancialYearDAO extends AbstractDAO<FinancialYear> implements IFinancialYearDAO {

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.dao.IFinancialYearDAO#getFinanceYear()
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<FinancialYear> getFinanceYear() {
        final Query query = createQuery(
                "SELECT fy FROM FinancialYear fy Where fy.faFromDate <= CURDATE() order by fy.faFromDate desc");

        return query.getResultList();

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.dao.IFinancialYearDAO#getFinanceYearListFromGivenDate()
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<FinancialYear> getFinanceYearListFromGivenDate(Long orgId, Long finYearId, Date currentDate) {
        final Query query = entityManager.createQuery("select fy from FinancialYear fy where fy.faFromDate>= "
                + " (select faFromDate from FinancialYear where faYear=:finYearId) and fy.faFromDate < :currentDate order by fy.faFromDate ");
        query.setParameter("finYearId", finYearId);
        query.setParameter("currentDate", currentDate);
        return query.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Date getMinFinanceYear(Long orgId) {
        final Query query = createQuery(
                "select min(f.faFromDate) from FinancialYear f where f.faYear in "
                        + "(select o.tbFinancialyear.faYear from TbFincialyearorgMapEntity o where o.orgid=:orgId)");
        query.setParameter("orgId", orgId);
        return (Date) query.getSingleResult();

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.web.common.dao.IFinancialYearDAO#getFinincialYearsById(java.lang.Long, java.lang.Long)
     */
    @Override
    public FinancialYear getFinincialYearsById(final Long finId, final Long orgId) {
        final Query query = createQuery("SELECT fy FROM FinancialYear fy where  fy.faYear=:finId");
        query.setParameter("finId", finId);
        return (FinancialYear) query.getSingleResult();
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.web.common.dao.IFinancialYearDAO#getFinanceYearId(java.util.Date)
     */
    @Override
    public Long getFinanceYearId(final Date date) {

        final Query query = createQuery(
                "SELECT fy.faYear FROM FinancialYear fy Where  fy.faFromDate <= :dateField and fy.faToDate >= :dateField");
        query.setParameter("dateField", date, TemporalType.DATE);
        final Long financeYearId = (Long) query.getSingleResult();
        return financeYearId;

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.web.common.dao.IFinancialYearDAO#getFinacialYearByDate(java.util.Date)
     */
    @Override
    public Object[] getFinacialYearByDate(final Date date) {
        Object[] financeYearId = null;
        try {
            final Query query = entityManager.createQuery(
                    "select f.faYear,f.faFromDate,f.faToDate  from FinancialYear f where ?1 between f.faFromDate and f.faToDate");
            query.setParameter(1, date, TemporalType.DATE);
            financeYearId = (Object[]) query.getSingleResult();
        } catch (final NoResultException e) {
            e.printStackTrace();
        }
        return financeYearId;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<FinancialYear> getFinanceYearListAfterGivenDate(long orgid, Long faYearId, Date currDate) {
        final Query query = entityManager.createQuery("select fy from FinancialYear fy where fy.faFromDate> "
                + " (select faFromDate from FinancialYear where faYear=:finYearId) and fy.faFromDate <= :currentDate order by fy.faFromDate ");
        query.setParameter("finYearId", faYearId);
        query.setParameter("currentDate", currDate);
        return query.getResultList();
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<FinancialYear> getFinanceYearList(List<Long> finIdList) {
		final Query query = entityManager.createQuery("select fy from FinancialYear fy where fy.faYear in (:finYearId)");
        query.setParameter("finYearId", finIdList);
        return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
    @Override
    public List<FinancialYear> getFinanceYearListAfterGivenDateForNewBills(long orgid, Long faYearId, Date currDate) {
        final Query query = entityManager.createQuery("select fy from FinancialYear fy where fy.faFromDate>= "
                + " (select faFromDate from FinancialYear where faYear=:finYearId) and fy.faFromDate <= :currentDate order by fy.faFromDate ");
        query.setParameter("finYearId", faYearId);
        query.setParameter("currentDate", currDate);
        return query.getResultList();
    }

}
