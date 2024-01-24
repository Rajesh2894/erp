
package com.abm.mainet.account.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.account.constants.AccountMasterQueryConstant;
import com.abm.mainet.account.domain.AccountBudgetOpenBalanceEntity;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.QueryConstants;
import com.abm.mainet.common.dao.AbstractDAO;

/**
 * @author prasad.kancharla
 *
 */
@Repository
public class AccountBudgetOpenBalanceDaoImpl extends AbstractDAO<AccountBudgetOpenBalanceEntity>
        implements AccountBudgetOpenBalanceDao {

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.account.dao.AccountBudgetAllocationDao#isCombinationExists(java.lang.Long, java.lang.Long,
     * java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long)
     */
    @Override
    @Transactional
    public Boolean isCombinationExists(final Long faYearid, final Long fundId, final Long fieldId, final String cpdIdDrcr,
            final Long sacHeadId, final Long orgId) {

        String queryString = QueryConstants.MASTERS.BUGOPEN_BALANCE.QUERY_TO_CHEK_DUPLICATE_ENTRY;

        if (fundId == null) {
            queryString += " and te.tbAcFundMaster.fundId is null";
        } else {
            queryString += " and te.tbAcFundMaster.fundId =:fundId";
        }
        if (fieldId == null) {
            queryString += " and te.tbAcFieldMaster.fieldId is null";
        } else {
            queryString += " and te.tbAcFieldMaster.fieldId =:fieldId";
        }
        queryString += " order by 1 desc";

        final Query query = createQuery(queryString);

        query.setParameter(
                MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.FAYEAR_ID,
                faYearid);
        if (fundId != null) {
            query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.FUND_ID,
                    fundId);
        }
        if (fieldId != null) {
            query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.FIELD_ID,
                    fieldId);
        }
        query.setParameter(
                MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.CPD_ID_DR_CR,
                cpdIdDrcr);
        query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.SAC_HEAD_ID,
                sacHeadId);
        query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.ORGID,
                orgId);

        if (query.getResultList().isEmpty()) {
            return false;
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<AccountBudgetOpenBalanceEntity> findByGridDataFinancialId(final Long faYearid, final String cpdIdDrcr,
            final Long sacHeadId,
            final String status, final Long orgId) {

        String queryString = AccountMasterQueryConstant.QUERY_TO_GET_GRID_DATA_FINANCIAL_ID;
        if (faYearid != null) {
            queryString += " and te.faYearid =:faYearid";
        }
        if ((cpdIdDrcr != null) && !cpdIdDrcr.isEmpty()) {
            queryString += " and te.opnBalType =:cpdIdDrcr";
        }
        if (sacHeadId != null) {
            queryString += " and te.tbAcSecondaryheadMaster.sacHeadId =:sacHeadId";
        }
        if ((status != null) && !status.isEmpty()) {
            queryString += " and te.flagFlzd =:status";
        }
        queryString += " order by 1 desc";

        final Query query = createQuery(queryString);

        query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.ORGID,
                orgId);

        if (faYearid != null) {
            query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.FAYEAR_ID,
                    faYearid);
        }
        if ((cpdIdDrcr != null) && !cpdIdDrcr.isEmpty()) {
            query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.CPD_ID_DR_CR,
                    cpdIdDrcr);
        }
        if (sacHeadId != null) {
            query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.SAC_HEAD_ID,
                    sacHeadId);
        }
        if ((status != null) && !status.isEmpty()) {
            query.setParameter(MainetConstants.REQUIRED_PG_PARAM.STATUS,
                    status);
        }
        List<AccountBudgetOpenBalanceEntity> result = null;
        result = query.getResultList();
        return result;
    }

}
