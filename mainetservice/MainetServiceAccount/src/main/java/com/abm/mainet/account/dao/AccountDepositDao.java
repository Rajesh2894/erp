package com.abm.mainet.account.dao;

import java.util.Date;
import java.util.List;

import com.abm.mainet.account.domain.AccountDepositEntity;

public interface AccountDepositDao {

    List<AccountDepositEntity> findByAllGridSearchData(String depNo, Long vmVendorid, Long cpdDepositType, Long sacHeadId,
            Date date, String depAmount, Long orgId,Long deptIds);
}
