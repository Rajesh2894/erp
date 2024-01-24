package com.abm.mainet.account.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.account.domain.AccountDepositEntity;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountDeposit;
import com.abm.mainet.common.constant.QueryConstants;
import com.abm.mainet.common.dao.AbstractDAO;

@Repository
public class AccountDepositDaoImpl extends AbstractDAO<AccountDepositEntity> implements AccountDepositDao {

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<AccountDepositEntity> findByAllGridSearchData(final String depNo, final Long vmVendorid, final Long cpdDepositType,
            final Long sacHeadId, final Date date, final String depAmount, final Long orgId,Long deptId) {

        BigDecimal depAmt = null;
        if ((depAmount != null) && !depAmount.isEmpty()) {
            depAmt = new BigDecimal(depAmount);
        }
        String queryString = QueryConstants.MASTERS.AccountDepositMaster.QUERY_TO_SELECT_ACC_DEPOSIT_ENTITY;

        if (depNo != null && !depNo.isEmpty()) {
            queryString += " and de.depNo =:depNo";
        }
        if (vmVendorid != null) {
            queryString += " and de.tbVendormaster.vmVendorid =:vmVendorid";
        }
        if (cpdDepositType != null) {
            queryString += " and de.tbComparamDetEntity.cpdId =:cpdDepositType";
        }
        if (sacHeadId != null) {
            queryString += " and de.sacHeadId =:sacHeadId";
        }
        if (date != null) {
            queryString += " and de.depReceiptdt =:date";
        }
        if (depAmt != null) {
            queryString += " and de.depAmount =:depAmt";
        }
        
        if(deptId!=null) {
        	queryString += " and de.tbDepartment.dpDeptid =:deptId";
        }

        queryString += " order by 1 desc";

        final Query query = createQuery(queryString);

        query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.ORGID,
                orgId);

        if (depNo != null && !depNo.isEmpty()) {
            query.setParameter(AccountDeposit.DEPNO,
                    depNo);
        }
        if (vmVendorid != null) {
            query.setParameter(AccountDeposit.VM_VENDORID,
                    vmVendorid);
        }
        if (cpdDepositType != null) {
            query.setParameter(AccountDeposit.CPD_DEPOSIT_TYPE,
                    cpdDepositType);
        }
        if (sacHeadId != null) {
            query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.SAC_HEAD_ID,
                    sacHeadId);
        }
        if (date != null) {
            query.setParameter(MainetConstants.DATE,
                    date);
        }
        if (depAmt != null) {
            query.setParameter(AccountDeposit.DEP_AMT,
                    depAmt);
        }
        
        if(deptId!=null) {
        	query.setParameter("deptId",
        			deptId);
        }
        List<AccountDepositEntity> result = null;
        result = query.getResultList();
        return result;
    }
}
