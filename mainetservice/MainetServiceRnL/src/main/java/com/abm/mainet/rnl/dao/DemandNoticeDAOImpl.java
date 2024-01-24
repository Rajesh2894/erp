package com.abm.mainet.rnl.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;

@Repository
public class DemandNoticeDAOImpl extends AbstractDAO<Long> implements IDemandNoticeDAO {

    @SuppressWarnings("unchecked")
    @Override
    public Object[] getDemandNoticeDataByCond(Long contId, Long orgId, Date date, StringBuilder condition) {
        Object[] data = null;
        final StringBuilder queryString = new StringBuilder();
        queryString
                .append("select contId, sum(balanceAmount) from RLBillMaster where paidFlag = 'N' AND ");
        queryString.append(condition);
        queryString.append(" AND contId =:contId AND orgId =:orgId group by contId");
        final Query hqlQuery = createQuery(queryString.toString());

        hqlQuery.setParameter("contId", contId);
        hqlQuery.setParameter("date", date);
        hqlQuery.setParameter("orgId", orgId);

        List<Object[]> result = new ArrayList<Object[]>();
        result = hqlQuery.getResultList();
        for (Object[] objects : result) {
            data = objects;
        }
        return data;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object[] getDemandRegisterDataByCond(Long contId, Long orgId, Date date, StringBuilder condition) {
        Object[] data = null;
        final StringBuilder queryString = new StringBuilder();
        queryString
                .append("select contId, sum(amount) from RLBillMaster where ");
        queryString.append(condition);
        queryString.append(" AND contId =:contId AND orgId =:orgId group by contId");
        final Query hqlQuery = createQuery(queryString.toString());
        hqlQuery.setParameter("contId", contId);
        hqlQuery.setParameter("date", date);
        hqlQuery.setParameter("orgId", orgId);

        List<Object[]> result = new ArrayList<Object[]>();
        result = hqlQuery.getResultList();
        for (Object[] objects : result) {
            data = objects;
        }
        return data;
    }
}
