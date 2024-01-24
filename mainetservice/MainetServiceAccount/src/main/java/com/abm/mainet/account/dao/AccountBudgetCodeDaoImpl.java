
package com.abm.mainet.account.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.QueryConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.integration.acccount.domain.AccountBudgetCodeEntity;

/**
 * @author prasad.kancharla
 *
 */

@Repository
public class AccountBudgetCodeDaoImpl extends AbstractDAO<AccountBudgetCodeEntity> implements AccountBudgetCodeDao {

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<AccountBudgetCodeEntity> findByAllGridSearchData(final Long dpDeptid, final Long fundId, final Long fieldId,
            final Long functionId,
            final Long sacHeadId, final String cpdIdStatusFlag, final Long orgId, final String objectHeadType) {

        String queryString = QueryConstants.MASTERS.BUDGET_CODE_CREATION.QUERY_TO_SEARCH_ALL_GRID_DATA;
        if (dpDeptid != null) {
            queryString += " and te.tbDepartment.dpDeptid =:dpDeptid";
        }

        if (fundId != null) {
            queryString += " and te.tbAcFundMaster.fundId =:fundId";
        }
        if (fieldId != null) {
            queryString += " and te.tbAcFieldMaster.fieldId =:fieldId";
        }
        if (functionId != null) {
            queryString += " and te.tbAcFunctionMaster.functionId =:functionId";
        }
        if (objectHeadType.equals(MainetConstants.BUDGET_CODE.PRIMARY_WISE_STATUS)) {
            if (sacHeadId != null) {
                queryString += " and te.tbAcPrimaryheadMaster.primaryAcHeadId =:sacHeadId";
            }
        } else {
            if (sacHeadId != null) {
                queryString += " and te.tbAcSecondaryheadMaster.sacHeadId =:sacHeadId";
            }
        }
        if ((cpdIdStatusFlag != null) && !cpdIdStatusFlag.isEmpty()) {
            queryString += " and te.cpdIdStatusFlag =:cpdIdStatusFlag";
        }
        queryString += " order by 1 desc";

        final Query query = createQuery(queryString);

        query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.ORGID,
                orgId);

        if (dpDeptid != null) {
            query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.DEPT_ID,
                    dpDeptid);
        }
        if (fundId != null) {
            query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.FUND_ID,
                    fundId);
        }
        if (fieldId != null) {
            query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.FIELD_ID,
                    fieldId);
        }
        if (functionId != null) {
            query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.FUNCTION_ID,
                    functionId);
        }

        if (sacHeadId != null) {
            query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.SAC_HEAD_ID,
                    sacHeadId);
        }

        if ((cpdIdStatusFlag != null) && !cpdIdStatusFlag.isEmpty()) {
            query.setParameter(MainetConstants.BUDGET_CODE.CPD_ID_STATUS_FLAG,
                    cpdIdStatusFlag);
        }
        List<AccountBudgetCodeEntity> result = null;
        result = query.getResultList();
        return result;
    }

    @Override
    @Transactional
    public Boolean isCombinationExists(final Long dpDeptid, final Long fundId, final Long functionId, final Long fieldId,
            final Long sacId, final Long orgId,
            final String objectHeadType) {

        String queryString = QueryConstants.MASTERS.BUDGET_CODE_CREATION.QUERY_TO_CHEK_DUPLICATE_ENTRY;

        if (dpDeptid == null) {
            queryString += " and te.tbDepartment.dpDeptid is null";
        } else {
            queryString += " and te.tbDepartment.dpDeptid =:dpDeptid";
        }
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
        if (functionId != null) {
            queryString += " and te.tbAcFunctionMaster.functionId =:functionId";
        }

        if (objectHeadType.equals(MainetConstants.BUDGET_CODE.PRIMARY_WISE_STATUS)) {
            if (sacId != null) {
                queryString += " and te.tbAcPrimaryheadMaster.primaryAcHeadId =:sacHeadId";
            }
        } else {
            if (sacId != null) {
                queryString += " and te.tbAcSecondaryheadMaster.sacHeadId =:sacHeadId";
            }
        }
        final Query query = createQuery(queryString);

        if (dpDeptid != null) {
            query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.DEPT_ID,
                    dpDeptid);
        }
        if (fundId != null) {
            query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.FUND_ID,
                    fundId);
        }
        if (functionId != null) {
            query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.FUNCTION_ID,
                    functionId);
        }
        if (fieldId != null) {
            query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.FIELD_ID,
                    fieldId);
        }
        if (sacId != null) {
            query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.SAC_HEAD_ID,
                    sacId);
        }
        query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.ORGID,
                orgId);

        if (query.getResultList().isEmpty()) {
            return false;
        }
        return true;
    }

}