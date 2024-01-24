package com.abm.mainet.swm.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.swm.domain.EmployeeSchedule;

/**
 * @author Ajay.Kumar
 *
 */
@Repository
public class EmployeeScheduleDAO extends AbstractDAO<EmployeeSchedule> implements IEmployeeScheduleDAO {

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.dao.IEmployeeScheduleDAO#searchEmployeeScheduleByEmployeeName(java.lang.Long, java.util.Date,
     * java.util.Date, java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<EmployeeSchedule> searchEmployeeScheduleByEmployeeName(Long empid, Date fromDate, Date toDate,
            Long orgId) {
        Query query = this.createQuery(buildQuery(empid, fromDate, toDate));

        query.setParameter("orgid", orgId);

        if (empid != null) {
            query.setParameter("empid", empid);
        }
        if (fromDate != null) {
            query.setParameter("fromDate", fromDate);
        }
        if (toDate != null) {
            query.setParameter("toDate", toDate);
        }

        return query.getResultList();
    }

    /**
     * @param empid
     * @param fromDate
     * @param toDate
     * @return
     */
    private String buildQuery(Long empid, Date fromDate, Date toDate) {
        StringBuilder searchQuery = new StringBuilder(
                " SELECT distinct es FROM EmployeeSchedule es JOIN FETCH es.tbSwEmployeeScheddets det WHERE es.orgid = :orgid ");

        if (empid != null) {
            searchQuery.append(" AND det.empid = :empid ");
        }
        if (fromDate != null) {
            searchQuery.append(" AND es.emsFromdate >= :fromDate ");
        }
        if (toDate != null) {
            searchQuery.append(" AND es.emsTodate <= :toDate ");
        }
        searchQuery.append(" ORDER BY es.emsId DESC ");
        return searchQuery.toString();
    }

}
