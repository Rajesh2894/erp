package com.abm.mainet.account.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.account.constants.AccountMasterQueryConstant;
import com.abm.mainet.account.domain.AccountBankDepositeSlipLedgerEntity;
import com.abm.mainet.account.domain.AccountBankDepositeSlipMasterEntity;
import com.abm.mainet.account.dto.AccountChequeOrCashDepositeBean;
import com.abm.mainet.account.dto.VoucherReversalDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountChequeOrCashDeposite;
import com.abm.mainet.common.constant.QueryConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.integration.acccount.domain.AccountHeadSecondaryAccountCodeMasterEntity;

/**
 * @author prasant.sahu
 *
 */
@Repository
public class AccountChequeOrCashDepositeDaoImpl extends AbstractDAO<AccountBankDepositeSlipMasterEntity>
        implements AccountChequeOrCashDepositeDao {

    private static final Logger LOGGER = Logger.getLogger(AccountChequeOrCashDepositeDaoImpl.class);

    @Override
    public List<Object[]> getReceiptDetails(final AccountChequeOrCashDepositeBean bean2) throws ParseException {
        final String feeMode = bean2.getSfeeMode().replace(MainetConstants.operator.COMMA, MainetConstants.BLANK);
        Date frmDate = null;
        Date tDate = null;
        if ((bean2.getSfromDate() != null) && (bean2.getStoDate() != null)) {
            final SimpleDateFormat formatter = new SimpleDateFormat(MainetConstants.CommonConstants.D_FORMATE);
            frmDate = formatter.parse(bean2.getSfromDate());
            tDate = formatter.parse(bean2.getStoDate());
        }
        // DEPOSITE_SLIP
        final StringBuilder queryString = new StringBuilder(
                QueryConstants.MASTERS.DEPOSITE_SLIP.DEPOSITE_SLIP_RECEIPT);
        if ((bean2.getDepartment() != null) && (bean2.getDepartment() > 0)) {
            queryString.append(" and sm.dpDeptId=:dpDeptId ");
        }

        if ((bean2.getFundId() != null) && (bean2.getFundId() > 0)) {
            queryString.append(" and fd.tbAcFundMaster.fundId=:fundId ");
        }
        if ((bean2.getFieldId() != null) && (bean2.getFieldId() > 0)) {
            queryString.append(" and fd.tbAcFieldMaster.fieldId=:fieldId");
        }
        queryString.append(" group by sm.rmRcptid,sm.rmRcptno,sm.rmDate,sm.dpDeptId");
        final Query query = entityManager.createQuery(queryString.toString());
        query.setParameter(AccountChequeOrCashDeposite.CPD_MODE, Long.valueOf(feeMode));
        query.setParameter(MainetConstants.FROM_DATE, frmDate);
        query.setParameter(MainetConstants.TO_DATE, tDate);
        if ((bean2.getDepartment() != null) && (bean2.getDepartment() > 0)) {
            query.setParameter(AccountChequeOrCashDeposite.DP_DEPT_ID, bean2.getDepartment());
        }

        if ((bean2.getFundId() != null) && (bean2.getFundId() > 0)) {
            query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.FUND_ID, bean2.getFundId());
        }

        if ((bean2.getFieldId() != null) && (bean2.getFieldId() > 0)) {
            query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.FIELD_ID, bean2.getFieldId());
        }

        @SuppressWarnings("unchecked")
        final List<Object[]> receiptDetails = query.getResultList();
        return receiptDetails;
    }

    @Override
    public List<Object[]> getReceiptDetailsView(final AccountChequeOrCashDepositeBean bean2) {
        final String feeMode = bean2.getSfeeMode().replace(MainetConstants.operator.COMMA, MainetConstants.BLANK);
        final StringBuilder queryString = new StringBuilder(
                QueryConstants.MASTERS.DEPOSITE_SLIP.DEPOSITE_SLIP_RECEIPT_VIEW);
        queryString.append(" group by sm.rmRcptid,sm.rmRcptno,sm.rmDate,sm.dpDeptId");
        final Query query = entityManager.createQuery(queryString.toString());
        query.setParameter(AccountChequeOrCashDeposite.CPD_MODE, Long.valueOf(feeMode));
        query.setParameter(AccountChequeOrCashDeposite.DEPOSIT_SLIP_ID, bean2.getDepositeSlipId());
        @SuppressWarnings("unchecked")
        final List<Object[]> receiptList = query.getResultList();
        return receiptList;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.account.dao.AccountChequeOrCashDepositeDao#getSavedReceiptDetails(java.lang.String,
     * java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<AccountBankDepositeSlipMasterEntity> getSavedReceiptDetails(final String fromDate, final String toDate,
            final String feeMode,
            final Long orgId, final String account, final String slipNumber) throws ParseException {

        final StringBuilder queryString = new StringBuilder();
        queryString.append(QueryConstants.MASTERS.DEPOSITE_SLIP.QUERY_TO_SELECT_SLIP_MASTER);
        if ((fromDate != null) && !fromDate.equals(MainetConstants.BLANK) && (toDate != null)
                && !toDate.equals(MainetConstants.BLANK)) {
            queryString.append(" and depSlipMaster.depositeSlipDate between :fromDate and :toDate ");
        }
        if ((feeMode != null) && !feeMode.equals(MainetConstants.BLANK)) {
            queryString.append(" and depSlipMaster.depositeTypeFlag =:feeMode");
        }

        if ((account != null) && !account.equals(MainetConstants.BLANK)) {
            queryString.append(" and depSlipMaster.depositeBAAccountId =:accountId");
        }
        if ((slipNumber != null) && !slipNumber.equals(MainetConstants.BLANK)) {
            queryString.append(" and depSlipMaster.depositeSlipNumber =:slipNo");
        }
        queryString.append(" order by 1 desc");

        final Query query = createQuery(queryString.toString());
        query.setParameter(MainetConstants.MobileCommon.ORGID, orgId);
        if ((fromDate != null) && !fromDate.equals(MainetConstants.BLANK) && (toDate != null)
                && !toDate.equals(MainetConstants.BLANK)) {
            final SimpleDateFormat formatter = new SimpleDateFormat(MainetConstants.CommonConstants.D_FORMATE);
            Date frmDate = null;
            Date tDate = null;
            frmDate = formatter.parse(fromDate);
            tDate = formatter.parse(toDate);
            query.setParameter(MainetConstants.FROM_DATE, frmDate, TemporalType.TIMESTAMP);
            query.setParameter(MainetConstants.TO_DATE, tDate, TemporalType.TIMESTAMP);
        }
        if ((feeMode != null) && !feeMode.equals(MainetConstants.BLANK)) {
            query.setParameter(AccountChequeOrCashDeposite.FEE_MODE, feeMode);
        }

        if ((account != null) && !account.equals(MainetConstants.BLANK)) {
            query.setParameter(AccountChequeOrCashDeposite.ACCOUNT_ID, Long.valueOf(account));
        }
        if ((slipNumber != null) && !slipNumber.equals(MainetConstants.BLANK)) {
            query.setParameter(AccountChequeOrCashDeposite.SLIP_NO, slipNumber);
        }
        return query.getResultList();

    }

    /*
     * (non-Javadoc)
     * @see
     * com.abm.mainetservice.account.dao.AccountChequeOrCashDepositeDao#saveDepositeSlipMasterEntity(com.abm.mainetservice.account
     * .entity.AccountBankDepositeSlipMasterEntity)
     */
    @Override
    public AccountBankDepositeSlipMasterEntity saveDepositeSlipMasterEntity(
            final AccountBankDepositeSlipMasterEntity depositeSlipMasterEntity) {
        entityManager.persist(depositeSlipMasterEntity);
        entityManager.flush();
        return depositeSlipMasterEntity;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.account.dao.AccountChequeOrCashDepositeDao#saveDenominationEntity(java.lang.Long)
     */
    @Override
    public AccountBankDepositeSlipMasterEntity saveDenominationEntity(final Long depositeSlipId) {
        final Query query = createQuery(QueryConstants.MASTERS.DEPOSITE_SLIP.QUERY_TO_SELECT_DEP_SLIP_MASTER);
        query.setParameter(AccountChequeOrCashDeposite.SLIP_ID, depositeSlipId);
        AccountBankDepositeSlipMasterEntity accountBankDepositeSlipMasterEntity = null;
        try {
            accountBankDepositeSlipMasterEntity = (AccountBankDepositeSlipMasterEntity) query.getSingleResult();
        } catch (final Exception e) {
            LOGGER.error("No record found from table TB_AC_BANK_DEPOSITSLIP_MASTER for provided input Params[depositeSlipId="
                    + depositeSlipId + "]", e);
        }
        return accountBankDepositeSlipMasterEntity;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.account.dao.AccountChequeOrCashDepositeDao#getLedgertDetails(java.util.Date, java.util.Date,
     * java.lang.String)
     */
    @Override
    public List<Object[]> getLedgertDetails(final Date fromDate, final Date toDate, final Long depositeType, final Long fundId,
            final Long fieldId,
            final Long department, Long orgId,Long functionId) {

        final StringBuilder queryString = new StringBuilder();

        queryString.append("select sum(fd.RF_FEEAMOUNT), group_concat(fd.RF_FEEID) ,fd.SAC_HEAD_ID");
        queryString.append(" from TB_RECEIPT_MAS sm,TB_RECEIPT_MODE md,TB_RECEIPT_DET fd,TB_AC_SECONDARYHEAD_MASTER sa,TB_AC_FUNCTION_MASTER fm");
        queryString.append(
                " where sm.RM_RCPTID = md.RM_RCPTID and sm.ORGID = md.ORGID and sm.RM_RCPTID = fd.RM_RCPTID and sm.ORGID = fd.ORGID and fd.SAC_HEAD_ID=sa.SAC_HEAD_ID and sa.FUNCTION_ID=fm.FUNCTION_ID and sm.RECEIPT_DEL_FLAG IS NULL");
        queryString.append(" and md.CPD_FEEMODE=:cpdMode and fd.DPS_SLIPID is null ");
        if ((fromDate != null) && (toDate != null)) {
            queryString.append(" AND sm.RM_DATE between :fromDate and :toDate ");
        }
		/*
		 * if ((fundId != null) && (fundId > 0)) {
		 * queryString.append(" and fd.tbAcFundMaster.fundId=:fundId "); }
		 */
        if ((fieldId != null) && (fieldId > 0)) {
            queryString.append(" and sm.FIELD_ID=:fieldId ");
        }
        if ((department != null) && (department > 0)) {
            queryString.append(" and sm.DP_DEPTID=:dpDeptId ");
        }
        if ((orgId != null) && (orgId > 0)) {
            queryString.append(" and sm.ORGID =:orgId ");
        }
        if(functionId!=null && functionId>0) {
        	queryString.append(" and fm.FUNCTION_ID =:functionId ");
        }
    
        queryString.append(" group by fd.SAC_HEAD_ID");

        final Query query = createNativeQuery(queryString.toString());
        query.setParameter(AccountChequeOrCashDeposite.CPD_MODE, depositeType);
        if ((fromDate != null) && (toDate != null)) {
            query.setParameter(MainetConstants.FROM_DATE, fromDate);
            query.setParameter(MainetConstants.TO_DATE, toDate);
        }

        if ((fundId != null) && (fundId > 0)) {
            query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.FUND_ID, fundId);
        }
        if ((fieldId != null) && (fieldId > 0)) {
            query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.FIELD_ID, fieldId);
        }
        if ((department != null) && (department > 0)) {
            query.setParameter(AccountChequeOrCashDeposite.DP_DEPT_ID, department);
        }
        if ((orgId != null) && (orgId > 0)) {
            query.setParameter(MainetConstants.Common_Constant.ORGID, orgId);
        }
        if(functionId!=null && functionId>0) {
        	 query.setParameter("functionId", functionId);
        }
        @SuppressWarnings("unchecked")
        final List<Object[]> listOfObjArray = query.getResultList();
        final List<Object[]> listOfObjArrayFinal = getLedgerWiseData(listOfObjArray);
        return listOfObjArrayFinal;

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.account.dao.AccountChequeOrCashDepositeDao#getDraweeBankDetails(java.util.Date, java.util.Date,
     * java.lang.Long)
     */
    @Override
    public List<Object[]> getDraweeBankDetails(final Date fromDate, final Date toDate, final Long orgId, final Long fundId,
            final Long fieldId) {

        final StringBuilder queryString = new StringBuilder();
        queryString.append(QueryConstants.MASTERS.ChequeOrCashDepositeMaster.QUERY_TO_SELECT_BANK_DETAIL);

        if ((fieldId != null) && (fieldId > 0)) {
            queryString.append(" and det.tbAcFieldMaster.fieldId=:fieldId ");
        }
        final Query query = createQuery(queryString.toString());
        query.setParameter(MainetConstants.FROM_DATE, fromDate);
        query.setParameter(MainetConstants.TO_DATE, toDate);
        query.setParameter(MainetConstants.MobileCommon.ORGID, orgId);

        if ((fieldId != null) && (fieldId > 0)) {
            query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.FIELD_ID, fieldId);
        }
        @SuppressWarnings("unchecked")
        final List<Object[]> listOfObjArr = query.getResultList();
        return listOfObjArr;
    }

    @Override
    public List<Object[]> getDraweeBankDetailsView(final Long depositeSlipId, final Long orgId, final Character coTypeFlag,
            final String depositeType) {

        String queryString = null;
        if (coTypeFlag == null) {
            if (depositeType.equals(MainetConstants.AccountReceiptEntry.RT) || depositeType.equals(MainetConstants.MENU.N)
                    || depositeType.equals(MainetConstants.FlagB)) {

                queryString = QueryConstants.MASTERS.ChequeOrCashDepositeMaster.QUERY_TO_SELECT_DRAWEE_BANK_DETAIL;
            } else {

                queryString = QueryConstants.MASTERS.ChequeOrCashDepositeMaster.QUERY_TO_SELECT_DRAWEE_BANK_DETAIL_VIEW;
            }
        } else if (!coTypeFlag.equals(MainetConstants.AccountContraVoucherEntry.T)
                && !coTypeFlag.equals(MainetConstants.AccountContraVoucherEntry.W)
                && !coTypeFlag.equals(MainetConstants.AccountContraVoucherEntry.D)) {
            if (depositeType.equals(MainetConstants.AccountReceiptEntry.RT) || depositeType.equals(MainetConstants.MENU.N)
                    || depositeType.equals(MainetConstants.FlagB)) {

                queryString = QueryConstants.MASTERS.ChequeOrCashDepositeMaster.QUERY_TO_SELECT_DRAWEE_BANK_DETAIL;
            } else {

                queryString = QueryConstants.MASTERS.ChequeOrCashDepositeMaster.QUERY_TO_SELECT_DRAWEE_BANK_DETAIL_VIEW;
            }
        } else {
            queryString = AccountMasterQueryConstant.QUERY_TO_GET_DRAWEE_BANK_DETAIL_VIEW;
        }
        final Query query = createQuery(queryString);
        query.setParameter(AccountChequeOrCashDeposite.DEPOSITE_SLIP_ID, depositeSlipId);
        @SuppressWarnings("unchecked")
        final List<Object[]> listOfObjArr = query.getResultList();

        return listOfObjArr;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.account.dao.AccountChequeOrCashDepositeDao#getChequeOrDDDetails(java.util.Date, java.util.Date,
     * java.lang.Long, java.lang.Long, java.lang.Long)
     */
    @Override
    public List<Object[]> getChequeOrDDDetails(final Date fromDate, final Date toDate, final Long mode, final Long fieldId,
            final Long orgId,final Date rmDate,Long deptId,Long functionId) {

        final StringBuilder stringQuery = new StringBuilder();
        stringQuery.append(QueryConstants.MASTERS.ChequeOrCashDepositeMaster.QUERY_TO_SELECT_CHEQUE_DD_DETAIL);
        if ((fromDate != null) && (toDate != null)) {
            stringQuery.append(" AND a.rmDate between :fromDate and :toDate ");
        }
        if(rmDate!=null) {
        	 stringQuery.append(" AND a.rmDate =:rmDate ");
        }

        if ((fieldId != null) && (fieldId > 0)) {
            stringQuery.append(" AND a.fieldId = :fieldId ");
        }

        if ((orgId != null) && (orgId > 0)) {
            stringQuery.append("AND a.orgId = :orgId ");
        }
        if ((functionId != null) && (functionId > 0)) {
            stringQuery.append("AND fm.functionId = :functionId ");
        }
        
        
        if(deptId!=null&&deptId>0) {
        	stringQuery.append("AND a.dpDeptId = :dpDeptId ");
        }

        stringQuery.append("order by a.rmDate,a.rmRcptno asc");

        final Query query = createQuery(stringQuery.toString());
        if ((fromDate != null) && (toDate != null)) {
            query.setParameter(MainetConstants.FROM_DATE, fromDate);
            query.setParameter(MainetConstants.TO_DATE, toDate);
        }
        query.setParameter(MainetConstants.REQUIRED_PG_PARAM.MODE, mode);
        if ((fieldId != null) && (fieldId > 0)) {
            query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.FIELD_ID, fieldId);
        }
        if ((orgId != null) && (orgId > 0)) {
            query.setParameter(MainetConstants.Common_Constant.ORGID, orgId);
        }
        if(rmDate!=null) {
        	 query.setParameter("rmDate", rmDate);
       }
        if(deptId!=null&&deptId>0) {
        	 query.setParameter("dpDeptId", deptId);        }
        if ((functionId != null) && (functionId > 0)) {
        	query.setParameter("functionId", functionId);  
        }
        @SuppressWarnings("unchecked")
        final List<Object[]> listOfObjArr = query.getResultList();

        return listOfObjArr;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.account.dao.AccountChequeOrCashDepositeDao#getReceiptLedger()
     */

    @Override
    public List<AccountBankDepositeSlipLedgerEntity> getReceiptLedger(final Date fromDate, final Date toDate, final Long orgid) {
        final Query query = createQuery(
                QueryConstants.MASTERS.ChequeOrCashDepositeMaster.QUERY_TO_SELECT_RECEIPT_LEDGER);
        query.setParameter(MainetConstants.MobileCommon.ORGID, orgid);
        query.setParameter(MainetConstants.FROM_DATE, fromDate, TemporalType.TIMESTAMP);
        query.setParameter(MainetConstants.TO_DATE, toDate, TemporalType.TIMESTAMP);
        @SuppressWarnings("unchecked")
        final List<AccountBankDepositeSlipLedgerEntity> ledgerDetails = query
                .getResultList();
        return ledgerDetails;
    }

    /**
     * Checking Repeatation Of Ledger
     * @param obj
     * @param listOfObjArray
     * @return
     */
    int checkExistance(final Object budgetCode, final List<Object[]> listOfObjArray) {
        int count = 0;
        for (final Object[] obj : listOfObjArray) {
            if (budgetCode == obj[2]) {
                count++;
            }
        }
        return count;
    }

    /**
     * getting sum of Amount by Ledger
     * @param obj
     * @param listOfObjArray
     * @return
     */
    private Map<Double, String> getTotalAmount(final Object budgetCode, final List<Object[]> listOfObjArray) {

        Double amount = 0.0d;
        String receiptId = MainetConstants.BLANK;
        final Map<Double, String> amountReceiptMap = new HashMap<>();
        for (final Object[] obj : listOfObjArray) {

            if (budgetCode == obj[2]) {
                amount = amount + Double.valueOf(obj[0].toString());
                receiptId = receiptId + MainetConstants.operator.COMMA + obj[1];

            }

        }
        amountReceiptMap.put(amount, receiptId);
        return amountReceiptMap;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.abm.mainetservice.account.dao.AccountChequeOrCashDepositeDao#getLedgertDetailsView(com.abm.mainetservice.account.bean.
     * AccountChequeOrCashDepositeBean)
     */
    @Override
    public List<Object[]> getLedgertDetailsView(final AccountChequeOrCashDepositeBean bean) {

        final Query query = createQuery(
                QueryConstants.MASTERS.ChequeOrCashDepositeMaster.QUERY_TO_SELECT_LEDGER_DETAIL);
        query.setParameter(AccountChequeOrCashDeposite.CPD_MODE, Long.valueOf(bean.getSfeeMode()));
        query.setParameter(AccountChequeOrCashDeposite.DEPOSIT_SLIP_ID, bean.getDepositeSlipId());
        @SuppressWarnings("unchecked")
        final List<Object[]> listOfObjArray = query.getResultList();
        final List<Object[]> listOfObjArrayFinal = getLedgerWiseData(listOfObjArray);
        return listOfObjArrayFinal;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.abm.mainetservice.account.dao.AccountChequeOrCashDepositeDao#getLedgertDetailsView(com.abm.mainetservice.account.bean.
     * AccountChequeOrCashDepositeBean)
     */
    @Override
    public List<Object[]> getReceiptLedgertDetailsView(final AccountChequeOrCashDepositeBean bean) {

        final Query query = createQuery(
                QueryConstants.MASTERS.ChequeOrCashDepositeMaster.QUERY_TO_SELECT_RECEIPT_LEDGER_DETAIL);
        query.setParameter(AccountChequeOrCashDeposite.CPD_MODE, Long.valueOf(bean.getSfeeMode()));
        query.setParameter(AccountChequeOrCashDeposite.DEPOSIT_SLIP_ID, bean.getDepositeSlipId());
        @SuppressWarnings("unchecked")
        final List<Object[]> listOfObjArray = query.getResultList();
        // final List<Object[]> listOfObjArrayFinal = getLedgerWiseData(listOfObjArray);
        return listOfObjArray;
    }

    /**
     * @param listOfObjArray
     */
    private List<Object[]> getLedgerWiseData(final List<Object[]> listOfObjArray) {
        final List<Object[]> listOfObjArrayFinal = new ArrayList<>();
        int checkCount;
        for (final Object[] obj : listOfObjArray) {

            checkCount = checkExistance(obj[2], listOfObjArray);
            if (checkCount == 1) {
                listOfObjArrayFinal.add(obj);
            } else {

                final int finalCheck = checkExistance(obj[2], listOfObjArrayFinal);
                final Map<Double, String> amount = getTotalAmount(obj[2], listOfObjArray);
                if (finalCheck == 0) {
                    for (final Map.Entry<Double, String> entry : amount.entrySet()) {
                        obj[0] = entry.getKey();
                        obj[1] = entry.getValue();
                    }
                    listOfObjArrayFinal.add(obj);
                }
            }

        }
        for (final Object[] objArray : listOfObjArrayFinal) {
            if (objArray[2] != null) {
                final Query query1 = createQuery(
                        QueryConstants.MASTERS.ChequeOrCashDepositeMaster.QUERY_TO_SELECT_LEDGER_WISE_DATA);

                query1.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.SAC_HEAD_ID, Long.valueOf(objArray[2].toString()));
                AccountHeadSecondaryAccountCodeMasterEntity budget = null;
                try {
                    budget = (AccountHeadSecondaryAccountCodeMasterEntity) query1.getSingleResult();
                } catch (final Exception e) {
                    LOGGER.error("No record found from table for provided input Params[listOfObjArray=" + listOfObjArray + "]",
                            e);
                }
                objArray[2] = budget;
            }
        }
        return listOfObjArrayFinal;
    }

    @Override
    @Transactional
    public List<Object[]> getAllBankDepositSlipEntryData(final Long receiptModeId, final Long orgId) {
        final Query query = createQuery(
                QueryConstants.MASTERS.ChequeOrCashDepositeMaster.QUERY_TO_SELECT_BANK_DEPOSIT_SLIP_ENTRY);
        query.setParameter(MainetConstants.AccountChequeDishonour.RECEIPT_MODE_ID, receiptModeId);
        query.setParameter(MainetConstants.MobileCommon.ORGID, orgId);
        @SuppressWarnings("unchecked")
        final List<Object[]> depositSlipNoDate = query.getResultList();
        return depositSlipNoDate;
    }

    @Override
    @Transactional
    public List<Object[]> getAllReceiptEntryData(final Long receiptModeId, final Long orgId) {
        final Query query = createQuery(
                QueryConstants.MASTERS.ChequeOrCashDepositeMaster.QUERY_TO_SELECT_RECEIPT_ENTRY);
        query.setParameter(MainetConstants.AccountChequeDishonour.RECEIPT_MODE_ID, receiptModeId);
        query.setParameter(MainetConstants.MobileCommon.ORGID, orgId);
        @SuppressWarnings("unchecked")
        final List<Object[]> receiptNoDate = query.getResultList();
        return receiptNoDate;
    }

    @Override
    public void reverseDepositSlip(final VoucherReversalDTO dto, final Long transactionId, final Long orgId,
            final String ipMacAddress) {
        // REVERSE DEPOSIT SLIP
        final Query query = entityManager
                .createQuery(QueryConstants.MASTERS.ChequeOrCashDepositeMaster.QUERY_TO_UPDATE_DEPOSIT_SLIP);
        query.setParameter(AccountChequeOrCashDeposite.DPS_DEL_FLAG, MainetConstants.MENU.Y);
        query.setParameter(AccountChequeOrCashDeposite.DPS_DEL_ORDER_NO, dto.getApprovalOrderNo());
        query.setParameter(AccountChequeOrCashDeposite.DPS_DEL_AUTH_BY, dto.getApprovedBy());
        query.setParameter(AccountChequeOrCashDeposite.DPS_DEL_DATE, new Date());
        query.setParameter(AccountChequeOrCashDeposite.DPS_DEL_REMARK, dto.getNarration());
        query.setParameter(AccountChequeOrCashDeposite.UPDATED_BY, dto.getUpdatedBy());
        query.setParameter(AccountChequeOrCashDeposite.UPDATED_DATE, new Date());
        query.setParameter(MainetConstants.DEPENDENT_TABLE_COLUMN.LG_IP_MAC, ipMacAddress);
        query.setParameter(AccountChequeOrCashDeposite.DEPOSITE_SLIP_ID, transactionId);
        query.setParameter(MainetConstants.MobileCommon.ORGID, orgId);
        query.executeUpdate();

        // REMOVING DEPOSITNO REFERENCE FROM TB_RECEIPT_DET
        final Query query2 = entityManager
                .createQuery(QueryConstants.MASTERS.ChequeOrCashDepositeMaster.QUERY_TO_REMOVE_DEPOSIT_NO_REF);
        query2.setParameter(AccountChequeOrCashDeposite.DEPOSITE_SLIP_ID, transactionId);
        query2.setParameter(AccountChequeOrCashDeposite.DEPOSITE_NULL, null);
        query2.setParameter(MainetConstants.MobileCommon.ORGID, orgId);
        query2.executeUpdate();
    }

    @Override
    public List<Object[]> getReceiptLedgertDetails(Date fromDate, Date toDate, Long depositeType, Long fundId,
            Long fieldId, Long department, Long orgId,Long functionId) {
        // TODO Auto-generated method stub
        final StringBuilder queryString = new StringBuilder();

        queryString.append("select distinct sm.rmRcptid,sm.rmDate,sm.rmRcptno,sm.manualReceiptNo,sm.rmAmount,sm.dpDeptId");
        queryString.append(" from TbServiceReceiptMasEntity sm,TbSrcptModesDetEntity md,TbSrcptFeesDetEntity fd,AccountHeadSecondaryAccountCodeMasterEntity sa,AccountFunctionMasterEntity fm");
        queryString.append(
                " where sm.rmRcptid = md.rmRcptid.rmRcptid and sm.orgId = md.orgId and sm.rmRcptid = fd.rmRcptid.rmRcptid and sm.orgId = fd.orgId and fd.sacHeadId=sa.sacHeadId and sa.tbAcFunctionMaster.functionId=fm.functionId and sm.receiptDelFlag IS NULL");
        queryString.append(" and md.cpdFeemode=:cpdMode and fd.depositeSlipId is null ");
        if ((fromDate != null) && (toDate != null)) {
            queryString.append(" AND sm.rmDate between :fromDate and :toDate ");
        }
        if ((fundId != null) && (fundId > 0)) {
            queryString.append(" and fd.tbAcFundMaster.fundId=:fundId ");
        }
        if ((fieldId != null) && (fieldId > 0)) {
            queryString.append(" and sm.fieldId=:fieldId ");
        }
        if ((department != null) && (department > 0)) {
            queryString.append(" and sm.dpDeptId=:dpDeptId ");
        }
        if ((orgId != null) && (orgId > 0)) {
            queryString.append(" and sm.orgId =:orgId ");
        }
        if(functionId!=null && functionId>0) {
        	queryString.append(" and fm.functionId =:functionId ");
        }
        queryString.append("order by sm.rmDate,sm.rmRcptno asc");

        final Query query = createQuery(queryString.toString());
        query.setParameter(AccountChequeOrCashDeposite.CPD_MODE, depositeType);
        if ((fromDate != null) && (toDate != null)) {
            query.setParameter(MainetConstants.FROM_DATE, fromDate);
            query.setParameter(MainetConstants.TO_DATE, toDate);
        }

        if ((fundId != null) && (fundId > 0)) {
            query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.FUND_ID, fundId);
        }
        if ((fieldId != null) && (fieldId > 0)) {
            query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.FIELD_ID, fieldId);
        }
        if ((department != null) && (department > 0)) {
            query.setParameter(AccountChequeOrCashDeposite.DP_DEPT_ID, department);
        }
        if ((orgId != null) && (orgId > 0)) {
            query.setParameter(MainetConstants.Common_Constant.ORGID, orgId);
        }
        if(functionId!=null && functionId>0) {
        	query.setParameter("functionId", functionId);
        	queryString.append(" and fm.functionId =:functionId ");
        }
        @SuppressWarnings("unchecked")
        final List<Object[]> listOfObjArray = query.getResultList();
        // final List<Object[]> listOfObjArrayFinal = getLedgerWiseData(listOfObjArray);
        return listOfObjArray;
    }

}
