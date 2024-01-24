
package com.abm.mainet.account.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.abm.mainet.account.domain.AccountDepositEntity;
import com.abm.mainet.account.dto.AccountDepositBean;
import com.abm.mainet.account.dto.AccountDepositUploadDto;

public interface AccountDepositService {

    AccountDepositBean create(AccountDepositBean bean) throws ParseException;

    AccountDepositEntity fidbyId(Long depid);

    List<AccountDepositBean> findAll(Long orgId);

    List<AccountDepositBean> findByAllGridSearchData(String depNo, Long vmVendorid, Long cpdDepositType, Long sacHeadId, Date date,
            String depAmount, Long orgId,Long deptId);

    BigDecimal getEmdAmount(Long trEmdAmt, Long orgId);

    public void saveAccountDepositExcelData(AccountDepositUploadDto accountDepositUploadDto, Long orgId, int langId,
            Long statusId) throws ParseException;
}
