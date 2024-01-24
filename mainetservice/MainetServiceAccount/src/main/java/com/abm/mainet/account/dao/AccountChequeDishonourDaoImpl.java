package com.abm.mainet.account.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountChequeDishonour;
import com.abm.mainet.common.constant.QueryConstants;
import com.abm.mainet.common.dao.AbstractDAO;

@Repository
public class AccountChequeDishonourDaoImpl extends AbstractDAO<T> implements AccountChequeDishonourDao {
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<Object[]> findByAllGridPayInSlipSearchData(final String number, final Date date, final BigDecimal amount,
            final Long bankAccount,
            final Long orgId) {

        final Query query = entityManager.createQuery(buildDynamicDataQuery(number, date, amount, bankAccount, orgId));

        if ((number != null) && (number !="")) {
            query.setParameter(AccountChequeDishonour.NUMBER2, number.toString());
        }
        if (date != null) {
            query.setParameter(MainetConstants.DATE, date);
        }
        if (amount != null) {
            query.setParameter(MainetConstants.BankParam.AMT, amount);
        }
        if ((bankAccount != null) && (bankAccount != 0l)) {
            query.setParameter(AccountChequeDishonour.BANK_ACCOUNT, bankAccount);
        }
        query.setParameter(MainetConstants.TABLE_COLUMN.ORGANISATION, orgId);
        return query.getResultList();
    }

    private String buildDynamicDataQuery(final String number, final Date date, final BigDecimal amount, final Long bankAccount,
            final Long orgId) {

        final StringBuilder builder = new StringBuilder();
        builder.append(QueryConstants.MASTERS.ChequeDishonour.QUERY_TO_SELECT_DIST_DEPOSIT_SLIP);

        if ((number != null) && (number !="")) {
            builder.append(" AND te.depositeSlipNumber=:number ");
        }
        if (date != null) {
            builder.append(" AND te.depositeSlipDate=:date ");
        }
        if (amount != null) {
            builder.append(" AND te.depositeAmount=:amount ");
        }
        if ((bankAccount != null) && (bankAccount != 0l)) {
            builder.append(" AND te.depositeBAAccountId=:bankAccount ");
        }
        builder.append(" order by 1 desc");

        return builder.toString();
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<Object[]> findByAllGridChequeDDNoSearchData(final Long number, final Date date, final BigDecimal amount,
            final Long bankAccount,
            final Long orgId) {

        final Query query = entityManager.createQuery(buildDynamicQuery(number, date, amount, bankAccount, orgId));

        if ((number != null) && (number !=0)) {
            query.setParameter(AccountChequeDishonour.NUMBER2, number);
        }
        if (date != null) {
            query.setParameter(MainetConstants.DATE, date);
        }
        if (amount != null) {
            query.setParameter(MainetConstants.BankParam.AMT, amount);
        }
        if ((bankAccount != null) && (bankAccount != 0l)) {
            query.setParameter(AccountChequeDishonour.BANK_ACCOUNT, bankAccount);
        }
        query.setParameter(MainetConstants.TABLE_COLUMN.ORGANISATION, orgId);
        return query.getResultList();
    }

    private String buildDynamicQuery(final Long number, final Date date, final BigDecimal amount, final Long bankAccount,
            final Long orgId) {

        final StringBuilder builder = new StringBuilder();
        builder.append(QueryConstants.MASTERS.ChequeDishonour.QUERY_TO_SELECT_DIST_DEPOSIT_SLIP_ID);

        if ((number != null) && (number !=0)) {
            builder.append(" AND tm.rdChequeddno=:number ");
        }
        if (date != null) {
            builder.append(" AND tm.rdChequedddate=:date ");
        }
        if (amount != null) {
            builder.append(" AND tm.rdAmount=:amount ");
        }
        if ((bankAccount != null) && (bankAccount != 0l)) {
            builder.append(" AND te.depositeBAAccountId=:bankAccount ");
        }
        builder.append(" order by 1 desc");
        return builder.toString();
    }

    @Override
    @Transactional
    public void saveAccountChequeDishonourFormData(
            final Long receiptModeId, final Date chequeDishonourDate, final Double chequeDisChgAmt, final String remarks,
            final String flag, final Long orgId) {
        final Query query = entityManager
                .createQuery(buildDynamicSaveQuery(receiptModeId, flag, chequeDishonourDate, chequeDisChgAmt, remarks, orgId));
        query.setParameter(AccountChequeDishonour.RECEIPT_MODE_ID, receiptModeId);
        query.setParameter(AccountChequeDishonour.FLAG2, Long.valueOf(flag));
        query.setParameter(AccountChequeDishonour.CHEQUE_DISHONOUR_DATE, chequeDishonourDate);
        query.setParameter(AccountChequeDishonour.CHEQUE_DIS_CHG_AMT, chequeDisChgAmt);
        query.setParameter(AccountChequeDishonour.REMARKS2, remarks);
        query.setParameter(MainetConstants.TABLE_COLUMN.ORGANISATION, orgId);
        query.executeUpdate();
        return;
    }

    private String buildDynamicSaveQuery(final Long receiptModeId, final String flag, final Date chequeDishonourDate,
            final Double chequeDisChgAmt,
            final String remarks, final Long orgId) {
        final StringBuilder builder = new StringBuilder();
        builder.append(QueryConstants.MASTERS.ChequeDishonour.QUERY_TO_UPDATE_SCRPT_MODE_ENTITY);
        return builder.toString();
    }

    @Override
    @Transactional
    public Long getTaxMasterEntryBudgetCodeId(final Long departmentId, final Long voucherSubTypeId, final Long orgId) {
        final Query query = entityManager
                .createQuery(QueryConstants.MASTERS.ChequeDishonour.QUERY_TO_SELECT_DIST_BUDGET_CODE);
        query.setParameter(MainetConstants.AccountBillEntry.DEPARTMENT_ID, departmentId);
        query.setParameter(AccountChequeDishonour.VOUCHER_SUB_TYPE_ID, voucherSubTypeId);
        query.setParameter(MainetConstants.TABLE_COLUMN.ORGANISATION, orgId);
        Long result = null;
        result = (Long) query.getSingleResult();
        return result;
    }

}
