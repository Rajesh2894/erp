package com.abm.mainet.account.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.abm.mainet.account.dto.AccountInvestmentMasterDto;

public interface AccountInvestmentService {
	
	 AccountInvestmentMasterDto saveInvestMentMaster(AccountInvestmentMasterDto acInvstMasterDto);

	List<AccountInvestmentMasterDto> findByBankIdInvestmentData(String invstNo,Long invstId, Long bankId, BigDecimal invstAmount, Long fundId,Date fromDate,Date toDate, Long orgId );

	AccountInvestmentMasterDto findByInvstIdAndOrgId(Long invstId,Long orgId);
	
	public List<AccountInvestmentMasterDto> findAllInvestmentDataByOrgId(Long orgId);
	
	
}
