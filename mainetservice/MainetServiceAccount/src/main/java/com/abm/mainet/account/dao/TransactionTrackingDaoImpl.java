
package com.abm.mainet.account.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.account.constants.AccountMasterQueryConstant;
import com.abm.mainet.account.domain.AccountVoucherEntryDetailsEntity;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.QueryConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;

/**
 * @author tejas.kotekar
 *
 */
@Repository
public class TransactionTrackingDaoImpl extends AbstractDAO<AccountVoucherEntryDetailsEntity> implements TransactionTrackingDao {

    Long getCountForPrimaryHead(final Long budgetCodeId, final Long orgId, final String compCode) {
        final StringBuilder builder = new StringBuilder();
        builder.append(QueryConstants.MASTERS.TransactionTrackingMaster.QUERY_TO_GET_COUNT_PRIMARY_HEAD);

        final Query query = entityManager.createQuery(builder.toString());

        if (budgetCodeId != null) {
            query.setParameter(MainetConstants.PaymentEntry.BUDGET_CODE_ID, budgetCodeId);
        }
        query.setParameter(MainetConstants.TABLE_COLUMN.ORGANISATION, orgId);
        query.setParameter(MainetConstants.COMP_CODE, compCode);
        final Long count = (Long) query.getSingleResult();
        return count;
    }

    @Override
    public List<Object[]> getTransactionDetails(final Long budgetCodeId, final Long orgId) {

        final Long countLiablility = getCountForPrimaryHead(budgetCodeId, orgId, MainetConstants.THREE_PERCENT);
        final List<Object[]> transactionList = new ArrayList<>();
        if (countLiablility > 0L) {
            final Object[] transactionDet = new Object[8];

            // Get Account code and account head
            final Object[] accountHead = getAccountHeadInfo(budgetCodeId, orgId);

            // Calculate opening balance
            final StringBuilder opnBalance = new StringBuilder();
            opnBalance.append(QueryConstants.MASTERS.TransactionTrackingMaster.QUERY_TO_GET_OPENING_BALANCE);
            final Query opnBalquery = entityManager.createQuery(opnBalance.toString());
            opnBalquery.setParameter(MainetConstants.PaymentEntry.BUDGET_CODE_ID, budgetCodeId);
            final Object[] openingBalance = (Object[]) opnBalquery.getSingleResult();

            // Credit sum
            final BigDecimal creditAmount = calculateCreditSum(budgetCodeId, orgId);
            // Debit sum
            final BigDecimal debitAmount = calculateDebitSum(budgetCodeId, orgId);

            // Calculate closing balance
            BigDecimal openingBal = null;
            BigDecimal closingBalance = null;
            if ((openingBalance[0] != null) && !openingBalance[0].equals(MainetConstants.CommonConstant.BLANK)) {
                final Double openingBalanceDbl = Double.valueOf((String) openingBalance[0]);
                openingBal = BigDecimal.valueOf(openingBalanceDbl);
                if ((creditAmount != null) && (debitAmount != null)) {
                    closingBalance = openingBal.add(creditAmount).subtract(debitAmount);
                }
            }

            transactionDet[0] = accountHead[0] + MainetConstants.HYPHEN + accountHead[1];
            transactionDet[1] = accountHead[2];
            transactionDet[2] = openingBal;
            transactionDet[4] = openingBalance[1];
            transactionDet[3] = debitAmount;
            transactionDet[5] = creditAmount;
            transactionDet[7] = closingBalance;
            transactionList.add(transactionDet);
        }

        final Long countAsset = getCountForPrimaryHead(budgetCodeId, orgId, MainetConstants.FOUR_PERCENT);
        if (countAsset > 0L) {
            final Object[] transactionDet = new Object[8];

            // Get Account code and account head
            final Object[] accountHead = getAccountHeadInfo(budgetCodeId, orgId);

            // Calculate opening balance
            final StringBuilder opnBalance = new StringBuilder();
            opnBalance.append(QueryConstants.MASTERS.TransactionTrackingMaster.QUERY_TO_GET_OPENING_BALANCE);
            final Query opnBalquery = entityManager.createQuery(opnBalance.toString());
            opnBalquery.setParameter(MainetConstants.PaymentEntry.BUDGET_CODE_ID, budgetCodeId);
            final Object[] openingBalance = (Object[]) opnBalquery.getSingleResult();

            // Credit sum
            final BigDecimal creditAmount = calculateCreditSum(budgetCodeId, orgId);
            // Debit sum
            final BigDecimal debitAmount = calculateDebitSum(budgetCodeId, orgId);

            // Calculate closing balance
            BigDecimal openingBal = null;
            BigDecimal closingBalance = null;
            if ((openingBalance[0] != null) && !openingBalance[0].equals(MainetConstants.CommonConstant.BLANK)) {
                final Double openingBalanceDbl = Double.valueOf((String) openingBalance[0]);
                openingBal = BigDecimal.valueOf(openingBalanceDbl);
                closingBalance = openingBal.add(debitAmount).subtract(creditAmount);
            }

            transactionDet[0] = accountHead[0] + MainetConstants.HYPHEN + accountHead[1];
            transactionDet[1] = accountHead[2];
            transactionDet[2] = openingBal;
            transactionDet[3] = debitAmount;
            transactionDet[4] = openingBalance[1];
            transactionDet[5] = creditAmount;
            transactionDet[7] = closingBalance;
            transactionList.add(transactionDet);
        }

        final Long countIncome = getCountForPrimaryHead(budgetCodeId, orgId, MainetConstants.ONE_PERCENT);
        if (countIncome > 0L) {
            final Object[] transactionDet = new Object[8];

            // Get Account code and account head
            final Object[] accountHead = getAccountHeadInfo(budgetCodeId, orgId);

            // Credit sum
            final BigDecimal creditAmount = calculateCreditSum(budgetCodeId, orgId);
            // Debit sum
            final BigDecimal debitAmount = calculateDebitSum(budgetCodeId, orgId);

            transactionDet[0] = accountHead[0] + MainetConstants.HYPHEN + accountHead[1];
            transactionDet[1] = accountHead[2];
            transactionDet[3] = debitAmount;
            transactionDet[5] = creditAmount;
            transactionList.add(transactionDet);
        }
        final Long countExpenditure = getCountForPrimaryHead(budgetCodeId, orgId, MainetConstants.TWO_PERCENT);
        if (countExpenditure > 0L) {
            final Object[] transactionDet = new Object[8];

            // Get Account code and account head
            final Object[] accountHead = getAccountHeadInfo(budgetCodeId, orgId);
            // Credit sum
            final BigDecimal creditAmount = calculateCreditSum(budgetCodeId, orgId);
            // Debit sum
            final BigDecimal debitAmount = calculateDebitSum(budgetCodeId, orgId);

            transactionDet[0] = accountHead[0] + MainetConstants.HYPHEN + accountHead[1];
            transactionDet[1] = accountHead[2];
            transactionDet[3] = debitAmount;
            transactionDet[5] = creditAmount;
            transactionList.add(transactionDet);
        }

        return transactionList;
    }

    private Object[] getAccountHeadInfo(final Long budgetCodeId, final Long orgId) {

        final StringBuilder accountHead = new StringBuilder();
        accountHead.append(QueryConstants.MASTERS.TransactionTrackingMaster.QUERY_TO_GET_ACCOUNT_HEAD_INFO);
        final Query accountHeadQuery = entityManager.createQuery(accountHead.toString());
        accountHeadQuery.setParameter(MainetConstants.TABLE_COLUMN.ORGANISATION, orgId);
        accountHeadQuery.setParameter(MainetConstants.PaymentEntry.BUDGET_CODE_ID, budgetCodeId);
        final Object[] accountHeadResult = (Object[]) accountHeadQuery.getSingleResult();
        return accountHeadResult;
    }

    private BigDecimal calculateDebitSum(final Long budgetCodeId, final Long orgId) {

        final StringBuilder debitSum = new StringBuilder();
        debitSum.append(QueryConstants.MASTERS.TransactionTrackingMaster.QUERY_TO_GET_CALCULATED_DEBIT_SUM);

        final Query debitSumQuery = entityManager.createQuery(debitSum.toString());
        debitSumQuery.setParameter(MainetConstants.PaymentEntry.BUDGET_CODE_ID, budgetCodeId);
        final Long drId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.DR.toString(), PrefixConstants.DCR,
                orgId);
        debitSumQuery.setParameter(MainetConstants.DR_ID, drId);
        debitSumQuery.setParameter(MainetConstants.TABLE_COLUMN.ORGANISATION, orgId);
        final BigDecimal debitAmount = (BigDecimal) debitSumQuery.getSingleResult();
        return debitAmount;
    }

    private BigDecimal calculateCreditSum(final Long budgetCodeId, final Long orgId) {

        final StringBuilder creditSum = new StringBuilder();
        creditSum.append(QueryConstants.MASTERS.TransactionTrackingMaster.QUERY_TO_GET_CALCULATED_CREDIT_SUM);
        final Query creditSumQuery = entityManager.createQuery(creditSum.toString());
        creditSumQuery.setParameter(MainetConstants.PaymentEntry.BUDGET_CODE_ID, budgetCodeId);
        final Long crId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.CR.toString(), PrefixConstants.DCR,
                orgId);
        creditSumQuery.setParameter(MainetConstants.CR_ID, crId);
        creditSumQuery.setParameter(MainetConstants.TABLE_COLUMN.ORGANISATION, orgId);
        final BigDecimal creditAmount = (BigDecimal) creditSumQuery.getSingleResult();
        return creditAmount;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> getMonthWiseTransactionDetails(final Long budgetCodeId, final Long orgId, final Date fromDate,
            final Date toDate,
            final Long finYearId, final Organisation organisation) {

        final Long countLiablility = getCountForPrimaryHead(budgetCodeId, orgId, MainetConstants.THREE_PERCENT);

        new ArrayList<Object[]>();
        if (countLiablility > 0L) {

            final StringBuilder opnBalance = new StringBuilder();
            opnBalance.append(
                    QueryConstants.MASTERS.TransactionTrackingMaster.QUERY_TO_GET_MONTHWISE_TRANSACTION_DETAIL);
            final Query opnBalquery = entityManager.createQuery(opnBalance.toString());
            opnBalquery.setParameter(MainetConstants.PaymentEntry.BUDGET_CODE_ID, budgetCodeId);
            opnBalquery.setParameter(MainetConstants.FIN_YEAR_ID, finYearId);
            final Object[] openingBalance = (Object[]) opnBalquery.getSingleResult();
            int count = 1;
            // Get month wise details
            final List<LookUp> monthLookupList = CommonMasterUtility
                    .findFinancialYearWiseMonthList(MainetConstants.Common_Constant.WEEK.MON, organisation);
            if ((monthLookupList != null) && !monthLookupList.isEmpty()) {
                for (final LookUp monthLookup : monthLookupList) {

                    final StringBuilder monthWiseDetails = new StringBuilder();
                    monthWiseDetails.append(
                            QueryConstants.MASTERS.TransactionTrackingMaster.QUERY_TO_GET_MONTHWISE_DETAIL_OPEN_BALANCE_ONE);

                    final Query monthWiseDetailsQuery = entityManager.createQuery(monthWiseDetails.toString());
                    monthWiseDetailsQuery.setParameter(MainetConstants.PaymentEntry.BUDGET_CODE_ID, budgetCodeId);
                    final Long crId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.CR.toString(),
                            PrefixConstants.DCR, orgId);
                    monthWiseDetailsQuery.setParameter(MainetConstants.CR_ID, crId);
                    final Long drId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.DR.toString(),
                            PrefixConstants.DCR, orgId);
                    monthWiseDetailsQuery.setParameter(MainetConstants.DR_ID, drId);
                    monthWiseDetailsQuery.setParameter(MainetConstants.TABLE_COLUMN.ORGANISATION, orgId);
                    monthWiseDetailsQuery.setParameter(MainetConstants.FROM_DATE, fromDate);
                    monthWiseDetailsQuery.setParameter(MainetConstants.TO_DATE, toDate);
                    final List<Object[]> monthWiseDetailsResult = monthWiseDetailsQuery.getResultList();
                    if (count > 1) {

                    }
                    final Double openingBalanceDbl = Double.valueOf((String) openingBalance[0]);
                    final BigDecimal openingBal = BigDecimal.valueOf(openingBalanceDbl);
                    for (final Object[] details : monthWiseDetailsResult) {
                        openingBal.add((BigDecimal) details[3]);

                    }

                    count++;
                }

            }

        }

        final Long countAsset = getCountForPrimaryHead(budgetCodeId, orgId, MainetConstants.FOUR_PERCENT);
        if (countAsset > 0L) {
            // Calculate opening balance
            final StringBuilder opnBalance = new StringBuilder();
            opnBalance.append(AccountMasterQueryConstant.QUERY_TO_GET_CALCULATED_OPEN_BALANCE);
            final Query opnBalquery = entityManager.createQuery(opnBalance.toString());
            opnBalquery.setParameter(MainetConstants.PaymentEntry.BUDGET_CODE_ID, budgetCodeId);
            opnBalquery.setParameter(MainetConstants.FIN_YEAR_ID, finYearId);
            final Object[] openingBalance = (Object[]) opnBalquery.getSingleResult();
            int count = 1;
            // Get month wise details
            final List<LookUp> monthLookupList = CommonMasterUtility
                    .findFinancialYearWiseMonthList(MainetConstants.Common_Constant.WEEK.MON, organisation);
            final StringBuilder monthWiseDetails = new StringBuilder();
            monthWiseDetails.append(
                    QueryConstants.MASTERS.TransactionTrackingMaster.QUERY_TO_GET_MONTHWISE_DETAIL_OPEN_BALANCE_TWO);

            final Query monthWiseDetailsQuery = entityManager.createQuery(monthWiseDetails.toString());
            monthWiseDetailsQuery.setParameter(MainetConstants.PaymentEntry.BUDGET_CODE_ID, budgetCodeId);
            final Long crId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.CR.toString(),
                    PrefixConstants.DCR,
                    orgId);
            monthWiseDetailsQuery.setParameter(MainetConstants.CR_ID, crId);
            final Long drId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.DR.toString(),
                    PrefixConstants.DCR,
                    orgId);
            monthWiseDetailsQuery.setParameter(MainetConstants.DR_ID, drId);
            monthWiseDetailsQuery.setParameter(MainetConstants.TABLE_COLUMN.ORGANISATION, orgId);
            monthWiseDetailsQuery.setParameter(MainetConstants.FROM_DATE, fromDate);
            monthWiseDetailsQuery.setParameter(MainetConstants.TO_DATE, toDate);
            final List<Object[]> monthWiseDetailsResult = monthWiseDetailsQuery.getResultList();
            final Double openingBalanceDbl = Double.valueOf((String) openingBalance[0]);
            final BigDecimal openingBal = BigDecimal.valueOf(openingBalanceDbl);
            for (final Object[] objects : monthWiseDetailsResult) {
                for (final LookUp monthLookup : monthLookupList) {
                    if (Integer.parseInt(monthLookup.getOtherField()) == (int) objects[4]) {
                        openingBal.add((BigDecimal) objects[2]);
                    }
                }
            }

            if ((monthLookupList != null) && !monthLookupList.isEmpty()) {
                for (final LookUp monthLookup : monthLookupList) {

                    if (count > 1) {

                    }

                    count++;
                }

            }

        }

        return null;

    }

}
