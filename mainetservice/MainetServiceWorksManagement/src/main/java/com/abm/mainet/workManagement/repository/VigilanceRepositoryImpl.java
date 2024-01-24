package com.abm.mainet.workManagement.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.workManagement.domain.Vigilance;

/**
 * 
 * @author Jeetendra.Pal
 *
 */
@Repository
public class VigilanceRepositoryImpl extends AbstractDAO<Long> implements VigilanceRepositoryCustome {

    @SuppressWarnings("unchecked")
    @Override
    public List<Vigilance> getFilterVigilanceList(String refType, String refNumber, Date memoDate, Date inspectionDate,
            Long orgId) {

        List<Vigilance> entity = new ArrayList<>();

        StringBuilder hql = new StringBuilder("SELECT v FROM Vigilance v  where v.orgId = :orgId");

        if (refType != null && !refType.isEmpty()) {
            hql.append(" and v.referenceType = :referenceType ");
        }
        if (refNumber != null && !refNumber.isEmpty()) {
            hql.append(" and v.referenceNumber = :referenceNumber ");
        }
        if (memoDate != null) {
            hql.append(" and v.memoDate = :memoDate ");
        }
        if (inspectionDate != null) {
            hql.append(" and v.inspectionDate = :inspectionDate");
        }

        final Query query = entityManager.createQuery(hql.toString());
        query.setParameter(MainetConstants.Common_Constant.ORGID, orgId);

        if (refType != null && !refType.isEmpty()) {
            query.setParameter("referenceType", refType);
        }
        if (refNumber != null && !refNumber.isEmpty()) {
            query.setParameter("referenceNumber", refNumber);
        }
        if (memoDate != null) {
            query.setParameter("memoDate", memoDate);
        }
        if (inspectionDate != null) {
            query.setParameter("inspectionDate", inspectionDate);
        }
        entity = query.getResultList();

        return entity;

    }

}
