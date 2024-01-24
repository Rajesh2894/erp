package com.abm.mainet.account.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.account.domain.AccountBankDepositeSlipMasterEntity;
import com.abm.mainet.account.dto.BankReconciliationDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.BankReconciliation;
import com.abm.mainet.common.constant.QueryConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.utility.Utility;

@Repository
public class BankReconciliationDaoImpl extends AbstractDAO<AccountBankDepositeSlipMasterEntity>
        implements BankReconciliationDao {

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<Object[]> findByAllGridReceiptSearchData(final Long bankAccount, final String depositeTypeFlag,
            final Date fromDte, final Date toDte, final Long orgId,final Long catagoryStatus) {
        String queryString = QueryConstants.MASTERS.BankReconciliationMaster.QUERY_TO_GET_GRID_RECEIPT_SEARCH;
        
        if(StringUtils.isNotEmpty(depositeTypeFlag) && !depositeTypeFlag.equals(MainetConstants.FlagA)) {
        	queryString += " and bd.depositeTypeFlag =:depositeTypeFlag ";
        }
        
        if ((fromDte != null) && (toDte != null)) {
            queryString += " and bd.depositeSlipDate between :fromDte and :toDte";
        }
        
        if(catagoryStatus!=null) {
        	 queryString += " and tm.checkStatus =:status";
        }
        
        queryString += " order by bd.depositeSlipDate asc";

        final Query query = createQuery(queryString);

        query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.ORGID, orgId);
        if (bankAccount != null) {
            query.setParameter(MainetConstants.AccountChequeDishonour.BANK_ACCOUNT, bankAccount);
        }
        if (StringUtils.isNotEmpty(depositeTypeFlag) && !depositeTypeFlag.equals("A")) {
            query.setParameter(BankReconciliation.DEPOSITE_TYPE_FLAG, depositeTypeFlag);
        }
        if ((fromDte != null) && (toDte != null)) {
            query.setParameter(BankReconciliation.FROM_DTE, fromDte);
            query.setParameter(BankReconciliation.TO_DTE, toDte);
        }
        
         if(catagoryStatus!=null) {
        	 query.setParameter("status", catagoryStatus);
         }
        List<Object[]> result = null;
        result = query.getResultList();
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<Object[]> findByAllGridPaymentEntrySearchData(final Long bankAccount, final Long tranMode,
            final Date fromDte, final Date toDte, final Long orgId,Long catagoryStatus) {
        String queryString = QueryConstants.MASTERS.BankReconciliationMaster.QUERY_TO_GET_GRID_PAYMENT_ENTRY;
        
        if(tranMode!=null) {
        	queryString +=" and pm.paymentModeId.cpdId =:tranMode ";
        }
        
        if ((fromDte != null) && (toDte != null)) {
            queryString += " and pm.paymentDate between :fromDte and :toDte";
        }
        
        if(catagoryStatus!=null) {
        	 queryString += " and cb.cpdIdstatus=:status";
        }
        
        queryString += " order by pm.paymentDate asc ";

        final Query query = createQuery(queryString);

        query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.ORGID, orgId);
        if (bankAccount != null) {
            query.setParameter(MainetConstants.AccountChequeDishonour.BANK_ACCOUNT, bankAccount);
        }
        if (tranMode != null) {
            query.setParameter(BankReconciliation.TRAN_MODE, tranMode);
        }
        if ((fromDte != null) && (toDte != null)) {
            query.setParameter(BankReconciliation.FROM_DTE, fromDte);
            query.setParameter(BankReconciliation.TO_DTE, toDte);
        }
        
        if(catagoryStatus!=null) {
        	query.setParameter("status", catagoryStatus);
        }
        
        List<Object[]> result = null;
        result = query.getResultList();
        return result;
    }

    @Override
    @Transactional
    public void saveOrUpdateBankReconciliationFormReceiptData(final Long receiptModeId, final Date tranDate,
            final String receiptTypeFlag, final Long orgId, Long userId, Date lmoddate, String lgIpMac) {
        final Query query = entityManager
                .createQuery(buildDynamicSaveQuery(receiptModeId, tranDate, receiptTypeFlag, orgId, userId, lmoddate, lgIpMac));
        query.setParameter(MainetConstants.AccountChequeDishonour.RECEIPT_MODE_ID, receiptModeId);
        query.setParameter(BankReconciliation.TRAN_DATE, tranDate);
        query.setParameter(BankReconciliation.RECEIPT_TYPE_FLAG, Long.valueOf(receiptTypeFlag));
        query.setParameter(BankReconciliation.updatedBy, userId);
        query.setParameter(BankReconciliation.updatedDate, lmoddate);
        query.setParameter(BankReconciliation.updatedIpmacId, lgIpMac);
        query.setParameter(MainetConstants.TABLE_COLUMN.ORGANISATION, orgId);
        query.executeUpdate();
        return;
    }

    private String buildDynamicSaveQuery(final Long receiptModeId, final Date tranDate, final String receiptTypeFlag,
            final Long orgId, Long userId, Date lmoddate, String lgIpMac) {
        final StringBuilder builder = new StringBuilder();
        builder.append(QueryConstants.MASTERS.BankReconciliationMaster.QUERY_TO_UPDATE_RECEIPT_DATA);
        return builder.toString();
    }

    @SuppressWarnings("unchecked")
    @Transactional
    @Override
    public List<Object[]> findByAllSummarySearchData(Long bankAccount, BigDecimal amount, Long statusId,
            String chequeddno, Date fromDte, Date toDte, Long orgId) {
        Query jpaQuery = this.createQuery(buildQuery(bankAccount, amount, statusId, chequeddno, fromDte, toDte, orgId));
        jpaQuery.setParameter("orgId", orgId);
        if (null != bankAccount) {
            jpaQuery.setParameter(MainetConstants.AccountChequeDishonour.BANK_ACCOUNT, bankAccount);
        }
        if (null != amount) {
            jpaQuery.setParameter("amount", amount);
        }
        if (null != statusId) {
            jpaQuery.setParameter("statusId", statusId);
        }
        if (null != chequeddno) {
            jpaQuery.setParameter("chequeddno", chequeddno);
        }

        if ((fromDte != null) && (toDte != null)) {
            jpaQuery.setParameter(BankReconciliation.FROM_DTE, fromDte);
            jpaQuery.setParameter(BankReconciliation.TO_DTE, toDte);
        }
        List<Object[]> result = null;
        result = jpaQuery.getResultList();
        return result;
    }

    private String buildQuery(Long bankAccount, BigDecimal amount, Long statusId, String chequeddno, Date fromDte,
            Date toDte, Long orgId) {
        StringBuilder query = new StringBuilder(
                "select distinct pm.paymentId, pm.paymentDate, pm.paymentNo, cb.chequeNo, pm.instrumentDate, pm.paymentAmount, pm.paymentDeletionFlag, pm.chequeClearanceDate, pm.paymentModeId.cpdId, pm.paymentTypeFlag, cb.cpdIdstatus from AccountPaymentMasterEntity pm,TbAcChequebookleafDetEntity cb where pm.paymentId=cb.paymentId and pm.orgId =:orgId and pm.paymentId=cb.paymentId and pm.orgId=cb.orgid ");
        if (null != bankAccount) {
            query.append(" and pm.baBankAccountId.baAccountId =:bankAccount");
        }
        if (null != amount) {
            query.append(" and pm.paymentAmount=:amount");
        }
        if (null != statusId) {
            query.append(" and cb.cpdIdstatus=:statusId");
        }
        if (null != chequeddno) {
            query.append(" and cb.chequeNo=:chequeddno");
        }
        if ((fromDte != null) && (toDte != null)) {
            query.append(" and pm.paymentDate between :fromDte and :toDte");
        }
        query.append(" order by pm.paymentDate asc ");
        return query.toString();
    }

    @Transactional
    @Override
    public void saveOrUpdateBankDepositslipMasterData(BankReconciliationDTO bankReconciliationDTO) {
        // TODO Auto-generated method stub
        final Query query = entityManager.createQuery(
                buildBankDepositeUpdateQuery(bankReconciliationDTO.getDepositSlipId(), bankReconciliationDTO.getFlag(),
                        bankReconciliationDTO.getDate(), bankReconciliationDTO.getUserId(), bankReconciliationDTO.getLmoddate(),
                        bankReconciliationDTO.getLgIpMac(), bankReconciliationDTO.getOrgid()));
        query.setParameter("depositeSlipId", bankReconciliationDTO.getDepositSlipId());
        query.setParameter("depositeAuthFlag", bankReconciliationDTO.getFlag());
        query.setParameter("depositeAuthDate", Utility.stringToDate(bankReconciliationDTO.getDate()));
        query.setParameter("updatedBy", bankReconciliationDTO.getUserId());
        query.setParameter("updatedDate", bankReconciliationDTO.getLmoddate());
        query.setParameter("lgIpMacUpd", bankReconciliationDTO.getLgIpMac());
        query.setParameter("orgId", bankReconciliationDTO.getOrgid());
        query.executeUpdate();
    }

    private String buildBankDepositeUpdateQuery(Long depositeSlipId, String flag, String date,
            Long userId, Date lmoddate, String lgIpMac, Long orgid) {
        // TODO Auto-generated method stub
        final StringBuilder builder = new StringBuilder();
        builder.append(QueryConstants.MASTERS.BankReconciliationMaster.QUERY_TO_UPDATE_BANK_DEPOSITE_SLIP_DATA);
        return builder.toString();
    }
}
