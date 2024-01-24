package com.abm.mainet.account.service;

import java.util.List;

import javax.jws.WebService;

import com.abm.mainet.account.dto.AccountLoanMasterDto;

@WebService
public interface AccountLoanMasterService {
	List<AccountLoanMasterDto> saveLoanMaster(List<AccountLoanMasterDto> accountLoanMasterDtoList);
	
	AccountLoanMasterDto saveLoanMaster(AccountLoanMasterDto accountLoanMasterDtoList);
	
	List<AccountLoanMasterDto> findLoanMasterData(Long loanId, String lnDeptname,String lnPurpose,Long orgId,String loanCode);
}