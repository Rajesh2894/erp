package com.abm.mainet.common.master.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.domain.CustomerMasterEntity;

@Repository
public class CustomerMasterDaoImpl extends AbstractDAO<CustomerMasterEntity> implements CustomerMasterDAO {

    @SuppressWarnings("unchecked")
    @Override
    public List<CustomerMasterEntity> searchCustomer(String custName, String custAddress, Long orgId) {
        Query query = this.createQuery(buildQuery(custName, custAddress));

        query.setParameter("orgid", orgId);

        if (StringUtils.isNotEmpty(custName)) {
            query.setParameter("custName", custName);
        }
        if (StringUtils.isNotEmpty(custAddress)) {
            query.setParameter("custAddress", custAddress);
        }

        return query.getResultList();
    }

    private String buildQuery(String custName, String custAddress) {
        StringBuilder searchQuery = new StringBuilder(
                " SELECT ts FROM CustomerMasterEntity ts  WHERE ts.orgid = :orgid ");

        if (StringUtils.isNotEmpty(custName)) {
            searchQuery.append(" AND ts.custName = :custName ");
        }

        if (StringUtils.isNotEmpty(custAddress)) {
            searchQuery.append(" AND ts.custAddress = :custAddress ");
        }

        searchQuery.append(" ORDER BY ts.custId DESC ");
        return searchQuery.toString();
    }

}
