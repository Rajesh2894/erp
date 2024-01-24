
package com.abm.mainet.account.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.account.domain.AccountBudgetProjectedRevenueEntryEntity;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountBudgetProjectedExpenditure;
import com.abm.mainet.common.constant.QueryConstants;
import com.abm.mainet.common.dao.AbstractDAO;

@Repository
public class AccountBudgetProjectedRevenueEntryDaoImpl extends AbstractDAO<AccountBudgetProjectedRevenueEntryEntity>
        implements AccountBudgetProjectedRevenueEntryDao {

    private static final Logger LOGGER = Logger.getLogger(AccountBudgetProjectedRevenueEntryDaoImpl.class);

    @Override
    @Transactional
    public Boolean isCombinationExists(final Long faYearid, final Long prBudgetCodeid, final Long orgId,final Long deptId,Long fieldId) {

        final Query query = createQuery(
                QueryConstants.MASTERS.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.QUERY_TO_CHEK_DUPLICATE_ENTRY);
        query.setParameter(MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.FA_YEAR_ID,
                faYearid);
        query.setParameter(MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.BUDGET_CODE_ID,
                prBudgetCodeid);
        query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.ORGID,
                orgId);
        query.setParameter("deptId",
        		deptId);
        query.setParameter("fieldId",
        		fieldId);
        if (query.getResultList().isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public Boolean isCombinationCheckTransactions(final Long prProjectionId, final Long faYearId, final Long orgId) {

        final Query query = createQuery(
                QueryConstants.MASTERS.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.QUERY_TO_GET_ALL_TRANSACTIONS);
        query.setParameter(AccountBudgetProjectedExpenditure.PR_PROJECTION_ID,
                prProjectionId);
        query.setParameter(MainetConstants.AccountBudgetProjectedExpenditure.FA_YEAR_ID,
                faYearId);
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
    public List<Object[]> getOrgEsmtAmtsLFYear(final Long faYearIds, final Long budgCodeid, final Long orgId) {

        final Query query = createQuery(
                QueryConstants.MASTERS.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.QUERY_TO_LASTFINYEARS_ORGAMOUNT);
        query.setParameter(MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.FA_YEAR_ID,
                faYearIds);
        query.setParameter(MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.BUDGET_CODE_ID,
                budgCodeid);
        query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.ORGID,
                orgId);

        List<Object[]> result = null;
        try {
            result = query.getResultList();
        } catch (final Exception e) {
            LOGGER.error("No record found from table for provided input Params[faYearIds=" + faYearIds + "+budgCodeid="
                    + budgCodeid + "orgId=" + orgId + "]", e);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> findByGridAllData(Long faYearid, Long fundId, Long functionId, Long cpdBugsubtypeId,
            Long dpDeptid, Long prBudgetCodeid, Long fieldId, Long orgId) {

        String queryString = "select te.prProjectionid, be.prBudgetCodeid, be.prBudgetCode, te.orginalEstamt, te.revisedEstamt, te.fieldId, te.tbDepartment from AccountBudgetProjectedRevenueEntryEntity te, AccountBudgetCodeEntity be where te.orgid =:orgId and te.orgid=be.orgid and te.tbAcBudgetCodeMaster.prBudgetCodeid=be.prBudgetCodeid ";

        if (fundId != null) {
            queryString += " and be.tbAcFundMaster.fundId =:fundId";
        }

        if (functionId != null) {
            queryString += " and be.tbAcFunctionMaster.functionId =:functionId";
        }

        if (faYearid != null) {
            queryString += " and te.faYearid =:faYearid";
        }

        if (cpdBugsubtypeId != null) {
            queryString += " and te.cpdBugsubtypeId =:cpdBugsubtypeId";
        }

        if (dpDeptid != null) {
            queryString += " and te.tbDepartment.dpDeptid =:dpDeptid";
        }

        if (prBudgetCodeid != null) {
            queryString += " and te.tbAcBudgetCodeMaster.prBudgetCodeid =:prBudgetCodeid";
        }
        
        if (fieldId != null) {
            queryString += " and te.fieldId =:fieldId";
        }
        
        queryString += " order by 1 desc";

        final Query query = createQuery(queryString);

        query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.ORGID,
                orgId);

        if (fundId != null) {
            query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.FUND_ID,
                    fundId);
        }
        if (functionId != null) {
            query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.FUNCTION_ID,
                    functionId);
        }
        if (faYearid != null) {
            query.setParameter(MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.FA_YEAR_ID,
                    faYearid);
        }
        if (cpdBugsubtypeId != null) {
            query.setParameter(MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.BUDGETSUB_TYPE_ID,
                    cpdBugsubtypeId);
        }
        if (dpDeptid != null) {
            query.setParameter(MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.DEPT_ID,
                    dpDeptid);
        }
        if (prBudgetCodeid != null) {
            query.setParameter(MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.BUDGETCODE_ID,
                    prBudgetCodeid);
        }
        if (fieldId != null) {
            query.setParameter(MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.FIELD_ID,
            		fieldId);
        }
        List<Object[]> result = null;
        result = query.getResultList();
        return result;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> findByGridAllDatas(Long faYearid, Long fundId, Long functionId, Long cpdBugsubtypeId,
            Long dpDeptid, Long prBudgetCodeid, Long fieldId, Long orgId) {

        String queryString = "select te.prProjectionid, be.prBudgetCodeid, be.tbAcSecondaryheadMaster.acHeadCode, te.orginalEstamt, te.revisedEstamt, te.fieldId, te.tbDepartment from AccountBudgetProjectedRevenueEntryEntity te, AccountBudgetCodeEntity be where te.orgid =:orgId and te.orgid=be.orgid and te.tbAcBudgetCodeMaster.prBudgetCodeid=be.prBudgetCodeid ";

        if (fundId != null) {
            queryString += " and be.tbAcFundMaster.fundId =:fundId";
        }

        if (functionId != null) {
            queryString += " and be.tbAcFunctionMaster.functionId =:functionId";
        }

        if (faYearid != null) {
            queryString += " and te.faYearid =:faYearid";
        }

        if (cpdBugsubtypeId != null) {
            queryString += " and te.cpdBugsubtypeId =:cpdBugsubtypeId";
        }

        if (dpDeptid != null) {
            queryString += " and te.tbDepartment.dpDeptid =:dpDeptid";
        }

        if (prBudgetCodeid != null) {
            queryString += " and te.tbAcBudgetCodeMaster.prBudgetCodeid =:prBudgetCodeid";
        }
        
        if (fieldId != null) {
            queryString += " and te.fieldId =:fieldId";
        }
        
        queryString += " order by 1 desc";

        final Query query = createQuery(queryString);

        query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.ORGID,
                orgId);

        if (fundId != null) {
            query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.FUND_ID,
                    fundId);
        }
        if (functionId != null) {
            query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.FUNCTION_ID,
                    functionId);
        }
        if (faYearid != null) {
            query.setParameter(MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.FA_YEAR_ID,
                    faYearid);
        }
        if (cpdBugsubtypeId != null) {
            query.setParameter(MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.BUDGETSUB_TYPE_ID,
                    cpdBugsubtypeId);
        }
        if (dpDeptid != null) {
            query.setParameter(MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.DEPT_ID,
                    dpDeptid);
        }
        if (prBudgetCodeid != null) {
            query.setParameter(MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.BUDGETCODE_ID,
                    prBudgetCodeid);
        }
        if (fieldId != null) {
            query.setParameter(MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.FIELD_ID,
            		fieldId);
        }
        List<Object[]> result = null;
        result = query.getResultList();
        return result;
    }

}
