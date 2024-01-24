package com.abm.mainet.account.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.abm.mainet.account.dto.AccountContraVoucherEntryBean;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.acccount.dto.VoucherPostDTO;

/** @author tejas.kotekar */
public interface AccountContraVoucherEntryService {

    /**
     * @param bankAcIdPay
     * @param fYearId
     * @param orgId
     * @return
     */
    BigDecimal getBankBalance(Long bankAcId, Date date, Long orgid);

    /**
     * @param contraVoucherBean
     * @return
     */
    AccountContraVoucherEntryBean createContraEntry(AccountContraVoucherEntryBean contraVoucherBean, Long payModLookupId,
            Organisation org);

    /**
     * @param orgId
     */
    Set<Object[]> getAllContraEntryData(Long orgId);

    /**
     * @param orgId
     */
    List<Long> getTransactionNo(Long orgId);

    Set<Object[]> getContraEntryDetails(Long orgId, String fromDate, String toDate, Long transactionNo,
            Character transactionType);

    /**
     * @param transactionId
     * @param orgId
     */
    Object[] getContraEntryDataById(Long transactionId, Long orgId);

    boolean checkTemplateType(VoucherPostDTO dto, Long templateTypeId, Long voucherTypeId);

    /**
     * @param paymentId
     * @param orgId
     * @return
     */
    List<Object[]> getDepositDetailsForChequeUtilization(Long paymentId, Long orgId);

    public Long getSacHeadId(Long baAccountidPay, Long orgId);

    public Long getBudgetCodeIdForPettyChash(Long cpdIdPayMode, Long orgId);

    BigDecimal getPettyCashAmount(Date date, Long orgId, Long cpdIdPayMode, Long voucherSubTypeId, Long voucherSubTypeId1,
            Long voucherSubTypeId2, Long voucherSubTypeId3, Long status, Long deptId);

    BigDecimal getCashAmount(Date date, Long orgId, Long cpdIdPayMode, Long voucherSubTypeId, Long status, Long deptId);

}
