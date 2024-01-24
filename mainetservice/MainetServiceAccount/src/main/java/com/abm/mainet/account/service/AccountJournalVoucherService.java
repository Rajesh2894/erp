
package com.abm.mainet.account.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.abm.mainet.account.domain.AccountVoucherEntryEntity;
import com.abm.mainet.account.dto.AccountJournalVoucherEntryBean;
import com.abm.mainet.account.dto.AccountVoucherMasterUploadDTO;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.utility.LookUp;

/**
 * @author deepika.pimpale
 *
 */
public interface AccountJournalVoucherService {

    /**
     * @param toDate
     * @param voucherType
     * @param authStatus
     * @param amount
     * @param searchEntity
     * @return
     */
    List<AccountJournalVoucherEntryBean> getSearchVoucherData(Long voucherType, Date fromDate, Date toDate, String dateType,
            String authStatus, BigDecimal amount, Long orgId, String refNo, String urlIdentifyFlag);

    /**
     * @param entity
     */
    boolean saveAccountJournalVoucherEntry(AccountJournalVoucherEntryBean entity);

    /**
     * @param rowId
     * @return
     */
    AccountJournalVoucherEntryBean getAccountVoucherDataBeanById(long rowId);

    /**
     * @param dto
     * @return
     */
    boolean updateAccountJournalVoucherEntry(AccountJournalVoucherEntryBean dto);

    /**
     * @param voudetId
     */
    void deleteRow(long voudetId);

    /**
     * @param rowId
     */
    void delete(long rowId);

    String generateVoucherNumber(String voucherType, Long org, String vocherDate);

    List<LookUp> findVoucherSubTypeList(Long voucherTypeId, Long deptId, Long orgId);

    boolean isCombinationCheckTransactions(Long sacHeadid, Long orgId);

    boolean checkFinancialYearStatusHardCloseOrNot(String transactionAuthDate, Long orgid);

    public boolean saveVoucherEntryExcelData(AccountVoucherMasterUploadDTO voucherEntryUploadDto, Long orgId, int langId,
            Employee emp);

    public List<AccountVoucherEntryEntity> getVoucherMasterDetails(Long orgId);

    List<AccountJournalVoucherEntryBean> getGridVoucherData(Long orgId);

    public void updateVoucherIdInDeposit(Long refNo, Long voucherId, Long orgId);

}
