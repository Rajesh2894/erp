package com.abm.mainet.account.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.account.domain.AccountTDSTaxHeadsEntity;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountTDSTaxHeads;
import com.abm.mainet.common.constant.QueryConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.integration.acccount.domain.AccountFieldMasterEntity;

/**
 * @author tejas.kotekar
 *
 */
@Repository
public class AccountTDSTaxHeadsDaoImpl extends AbstractDAO<AccountFieldMasterEntity> implements AccountTDSTaxHeadsDao {

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.account.dao.AccountTDSTaxHeadsDao#getTDSDetails(java.lang.Long, java.lang.Long, java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<AccountTDSTaxHeadsEntity> getTDSDetails(final Long orgId, final Long accountHeadId, final Long tdsTypeId,
            final String status) {

        final StringBuilder builder = new StringBuilder();
        builder.append(QueryConstants.MASTERS.TAX_HEADS_MASER.QUERY_TO_GET_TAX_DETAILS);
        if (accountHeadId != null) {
            builder.append(" and t.budgetCode.prBudgetCodeid =:accountHeadId");
        }
        if (tdsTypeId != null) {
            builder.append(" and t.tbComparamDet.cpdId =:tdsTypeId");
        }
        if ((status != null) && (status != MainetConstants.BLANK)) {
            builder.append(" and t.tdsStatusFlg =:status");
        }

        final Query query = entityManager.createQuery(builder.toString());
        if (accountHeadId != null) {
            query.setParameter(AccountTDSTaxHeads.ACCOUNT_HEAD_ID, accountHeadId);
        }
        if (tdsTypeId != null) {
            query.setParameter(AccountTDSTaxHeads.TDS_TYPE_ID, tdsTypeId);
        }
        if ((status != null) && (status != MainetConstants.BLANK)) {
            query.setParameter(MainetConstants.REQUIRED_PG_PARAM.STATUS, status);
        }

        query.setParameter(MainetConstants.REQUIRED_PG_PARAM.ORGID, orgId);
        final List<AccountTDSTaxHeadsEntity> resultList = query.getResultList();
        return resultList;
    }
}
