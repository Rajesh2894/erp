/**
 *
 */
package com.abm.mainet.swm.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.swm.domain.PumpMaster;

/**
 * @author Lalit.Prusti
 *
 * Created Date : 07-May-2018
 */
@Repository
public class PumpMasterDAO extends AbstractDAO<PumpMaster> implements IPumpMasterDAO {

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.dao.PumpMasterDAO#serchPumpByPumpTypeAndPumpRegNo(java.lang.Long, java.lang.String, java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<PumpMaster> searchPumpByPumpType(String status, Long pumpType, Long puId, String pumpName, Long orgId) {
        Query query = this.createQuery(buildQuery(status, pumpType, puId, pumpName));

        query.setParameter("orgid", orgId);

        if (StringUtils.isNotEmpty(status)) {
            query.setParameter("status", status);
        }

        if (null != pumpType) {
            query.setParameter("pumpType", pumpType);
        }
        if (null != puId) {
            query.setParameter("puId", puId);
        }

        if (StringUtils.isNotBlank(pumpName)) {
            query.setParameter("pumpName", pumpName);
        }

        return query.getResultList();
    }

    /**
     * Builds the query.
     *
     * @param pumpType the pump type
     * @param pumpName the pump name
     * @return the string
     */
    private String buildQuery(String status, Long pumpType, Long puId, String pumpName) {
        StringBuilder pumpMasterSearchQuery = new StringBuilder(
                " SELECT pm FROM PumpMaster pm WHERE pm.orgid = :orgid ");

        if (StringUtils.isNotEmpty(status)) {
            pumpMasterSearchQuery.append(" AND pm.puActive like :status ");
        }

        if (null != pumpType) {
            pumpMasterSearchQuery.append(" AND pm.puPutype = :pumpType ");
        }

        if (null != puId) {
            pumpMasterSearchQuery.append(" AND pm.puId != :puId ");
        }
        if (StringUtils.isNotBlank(pumpName)) {
            pumpMasterSearchQuery.append(" AND pm.puPumpname = :pumpName ");
        }

        pumpMasterSearchQuery.append(" ORDER BY pm.puId  DESC ");

        return pumpMasterSearchQuery.toString();
    }

}
