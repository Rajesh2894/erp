
package com.abm.mainet.account.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.account.domain.AccountBudgetEstimationPreparationEntity;
import com.abm.mainet.account.dto.AccountBudgetEstimationPreparationBean;
import com.abm.mainet.account.mapper.AccountBudgetEstimationPreparationServiceMapper;
import com.abm.mainet.account.service.AccountBudgetEstimationPreparationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.QueryConstants;
import com.abm.mainet.common.dao.AbstractDAO;


@Repository
public class AccountBudgetEstimationPreparationDaoImpl extends AbstractDAO<AccountBudgetEstimationPreparationEntity>
        implements AccountBudgetEstimationPreparationDao {
	
	 @Resource
     private AccountBudgetEstimationPreparationServiceMapper accountBudgetEstimationPreparationServiceMapper;

    @Override
    @Transactional
    public Boolean isBudgetEstimationPreparationEntryExists(final Long faYearid, final Long prRevBudgetCode, final Long orgId,Long deptId) {

        final String queryString = QueryConstants.MASTERS.BUDGET_ESTIMATIONPREPARATION_MASTER.QUERY_TO_CHEK_DUPLICATE_ENTRY;

        final Query query = createQuery(queryString);

        query.setParameter(
                MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.FAYEAR_ID,
                faYearid);
        query.setParameter(
                MainetConstants.BUDGET_ESTIMATION_PREPARATION.BUDGET_REV_BUDGET_CODE,
                prRevBudgetCode);
        query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.ORGID,
                orgId);
        query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.DEPT_ID,
        		deptId);

        if (query.getResultList().isEmpty()) {
            return false;
        }
        return true;

    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<AccountBudgetEstimationPreparationEntity> findByGridAllData(final Long faYearid, final Long cpdBugtypeId,
            final Long dpDeptid,
            final Long prBudgetCodeid, final Long orgId) {

        String queryString = QueryConstants.MASTERS.BUDGET_ESTIMATIONPREPARATION_MASTER.QUERY_TO_GET_ALL_GRID_DATA;

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

        List<AccountBudgetEstimationPreparationEntity> result = null;
        result = query.getResultList();
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<Object[]> findByAllBudgetCodeId(final Long faYearid, final Long fundId, final Long functionId,
            final Long cpdBugtypeId,
            final Long prBudgetCodeid, final Long dpDeptid, final Long orgId) {

        String queryString = QueryConstants.MASTERS.BUDGET_ESTIMATIONPREPARATION_MASTER.QUERY_TO_GET_ALL_BUDGET_IDS;
        
        
        
        if (faYearid != null) {
            queryString += " and m.faYearid =:faYearid";
        }
        if (fundId != null) {
            queryString += " and s.tbAcFundMaster.fundId =:fundId";
        }
        if (functionId != null) {
            queryString += " and s.tbAcFunctionMaster.functionId=:functionId";
        }

        if (cpdBugtypeId != null) {
            queryString += " and m.cpdBugtypeId =:cpdBugtypeId";
        }

        if (prBudgetCodeid != null) {
            queryString += " and m.tbAcBudgetCodeMaster.prBudgetCodeid =:prBudgetCodeid";
        }
        if (dpDeptid != null) {
            queryString += " and m.dpDeptid =:dpDeptid";
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

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
	public List<Object[]> findByAllBudgetCodeIdBulkEdit(Long faYearid, Long cpdBugtypeId, Long dpDeptid,
			Long cpdBugsubtypeId, Long fieldId, Long orgId) {
		String queryString = QueryConstants.MASTERS.BUDGET_ESTIMATIONPREPARATION_MASTER.QUERY_TO_GET_ALL_BUDGET_IDS;

        if (faYearid != null) {
            queryString += " and m.faYearid =:faYearid";
        }	

        if (cpdBugtypeId != null) {
            queryString += " and m.cpdBugtypeId =:cpdBugtypeId";
        }

        if (dpDeptid != null) {
            queryString += " and m.dpDeptid =:dpDeptid";
        }

        if (cpdBugsubtypeId != null) {
            queryString += " and m.cpdBugsubtypeId =:cpdBugsubtypeId";
        }
        
        if (fieldId != null) {
            queryString += " and m.fieldId =:fieldId";
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
        if (cpdBugsubtypeId != null) {
            query.setParameter(MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.BUDGETSUB_TYPE_ID,
            		cpdBugsubtypeId);
        }
        
        if (fieldId != null) {
            query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.FIELD_ID,
            		fieldId);
        }

        List<Object[]> result = null;
        result = query.getResultList();
        return result;

	}
}
