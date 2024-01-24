package com.abm.mainet.account.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


import com.abm.mainet.account.domain.AccountInvestmentMasterEntity;


public interface AccountInvestMentDao {

	List<AccountInvestmentMasterEntity> searchByBankId(String invstNo,Long invstId, Long bankId, BigDecimal invstAmount,Long fundId,Date fromDate,Date toDate,Long orgId);
}
