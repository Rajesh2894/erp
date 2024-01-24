package com.abm.mainet.swm.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.swm.domain.SLRMEmployeeMaster;

@Repository
public class SLRMEmployeeDAO extends AbstractDAO<SLRMEmployeeMaster>implements ISLRMEmployeeDAO{

    @SuppressWarnings("unchecked")
    @Override
    public List<SLRMEmployeeMaster> searchEmployeeList(Long empId, Long empUId, Long mrfId, Long orgId) {
        Query query = this.createQuery(buildQuery(empId, empUId, mrfId));

        query.setParameter("orgid", orgId);

        if (empId != null) {
            query.setParameter("empId", empId);
        }
        if (empUId != null) {
            query.setParameter("empUId", empUId);
        }
        if (mrfId != null) {
            query.setParameter("mrfId", mrfId);
        }

        return query.getResultList();
    }
    
    private String buildQuery(Long empId, Long empUId, Long mrfId) {
        StringBuilder searchQuery = new StringBuilder(
                " SELECT ts FROM SLRMEmployeeMaster ts  WHERE ts.orgid = :orgid ");

        if (empId != null) {
            searchQuery.append(" AND ts.empId = :empId ");
        }

        if (empUId != null) {
            searchQuery.append(" AND ts.empUId = :empUId ");
        }
        if (mrfId != null) {
            searchQuery.append(" AND ts.mrfId = :mrfId ");
        }
        searchQuery.append(" ORDER BY ts.empId DESC ");
        return searchQuery.toString();
    }

}
