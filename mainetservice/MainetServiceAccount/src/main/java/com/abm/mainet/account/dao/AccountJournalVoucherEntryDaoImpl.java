
package com.abm.mainet.account.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.account.domain.AccountVoucherEntryEntity;
import com.abm.mainet.account.repository.AccountVoucherEntryRepository;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountJournalVoucherEntry;
import com.abm.mainet.common.constant.QueryConstants;
import com.abm.mainet.common.dao.AbstractDAO;

/**
 * @author deepika.pimpale
 *
 */
@Repository
public class AccountJournalVoucherEntryDaoImpl extends AbstractDAO<AccountVoucherEntryEntity>
        implements AccountJournalVoucherEntryDao {

    @Resource
    private AccountVoucherEntryRepository accountVoucherEntryRepository;

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.account.dao.AccountJournalVoucherEntryDao# getSearchVoucherData(java.util.List)
     */
    @Override
    public List<Object[]> getSearchVoucherData(final Long voucherType, final Date frmDate, final Date toDate,
            final String dateType, final String authStatus, final BigDecimal amount, final Long org,
            final String refNo) {
        final StringBuilder builder = new StringBuilder();
        builder.append(QueryConstants.MASTERS.AccountJournalVoucherEntryMaster.QUERY_SEARCH_VOUCHER_DATA);
        if (MainetConstants.MENU.P.equals(dateType)) {
            builder.append(" details.master.vouPostingDate between :frmDate and :toDate");
        } else {
            builder.append(" details.master.vouDate between :frmDate and :toDate");
        }
        if ((voucherType != null) && (voucherType != 0L)) {
            builder.append(" and details.master.vouTypeCpdId =:voucherType");
        }
        if (amount != null) {
            builder.append(" and details.voudetAmt =:amount");
        }
        if ((refNo != null) && !MainetConstants.operator.EMPTY.equals(refNo)) {
            builder.append(" and details.master.vouReferenceNo =:refNo");
        }
        builder.append(" and details.master.org =:org order by details.master.vouId desc");
        final Query query = entityManager.createQuery(builder.toString());

        if (authStatus.equals(MainetConstants.MENU.Y)) {
            query.setParameter(AccountJournalVoucherEntry.AUTH_STATUS1, authStatus);
            query.setParameter(AccountJournalVoucherEntry.AUTH_STATUS2, MainetConstants.MENU.Y);
        } else {
            query.setParameter(AccountJournalVoucherEntry.AUTH_STATUS1, authStatus);
            query.setParameter(AccountJournalVoucherEntry.AUTH_STATUS2, MainetConstants.MENU.D);
        }
        query.setParameter(MainetConstants.FRM_DATE, frmDate);
        query.setParameter(MainetConstants.TO_DATE, toDate);

        if ((voucherType != null) && (voucherType != 0L)) {
            query.setParameter(AccountJournalVoucherEntry.VOUCHER_TYPE, voucherType);
        }
        if (amount != null) {
            query.setParameter(MainetConstants.BankParam.AMT, amount);
        }
        if ((refNo != null) && !MainetConstants.operator.EMPTY.equals(refNo)) {
            query.setParameter(AccountJournalVoucherEntry.REF_NO, refNo);
        }
        query.setParameter(AccountJournalVoucherEntry.ORG2, org);
        @SuppressWarnings("unchecked")
        final List<Object[]> list = query.getResultList();
        return list;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.account.dao.AccountJournalVoucherEntryDao# saveAccountJournalVoucherEntry(com.abm.mainetservice.
     * account.entity.AccountVoucherEntryEntity)
     */
    @Override
    public AccountVoucherEntryEntity saveAccountJournalVoucherEntry(final AccountVoucherEntryEntity entity) {
        AccountVoucherEntryEntity finalEntity = null;
        try {
            // entityManager.persist(entity);
            finalEntity = accountVoucherEntryRepository.save(entity);
        } catch (final Exception e) {
            logger.error(e);
        }
        return finalEntity;
    }

    @Override
    public AccountVoucherEntryEntity updateAccountJournalVoucherEntry(final AccountVoucherEntryEntity entity) {
        AccountVoucherEntryEntity finalEntity = null;
        try {
            finalEntity = entityManager.merge(entity);
        } catch (final Exception e) {
            logger.error(e);
        }
        return finalEntity;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.account.dao.AccountJournalVoucherEntryDao# getAccountVoucherDataBeanById(long)
     */
    @Override
    public AccountVoucherEntryEntity getAccountVoucherDataBeanById(final long rowId) {
        final Query query = entityManager
                .createQuery(QueryConstants.MASTERS.AccountJournalVoucherEntryMaster.QUERY_TO_SELECT_VOUCHER);
        query.setParameter(AccountJournalVoucherEntry.ROW_ID, rowId);
        AccountVoucherEntryEntity master = null;
        try {
            master = (AccountVoucherEntryEntity) query.getSingleResult();
        } catch (final NoResultException nre) {
            logger.error("No record found from table for provided input Params[rowId=" + rowId + "]", nre);
        }
        return master;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.account.dao.AccountJournalVoucherEntryDao#deleteRow( long)
     */
    @Override
    public void deleteRow(final long voudetId) {
        final Query query = entityManager
                .createQuery(QueryConstants.MASTERS.AccountJournalVoucherEntryMaster.QUERY_TO_DELETE_VOUCHER_ENTRY);
        query.setParameter(AccountJournalVoucherEntry.VOUDET_ID, voudetId);
        query.executeUpdate();
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.account.dao.AccountJournalVoucherEntryDao#delete(long)
     */
    @Override
    public void delete(final long rowId) {
        final Query query = entityManager
                .createQuery(QueryConstants.MASTERS.AccountJournalVoucherEntryMaster.QUERY_TO_DELETE_VOUCHER_ROWID);
        query.setParameter(AccountJournalVoucherEntry.ROW_ID, rowId);
        query.executeUpdate();

    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<AccountVoucherEntryEntity> getVoucherDetails(final String depositSlipNo, final Date depositDate,
            final Long voucherTypeId, final Long voucherSubTypeId, final Long orgId) {
        final Query query = entityManager
                .createQuery(QueryConstants.MASTERS.AccountJournalVoucherEntryMaster.QUERY_VOUCHER_DETAIL);
        query.setParameter(AccountJournalVoucherEntry.DEPOSIT_SLIP_NO, depositSlipNo);
        query.setParameter(AccountJournalVoucherEntry.DEPOSIT_DATE, depositDate);
        query.setParameter(AccountJournalVoucherEntry.VOUCHER_TYPE_ID, voucherTypeId);
        query.setParameter(MainetConstants.AccountChequeDishonour.VOUCHER_SUB_TYPE_ID, voucherSubTypeId);
        query.setParameter(MainetConstants.TABLE_COLUMN.ORGANISATION, orgId);
        List<AccountVoucherEntryEntity> master = null;
        try {
            master = query.getResultList();
        } catch (NoResultException | NonUniqueResultException nre) {
            logger.error("No record found from table for provided input Params[depositSlipNo & orgId=" + depositSlipNo
                    + "+" + orgId + "]", nre);
        }
        return master;
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<AccountVoucherEntryEntity> getReceiptVoucherDetails(final String depositSlipNo, final Date receiptDate,
            final Long voucherTypeId, final Long voucherSubTypeId, final Long orgId) {
        final Query query = entityManager
                .createQuery(QueryConstants.MASTERS.AccountJournalVoucherEntryMaster.QUERY_RECEIPT_VOUCHER_DETAIL);
        query.setParameter(AccountJournalVoucherEntry.DEPOSIT_SLIP_NO, depositSlipNo);
        query.setParameter(AccountJournalVoucherEntry.RECEIPT_DATE, receiptDate);
        query.setParameter(AccountJournalVoucherEntry.VOUCHER_TYPE_ID, voucherTypeId);
        query.setParameter(MainetConstants.AccountChequeDishonour.VOUCHER_SUB_TYPE_ID, voucherSubTypeId);
        query.setParameter(MainetConstants.TABLE_COLUMN.ORGANISATION, orgId);
        List<AccountVoucherEntryEntity> master = null;
        try {
            master = query.getResultList();
        } catch (NoResultException | NonUniqueResultException nre) {
            logger.error("No record found from table for provided input Params[depositSlipNo & orgId=" + depositSlipNo
                    + "+" + orgId + "]", nre);
        }
        return master;
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<AccountVoucherEntryEntity> getReceiptReversalVoucherDetails(final String depositSlipNo,
            final Date receiptDate, final Long voucherTypeId, final Long orgId, Long dpDeptId, List<Long> vouSubTypes,
            Long entryTypeId) {
        final Query query = entityManager.createQuery(
                QueryConstants.MASTERS.AccountJournalVoucherEntryMaster.QUERY_RECEIPT_REVERSAL_VOUCHER_DETAIL);
        query.setParameter(AccountJournalVoucherEntry.DEPOSIT_SLIP_NO, depositSlipNo);
        query.setParameter(AccountJournalVoucherEntry.RECEIPT_DATE, receiptDate);
        query.setParameter(AccountJournalVoucherEntry.VOUCHER_TYPE_ID, voucherTypeId);
        query.setParameter(MainetConstants.TABLE_COLUMN.ORGANISATION, orgId);
        query.setParameter(AccountJournalVoucherEntry.DEPT_ID, dpDeptId);
        query.setParameter(AccountJournalVoucherEntry.VOUCHER_SUB_TYPES, vouSubTypes);
        query.setParameter(AccountJournalVoucherEntry.ENTRY_TYPE_ID, entryTypeId);
        List<AccountVoucherEntryEntity> master = null;
        try {
            master = query.getResultList();
        } catch (NoResultException | NonUniqueResultException nre) {
            logger.error("No record found from table for provided input Params[depositSlipNo & orgId=" + depositSlipNo
                    + "+" + orgId + "]", nre);
        }
        return master;
    }

    @Override
    @Transactional
    public void updateAccountJournalVoucherEntryForDisapprove(final Long vouId, final String authRemark) {
        final Query query = entityManager.createQuery(buildDynamicSaveQuery(vouId, authRemark));
        query.setParameter(AccountJournalVoucherEntry.VOU_ID, vouId);
        query.setParameter(AccountJournalVoucherEntry.AUTH_REMARK, authRemark);
        query.executeUpdate();
        return;
    }

    private String buildDynamicSaveQuery(final Long vouId, final String authRemark) {

        final StringBuilder builder = new StringBuilder();
        builder.append(QueryConstants.MASTERS.AccountJournalVoucherEntryMaster.QUERY_UPDATE_ACC_VOUCHER_ENTRY);
        return builder.toString();
    }

    @Override
    @Transactional
    public boolean isCombinationCheckTransactions(Long sacHeadid, Long orgId) {
        // TODO Auto-generated method stub
        final Query query = createQuery(
                QueryConstants.MASTERS.AccountJournalVoucherEntryMaster.QUERY_TO_GET_ALL_TRANSACTIONS);
        query.setParameter("sacHeadId", sacHeadid);
        query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.ORGID, orgId);
        if (query.getResultList().isEmpty()) {
            return false;
        }
        return true;
    }

    /*
     * @Override public void saveVoucherEntryExcelData(AccountVoucherEntryDetailsEntity accountVoucherEntryEntity) {
     * accountVoucherEntryDetailsRepository.save(accountVoucherEntryEntity); }
     */

    @Override
    public List<AccountVoucherEntryEntity> getVoucherDetails(Long orgId) {

        final Query query = entityManager
                .createQuery("SELECT voucher FROM AccountVoucherEntryEntity voucher WHERE voucher.org=:orgId order by 1 desc");
        query.setParameter("orgId", orgId);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AccountVoucherEntryEntity> getGridVoucherData(Long orgId) {
        final Query query = entityManager
                .createQuery("SELECT voucher FROM AccountVoucherEntryEntity voucher WHERE voucher.org=:orgId order by 1 DESC");
        query.setParameter("orgId", orgId);
        List<AccountVoucherEntryEntity> result = null;
        result = query.getResultList();
        return result;
    }

}
