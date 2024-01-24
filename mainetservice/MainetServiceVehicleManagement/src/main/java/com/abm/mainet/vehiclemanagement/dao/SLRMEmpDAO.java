package com.abm.mainet.vehiclemanagement.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.vehiclemanagement.domain.VehicleEmployeeMaster;

@Repository
public class SLRMEmpDAO extends AbstractDAO<VehicleEmployeeMaster>implements ISLRMEmployeeDAO{

    @SuppressWarnings("unchecked")
    @Override
    public List<VehicleEmployeeMaster> searchEmployeeList(Long empId, String empUId, Long mrfId, Long orgId) {
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
    
    private String buildQuery(Long empId, String empUId, Long mrfId) {
        StringBuilder searchQuery = new StringBuilder(
                " SELECT ts FROM VehicleEmployeeMaster ts  WHERE ts.orgid = :orgid ");

        if (empId != null) {
            searchQuery.append(" AND ts.empId = :empId ");
        }

        if (empUId != null) {
            searchQuery.append(" AND ts.empUId = :empUId ");
        }
        if (mrfId != null) {
            searchQuery.append(" AND ts.mrfId = :mrfId ");
        }
        searchQuery.append(" ORDER BY ts.empName ASC ");
        return searchQuery.toString();
    }

}
