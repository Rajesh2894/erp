
package com.abm.mainet.account.repository;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.integration.acccount.domain.AccountBudgetCodeEntity;

/**
 * @author Vivek.Kumar
 *
 */
@Repository
public class BudgetHeadRepositoryImpl extends AbstractDAO<AccountBudgetCodeEntity> implements BudgetHeadRepositoryCustom {

    // @PersistenceContext
    // private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> fetchBudgetHeads(final Long payMode, final Long headTypeId, final Long budgetCodeId,
            final Long superOrgId, final Long orgId) {

        final Query query = entityManager.createQuery(buildDynamicQuery(payMode, headTypeId, budgetCodeId, superOrgId, orgId));
        if ((payMode != null) && (payMode != 0l)) {
            query.setParameter("modeId", payMode);
        }
        if ((orgId != null) && (orgId != 0l)) {
            query.setParameter("orgId", orgId);
        }
        if ((headTypeId != null) && (headTypeId != 0l)) {
            query.setParameter("cpdIdHeadType", headTypeId);
        }
        if ((budgetCodeId != null) && (budgetCodeId != 0l)) {
            query.setParameter("budgetCodeId", budgetCodeId);
        }

        return query.getResultList();
    }

    private String buildDynamicQuery(final Long payMode, final Long headTypeId, final Long budgetCodeId, final Long superOrgId,
            final Long orgId) {

        final StringBuilder builder = new StringBuilder();
        builder.append("SELECT abc.prBudgetCodeid, abc.prBudgetCode, ");
        builder.append("phm.primaryAcHeadCompcode, shm.sacHeadCode, ");
        builder.append("shm.sacHeadDesc");
        builder.append(" FROM AccountBudgetCodeEntity abc, AccountHeadSecondaryAccountCodeMasterEntity shm, ");
        builder.append("AccountHeadPrimaryAccountCodeMasterEntity phm");
        builder.append(" WHERE phm.primaryAcHeadId=shm.tbAcPrimaryheadMaster.primaryAcHeadId");
        builder.append(" AND shm.sacHeadId=abc.tbAcSecondaryheadMaster.sacHeadId");
        builder.append(" AND shm.orgid=abc.orgid ");
        if ((payMode != null) && (payMode != 0l)) {
            builder.append(" AND phm.payMode=:modeId");
        }
        if ((orgId != null) && (orgId != 0l)) {
            builder.append(" AND shm.orgid=:orgId");
        }
        if ((headTypeId != null) && (headTypeId != 0l)) {
            builder.append(" AND phm.cpdIdAcHeadTypes =:cpdIdHeadType");
        }
        if ((budgetCodeId != null) && (budgetCodeId != 0l)) {
            builder.append(" AND abc.prBudgetCodeid =:budgetCodeId");
        }

        return builder.toString();
    }
}
