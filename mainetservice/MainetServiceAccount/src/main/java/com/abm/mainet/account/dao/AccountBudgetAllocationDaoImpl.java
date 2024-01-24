
package com.abm.mainet.account.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.account.domain.AccountBudgetAllocationEntity;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.QueryConstants;
import com.abm.mainet.common.dao.AbstractDAO;

@Repository
public class AccountBudgetAllocationDaoImpl extends AbstractDAO<AccountBudgetAllocationEntity>
        implements AccountBudgetAllocationDao {

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.account.dao.AccountBudgetAllocationDao#isCombinationExists(java.lang.Long, java.lang.Long,
     * java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long)
     */
    @Override
    @Transactional
    public Boolean isCombinationExists(final Long faYearid, final Long prRevBudgetCode, final Long orgId) {

        final String queryString = QueryConstants.MASTERS.BUDGET_ALLOCATION_MASTER.QUERY_TO_CHEK_DUPLICATE_ENTRY;

        final Query query = createQuery(queryString);

        query.setParameter(
                MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.FAYEAR_ID,
                faYearid);
        query.setParameter(
                MainetConstants.BUDGET_ALLOCATION_MASTER.BUDGET_REV_CODE_ID,
                prRevBudgetCode);
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
    public List<AccountBudgetAllocationEntity> findByGridAllData(final Long faYearid, final Long cpdBugtypeId,
            final Long dpDeptid,
            final Long prBudgetCodeid, final Long orgId) {

        String queryString = QueryConstants.MASTERS.BUDGET_ALLOCATION_MASTER.QUERY_TO_GET_ALL_GRID_DATA;

        if (faYearid != null) {
            queryString += " and te.financialYear =:faYearid";
        }

        if (cpdBugtypeId != null) {
            queryString += " and te.cpdBugtypeId =:cpdBugtypeId";
        }

        if (dpDeptid != null) {
            queryString += " and te.department =:dpDeptid";
        }

        if (prBudgetCodeid != null) {
            queryString += " and te.tbAcBudgetCodeMaster.prBudgetCodeid =:prBudgetCodeid";
        }
        queryString += " order by 1 desc";

        final Query query = createQuery(queryString);

        query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.ORGID,
                orgId);

        if (faYearid != null) {
            query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.FAYEAR_ID,
                    faYearid);
        }
        if (cpdBugtypeId != null) {
            query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.CPD_BUG_TYPE_ID,
                    cpdBugtypeId);
        }
        if (dpDeptid != null) {
            query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.DEPT_ID,
                    dpDeptid);
        }
        if (prBudgetCodeid != null) {
            query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.BUDGET_CODE_ID,
                    prBudgetCodeid);
        }

        List<AccountBudgetAllocationEntity> result = null;
        result = query.getResultList();
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<Object[]> findByAllBudgetCodeId(final Long faYearid, final Long fundId, final Long functionId,
            final Long cpdBugtypeId,
            final Long prBudgetCodeid, final Long dpDeptid, final Long orgId) {

        String queryString = QueryConstants.MASTERS.BUDGET_ALLOCATION_MASTER.QUERY_TO_GET_ALL_BUDGET_IDS;
        if (faYearid != null) {
            queryString += " and m.financialYear =:faYearid";
        }
        if (fundId != null) {
            queryString += " and s.tbAcFundMaster.fundId =:fundId";
        }
        if (functionId != null) {
            queryString += " and s.tbAcFunctionMaster.functionId=:functionId";
        }

        if (cpdBugtypeId != null) {
            queryString += " and s.cpdBugtypeId =:cpdBugtypeId";
        }

        if (prBudgetCodeid != null) {
            queryString += " and s.prBudgetCodeid =:prBudgetCodeid";
        }
        if (dpDeptid != null) {
            queryString += " and s.tbDepartment.dpDeptid =:dpDeptid";
        }
        queryString += " order by 1 desc";

        final Query query = createQuery(queryString);

        query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.ORGID,
                orgId);
        if (faYearid != null) {
            query.setParameter(MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.FA_YEAR_ID,
                    faYearid);
        }
        if (fundId != null) {
            query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.FUND_ID,
                    fundId);
        }
        if (functionId != null) {
            query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.FUNCTION_ID,
                    functionId);
        }
        if (cpdBugtypeId != null) {
            query.setParameter(MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.BUDGET_TYPE_ID,
                    cpdBugtypeId);
        }

        if (prBudgetCodeid != null) {
            query.setParameter(MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.BUDGET_CODE_ID,
                    prBudgetCodeid);
        }
        if (dpDeptid != null) {
            query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.DEPT_ID,
                    dpDeptid);
        }
        List<Object[]> result = null;
        result = query.getResultList();
        return result;
    }

}
