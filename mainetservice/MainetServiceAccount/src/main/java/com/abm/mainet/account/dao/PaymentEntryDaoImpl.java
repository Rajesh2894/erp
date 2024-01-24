
package com.abm.mainet.account.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.account.constants.AccountMasterQueryConstant;
import com.abm.mainet.account.domain.AccountPaymentDetEntity;
import com.abm.mainet.account.domain.AccountPaymentMasterEntity;
import com.abm.mainet.account.dto.VoucherReversalDTO;
import com.abm.mainet.account.repository.BillEntryRepository;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.PaymentEntry;
import com.abm.mainet.common.constant.QueryConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.utility.UtilityService;

/**
 * @author tejas.kotekar
 *
 */
@Repository
public class PaymentEntryDaoImpl extends AbstractDAO<AccountPaymentMasterEntity> implements PaymentEntryDao {

    @Resource
    private BillEntryRepository billEntryServiceJpaRepository;

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.account.dao.PaymentEntryDao#getPaymentDetails(java.lang.Long, java.lang.String, java.math.BigDecimal,
     * java.lang.Long, java.lang.Long, java.lang.String, java.lang.Long)
     */
    @Override
    public List<AccountPaymentMasterEntity> getPaymentDetails(final Long orgId, final String paymentEntryDate,
            final BigDecimal paymentAmount,
            final Long vendorId,
            final Long budgetCodeId, final String paymentNo, final Long baAccountid, final Long paymentTypeFlag) {

        final StringBuilder builder = new StringBuilder();
        builder.append(QueryConstants.MASTERS.PaymentEntryMaster.QUERY_TO_GET_PAYMENT_DETAIL);

        if ((paymentEntryDate != null) && !paymentEntryDate.equals(MainetConstants.BLANK)) {

            builder.append(" and m.paymentDate =:paymentEntryDate");
        }

        if ((paymentAmount != null) && !paymentAmount.equals(MainetConstants.BLANK)) {

            builder.append(" and m.paymentAmount =:paymentAmount");
        }
        if (vendorId != null) {

            builder.append(" and m.vmVendorId.vmVendorid =:vendorId");
        }

        if (budgetCodeId != null) {

            builder.append(
                    " and d.budgetCodeId =:budgetCodeId and m.paymentId = d.paymentMasterId.paymentId and m.orgId=d.orgId");

        }

        if ((paymentNo != null) && !paymentNo.equals(MainetConstants.BLANK)) {

            builder.append(" and m.paymentNo =:paymentNo");

        }
        if (baAccountid != null) {

            builder.append(" and m.baBankAccountId.baAccountId =:baAccountid");

        }
        builder.append(" order by m.paymentId desc");

        final Query query = entityManager.createQuery(builder.toString());

        if ((paymentEntryDate != null) && !paymentEntryDate.equals(MainetConstants.BLANK)) {

            query.setParameter(PaymentEntry.PAYMENT_ENTRY_DATE, UtilityService.convertStringDateToDateFormat(paymentEntryDate));
        }

        if ((paymentAmount != null) && !paymentAmount.equals(MainetConstants.BLANK)) {

            query.setParameter(PaymentEntry.PAYMENT_AMOUNT, paymentAmount);
        }
        if (vendorId != null) {

            query.setParameter(MainetConstants.AccountBillEntry.VENDOR_ID, vendorId);
        }

        if (budgetCodeId != null) {

            query.setParameter(PaymentEntry.BUDGET_CODE_ID, budgetCodeId);

        }

        if ((paymentNo != null) && !paymentNo.equals(MainetConstants.BLANK)) {

            query.setParameter(PaymentEntry.PAYMENT_NO, paymentNo);

        }
        if (baAccountid != null) {

            query.setParameter(PaymentEntry.BA_ACCOUNTID, baAccountid);
        }

        query.setParameter(MainetConstants.TABLE_COLUMN.ORGANISATION, orgId);
        query.setParameter(PaymentEntry.PAYMENT_TYPE_FLAG, paymentTypeFlag);

        @SuppressWarnings("unchecked")
        final List<AccountPaymentMasterEntity> resultList = query.getResultList();

        return resultList;

    }

    @Override
    public void reversePaymentEntry(final VoucherReversalDTO dto, final Long transactionId, final Long orgId,
            final String ipMacAddress) {
        // REVERSE Payment entry
        final Query query = entityManager
                .createQuery(QueryConstants.MASTERS.PaymentEntryMaster.QUERY_TO_REVERSE_PAYMENT_ENTRY);
        query.setParameter(PaymentEntry.PAYMENT_DELETION_FLAG, MainetConstants.MENU.Y);
        query.setParameter(PaymentEntry.PAYMENT_DELETION_ORDER_NO, dto.getApprovalOrderNo());
        query.setParameter(PaymentEntry.DELETION_AUTHORIZED_BY, dto.getApprovedBy());
        query.setParameter(PaymentEntry.PAYMENT_DELETION_DATE, new Date());
        query.setParameter(PaymentEntry.DELETION_REMARK, dto.getNarration());
        query.setParameter(MainetConstants.AccountBillEntry.UPDATED_BY, dto.getUpdatedBy());
        query.setParameter(MainetConstants.AccountChequeOrCashDeposite.UPDATED_DATE, new Date());
        query.setParameter(MainetConstants.DEPENDENT_TABLE_COLUMN.LG_IP_MAC, ipMacAddress);
        query.setParameter(PaymentEntry.PAYMENT_ID, transactionId);
        query.setParameter(MainetConstants.TABLE_COLUMN.ORGANISATION, orgId);
        query.setParameter(MainetConstants.AccountBillEntry.DELETION_POSTING_DATE, new Date());
        query.executeUpdate();

    }

    @SuppressWarnings("unchecked")
    @Override
    public void updateBillPaymentStatus(long paymentId, long orgId) {

        String queryString = "select pd from AccountPaymentDetEntity pd where pd.paymentMasterId.paymentId=:paymentId AND pd.orgId= :orgId";
        final Query query = createQuery(queryString);
        query.setParameter(PaymentEntry.PAYMENT_ID, paymentId);
        query.setParameter(MainetConstants.TABLE_COLUMN.ORGANISATION,
                orgId);
        List<AccountPaymentDetEntity> result = null;
        result = query.getResultList();

        BigDecimal paymentTotalAmt = BigDecimal.ZERO;
        for (AccountPaymentDetEntity accountPaymentDetEntity : result) {
            Long bchId = accountPaymentDetEntity.getBchIdExpenditure();
            BigDecimal paymentAmt = accountPaymentDetEntity.getPaymentAmt();
            paymentTotalAmt = accountPaymentDetEntity.getPaymentMasterId().getPaymentAmount();
            if (bchId != null && paymentAmt != null) {
                List<Object[]> expPaymnetAmt = billEntryServiceJpaRepository.getExpenditurePaymentAmt(bchId, orgId);
                for (Object[] objects : expPaymnetAmt) {
                    if (objects[0] != null && objects[1] != null) {
                        Long bchPrimaryKeyId = (Long) objects[0];
                        String paymentRemainingAmt = (String) objects[1];
                        BigDecimal paymentExpFinalAmt = new BigDecimal(paymentRemainingAmt).add(paymentAmt);
                        billEntryServiceJpaRepository.updateExpenditureBalanceAmt(bchPrimaryKeyId, paymentExpFinalAmt.toString());
                    }
                }
            }
        }

        final Query query1 = entityManager
                .createQuery(AccountMasterQueryConstant.MASTERS.PaymentEntryMaster.QUERY_TO_UPDATE_FLAG_BILLMASTER);
        query1.setParameter(PaymentEntry.PAYMENT_ID, paymentId);
        query1.setParameter(MainetConstants.TABLE_COLUMN.ORGANISATION,
                orgId);
        query1.setParameter("paymentTotalAmt",
                paymentTotalAmt);
        query1.executeUpdate();

    }

    @Override
    public List<AccountPaymentMasterEntity> getTdsPaymentDetails(Long orgId, String paymentEntryDate,
            BigDecimal paymentAmount, Long vendorId, Long budgetCodeId, String paymentNo, Long baAccountid,
            Long paymentTypeFlag) {

        final StringBuilder builder = new StringBuilder();
        builder.append(QueryConstants.MASTERS.PaymentEntryMaster.QUERY_TO_GET_PAYMENT_DETAIL);

        if ((paymentEntryDate != null) && !paymentEntryDate.equals(MainetConstants.BLANK)) {

            builder.append(" and m.paymentDate =:paymentEntryDate");
        }

        if ((paymentAmount != null) && !paymentAmount.equals(MainetConstants.BLANK)) {

            builder.append(" and m.paymentAmount =:paymentAmount");
        }
        if (vendorId != null) {

            builder.append(" and m.vmVendorId.vmVendorid =:vendorId");
        }

        if (budgetCodeId != null) {

            builder.append(
                    " and d.budgetCodeId =:budgetCodeId and m.paymentId = d.paymentMasterId.paymentId and m.orgId=d.orgId");

        }

        if ((paymentNo != null) && !paymentNo.equals(MainetConstants.BLANK)) {

            builder.append(" and m.paymentNo =:paymentNo");

        }
        if (baAccountid != null) {

            builder.append(" and m.baBankAccountId.baAccountId =:baAccountid");

        }
        builder.append(" order by m.paymentId desc");

        final Query query = entityManager.createQuery(builder.toString());

        if ((paymentEntryDate != null) && !paymentEntryDate.equals(MainetConstants.BLANK)) {

            query.setParameter(PaymentEntry.PAYMENT_ENTRY_DATE, UtilityService.convertStringDateToDateFormat(paymentEntryDate));
        }

        if ((paymentAmount != null) && !paymentAmount.equals(MainetConstants.BLANK)) {

            query.setParameter(PaymentEntry.PAYMENT_AMOUNT, paymentAmount);
        }
        if (vendorId != null) {

            query.setParameter(MainetConstants.AccountBillEntry.VENDOR_ID, vendorId);
        }

        if (budgetCodeId != null) {

            query.setParameter(PaymentEntry.BUDGET_CODE_ID, budgetCodeId);

        }

        if ((paymentNo != null) && !paymentNo.equals(MainetConstants.BLANK)) {

            query.setParameter(PaymentEntry.PAYMENT_NO, paymentNo);

        }
        if (baAccountid != null) {

            query.setParameter(PaymentEntry.BA_ACCOUNTID, baAccountid);
        }

        query.setParameter(MainetConstants.TABLE_COLUMN.ORGANISATION, orgId);
        query.setParameter(PaymentEntry.PAYMENT_TYPE_FLAG, paymentTypeFlag);

        @SuppressWarnings("unchecked")
        final List<AccountPaymentMasterEntity> resultList = query.getResultList();

        return resultList;

    }

}
