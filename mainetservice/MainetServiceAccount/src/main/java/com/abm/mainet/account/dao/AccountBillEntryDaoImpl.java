package com.abm.mainet.account.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.account.domain.AccountBillEntryMasterEnitity;
import com.abm.mainet.account.dto.VoucherReversalDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountBillEntry;
import com.abm.mainet.common.constant.QueryConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.utility.UtilityService;

/**
 * @author tejas.kotekar
 *
 */
@Repository
public class AccountBillEntryDaoImpl extends AbstractDAO<AccountBillEntryMasterEnitity> implements AccountBillEntryDao {

    private static final Logger LOGGER = Logger.getLogger(AccountBillEntryDaoImpl.class);

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.account.dao.AccountBillEntryDao#getBillEntryDetails(java.lang.String, java.lang.String,
     * java.math.BigDecimal, java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<AccountBillEntryMasterEnitity> getBillEntryDetails(final Long orgId, final String fromDate, final String toDate,
            final String billNo,
            final Long billType, final Long vendorId, final Long departmentId) {

        final StringBuilder builder = new StringBuilder();
        builder.append(QueryConstants.MASTERS.AccountBillEntryMaster.QUERY_TO_BILL_ENTRY);

        if (((fromDate != null) && !fromDate.equals(MainetConstants.BLANK))
                || ((toDate != null) && !fromDate.equals(MainetConstants.BLANK))) {

            builder.append(" and m.billEntryDate between :fromDate and :toDate");

        }

        if ((billNo != null) && !billNo.equals(MainetConstants.BLANK)) {

            builder.append(" and m.billNo =:billNo");

        }

        if (billType != null) {

            builder.append(" and m.billTypeId.cpdId =:billType");

        }
        if (vendorId != null) {

            builder.append(" and m.vendorId.vmVendorid =:vendorId");

        }

        if (departmentId != null) {

            builder.append(" and m.departmentId.dpDeptid =:departmentId");

        }

        builder.append(" order by m.id desc");

        final Query query = entityManager.createQuery(builder.toString());

        if (((fromDate != null) && !fromDate.equals(MainetConstants.BLANK))
                || ((toDate != null) && !fromDate.equals(MainetConstants.BLANK))) {

            query.setParameter(MainetConstants.FROM_DATE, UtilityService.convertStringDateToDateFormat(fromDate));
            query.setParameter(MainetConstants.TO_DATE, UtilityService.convertStringDateToDateFormat(toDate));

        }
        if ((billNo != null) && !billNo.equals(MainetConstants.BLANK)) {

            query.setParameter(AccountBillEntry.BILL_NO, billNo);

        }
        if (billType != null) {

            query.setParameter(AccountBillEntry.BILL_TYPE, billType);

        }
        if (vendorId != null) {

            query.setParameter(AccountBillEntry.VENDOR_ID, vendorId);

        }
        if (departmentId != null) {

            query.setParameter(AccountBillEntry.DEPARTMENT_ID, departmentId);

        }
        query.setParameter(MainetConstants.TABLE_COLUMN.ORGANISATION, orgId);
        // @SuppressWarnings(AccountBillEntry.UNCHECKED)
        List<AccountBillEntryMasterEnitity> resultList = null;
        try {
            resultList = query.getResultList();
        } catch (final Exception ex) {
            LOGGER.error("Vendor Id is Not Available", ex);
        }
        return resultList;
    }

    @Override
    public void reverseBillInvoice(final VoucherReversalDTO dto, final Long transactionId, final Long orgId,
            final String ipMacAddress) {
        final Query query = entityManager
                .createQuery(QueryConstants.MASTERS.AccountBillEntryMaster.QUERY_TO_UPDATE_BILLENTRY_MASTER);
        query.setParameter(AccountBillEntry.BILL_DELETION_FLAG, MainetConstants.MENU.Y);
        query.setParameter(AccountBillEntry.BILL_DELETION_ORDER_NO, dto.getApprovalOrderNo());
        query.setParameter(AccountBillEntry.BILL_DELETION_AUTHORIZED_BY, dto.getApprovedBy());
        query.setParameter(AccountBillEntry.BILL_DELETION_DATE, new Date());
        query.setParameter(AccountBillEntry.BILL_DELETION_REMARK, dto.getNarration());
        query.setParameter(AccountBillEntry.UPDATED_BY, dto.getUpdatedBy());
        query.setParameter(AccountBillEntry.UPDATED_DATE, new Date());
        query.setParameter(AccountBillEntry.LG_IP_MAC_ADDRESS_UPDATED, ipMacAddress);
        query.setParameter(AccountBillEntry.ID, transactionId);
        query.setParameter(MainetConstants.TABLE_COLUMN.ORGANISATION, orgId);
        query.setParameter(AccountBillEntry.DELETION_POSTING_DATE, new Date());
        query.executeUpdate();

    }

    @Override
    @Transactional
    public boolean isPaymentDateisExists(Long orgid, Date paymentDate) {
        // TODO Auto-generated method stub
        String queryString = "select te.checkerDate from AccountBillEntryMasterEnitity te where ";
        if (orgid != null) {
            queryString += " te.orgId =:orgid ";
        }
        if ((paymentDate != null)) {
            queryString += " and te.checkerDate <= :paymentDate";
        }
        final Query query = createQuery(queryString);

        if (orgid != null) {
            query.setParameter("orgid",
                    orgid);
        }
        if (paymentDate != null) {
            query.setParameter("paymentDate",
                    paymentDate);
        }
        if (query.getResultList().isEmpty()) {
            return true;
        }
        return false;
    }

}
