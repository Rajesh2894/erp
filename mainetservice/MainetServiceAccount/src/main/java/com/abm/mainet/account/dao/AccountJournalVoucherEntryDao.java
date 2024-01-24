
package com.abm.mainet.account.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.abm.mainet.account.domain.AccountVoucherEntryEntity;

/**
 * @author deepika.pimpale
 *
 */
public interface AccountJournalVoucherEntryDao {

    /**
     * @param authStatus
     * @param amount
     * @param searchEntity
     * @return
     */
    List<Object[]> getSearchVoucherData(Long voucherType, Date fromDate, Date toDate, String dateType, String authStatus,
            BigDecimal amount, Long org, String refNo);

    /**
     * @param entity
     */
    AccountVoucherEntryEntity saveAccountJournalVoucherEntry(AccountVoucherEntryEntity entity);

    /**
     * @param rowId
     */
    AccountVoucherEntryEntity getAccountVoucherDataBeanById(long rowId);

    /**
     * @param entity
     */
    AccountVoucherEntryEntity updateAccountJournalVoucherEntry(AccountVoucherEntryEntity entity);

    /**
     * @param voudetId
     */
    void deleteRow(long voudetId);

    /**
     * @param rowId
     */
    void delete(long rowId);

    List<AccountVoucherEntryEntity> getVoucherDetails(String depositSlipNo, Date depositDate, Long voucherTypeId,
            Long voucherSubTypeId, Long orgId);

    List<AccountVoucherEntryEntity> getReceiptVoucherDetails(String depositSlipNo, Date receiptDate, Long voucherTypeId,
            Long voucherSubTypeId, Long orgId);

    List<AccountVoucherEntryEntity> getReceiptReversalVoucherDetails(String depositSlipNo, Date receiptDate, Long voucherTypeId,
            Long orgId, Long dpDeptId, List<Long> vouSubTypes, Long entryTypeId);

    void updateAccountJournalVoucherEntryForDisapprove(Long vouId, String authRemark);

    boolean isCombinationCheckTransactions(Long sacHeadid, Long orgId);

    public List<AccountVoucherEntryEntity> getVoucherDetails(Long orgId);

    List<AccountVoucherEntryEntity> getGridVoucherData(Long orgId);
}
