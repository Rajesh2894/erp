
package com.abm.mainet.account.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.account.domain.AccountBudgetAdditionalSupplementalEntity;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.QueryConstants;
import com.abm.mainet.common.dao.AbstractDAO;

/**
 * @author prasad.kancharla
 *
 */

@Repository
public class AccountBudgetAdditionalSupplementalDaoImpl extends AbstractDAO<AccountBudgetAdditionalSupplementalEntity>
        implements AccountBudgetAdditionalSupplementalDao {

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<AccountBudgetAdditionalSupplementalEntity> findByGridAllData(final Long faYearid, final Long cpdBugtypeId,
            final Long dpDeptid,
            final Long prBudgetCodeid, final String budgIdentifyFlag, final Long orgId) {

        String queryString = QueryConstants.MASTERS.BUDGET_ADDITIONAL_SUPPLEMENTAL_MASTER.QUERY_TO_GET_ALL_GRID_DATA;

        if (faYearid != null) {
            queryString += " and te.faYearid =:faYearid";
        }

        if (cpdBugtypeId != null) {
            queryString += " and te.cpdBugtypeId =:cpdBugtypeId";
        }

        if (dpDeptid != null) {
            queryString += " and te.dpDeptid =:dpDeptid";
        }

        if (prBudgetCodeid != null) {
            queryString += " and te.tbAcBudgetCodeMaster.prBudgetCodeid =:prBudgetCodeid";
        }

        queryString += " order by 1 desc";

        final Query query = createQuery(queryString);

        query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.ORGID,
                orgId);

        if (faYearid != null) {
            query.setParameter(MainetConstants.BUDGET_ADDITIONALSUPPLEMENTAL.FA_YEAR_ID,
                    faYearid);
        }
        if (cpdBugtypeId != null) {
            query.setParameter(MainetConstants.BUDGET_ADDITIONALSUPPLEMENTAL.BUDGET_TYPE_ID,
                    cpdBugtypeId);
        }
        if (dpDeptid != null) {
            query.setParameter(MainetConstants.BUDGET_ADDITIONALSUPPLEMENTAL.DEPT_ID,
                    dpDeptid);
        }
        if (prBudgetCodeid != null) {
            query.setParameter(MainetConstants.BUDGET_ADDITIONALSUPPLEMENTAL.BUDGET_CODE_ID,
                    prBudgetCodeid);
        }

        query.setParameter(MainetConstants.BUDGET_ADDITIONALSUPPLEMENTAL.BUDGET_IDENTIFY_FLAG_ID,
                budgIdentifyFlag);

        List<AccountBudgetAdditionalSupplementalEntity> result = null;
        result = query.getResultList();
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<AccountBudgetAdditionalSupplementalEntity> findByAuthorizationGridData(final Date frmDate, final Date todate,
            final Long cpdBugtypeId, final String status, final String budgIdentifyFlag, final Long orgId) {

        String queryString = QueryConstants.MASTERS.AccountBudgetAdditionalSupplemental.QUERY_TO_GET_AUTH_GRID_DATA;

        if ((frmDate != null) && (todate != null)) {
            queryString += " and te.lmoddate between :frmDate and :todate";
        }

        if (cpdBugtypeId != null) {
            queryString += " and te.cpdBugtypeId =:cpdBugtypeId";
        }

        if ((status != null) && !status.isEmpty()) {
            queryString += " and te.authFlag =:status";
        }

        queryString += " order by 1 desc";

        final Query query = createQuery(queryString);

        query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.ORGID,
                orgId);

        if ((frmDate != null) && (todate != null)) {
            query.setParameter(MainetConstants.FRM_DATE, frmDate);
            query.setParameter(MainetConstants.TODATE2, todate);
        }

        if (cpdBugtypeId != null) {
            query.setParameter(MainetConstants.BUDGET_REAPPROPRIATION_MASTER.BUDGET_TYPE_ID,
                    cpdBugtypeId);
        }
        if ((status != null) && !status.isEmpty()) {
            query.setParameter(MainetConstants.REQUIRED_PG_PARAM.STATUS, status);
        }
        query.setParameter(MainetConstants.BUDGET_REAPPROPRIATION_MASTER.BUDGET_IDENTIFY_FLAG_ID,
                budgIdentifyFlag);

        List<AccountBudgetAdditionalSupplementalEntity> result = null;
        result = query.getResultList();
        return result;

    }
}
