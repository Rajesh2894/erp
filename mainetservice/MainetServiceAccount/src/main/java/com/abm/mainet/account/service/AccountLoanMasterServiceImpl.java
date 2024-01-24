package com.abm.mainet.account.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import com.abm.mainet.account.dao.AccountLoanMasterDao;
import com.abm.mainet.account.domain.AccountLoanMasterEntity;
import com.abm.mainet.account.dto.AccountLoanMasterDto;
import com.abm.mainet.account.mapper.AccountLoanMasterMapper;
import com.abm.mainet.account.repository.AccountLoanMasterRepository;
import com.abm.mainet.common.constant.MainetConstants;

@Service
public class AccountLoanMasterServiceImpl implements AccountLoanMasterService {
	@Autowired
	private AccountLoanMasterMapper accountLoanMasterMapper;
	@Autowired
	private AccountLoanMasterRepository accountLoanMasterRepository;

	@Autowired
	private AccountLoanMasterDao accountLoanMasterDao;

	@Override
	@Transactional
	public List<AccountLoanMasterDto> saveLoanMaster(List<AccountLoanMasterDto> accountLoanMasterDtoList) {
		// TODO Auto-generated method stub
		List<AccountLoanMasterEntity> loanMasterList = accountLoanMasterMapper
				.mapAccountLoanMasterDtoListToAccountLoanMasterList(accountLoanMasterDtoList);
		loanMasterList = accountLoanMasterRepository.save(loanMasterList);
		return accountLoanMasterMapper.mapAccountLoanMasterListToAccountLoanMasterDtoList(loanMasterList);
	}

	@Override
	@Transactional
	public AccountLoanMasterDto saveLoanMaster(AccountLoanMasterDto accountLoanMasterDto) {
		// TODO Auto-generated method stub
		AccountLoanMasterEntity lonaMaster = accountLoanMasterMapper
				.mapAccountLoanMasterDtoToAccountLoanMaster(accountLoanMasterDto);
		lonaMaster = accountLoanMasterRepository.save(lonaMaster);
		return accountLoanMasterMapper.mapAccountLoanMasterToAccountLoanMasterDto(lonaMaster);
	}

	@Override
	@Transactional(readOnly = true)
	public List<AccountLoanMasterDto> findLoanMasterData(Long loanId,String lnDeptname, String lnPurpose, Long orgId,String loanCode) {
		// TODO Auto-generated method stub
		Assert.notNull(orgId, MainetConstants.ORGANISATION_ID_NOT_NULL);
		/*
		 * List<AccountLoanMasterEntity>
		 * master=accountLoanMasterRepository.findByOrgId(orgId);
		 */
		return accountLoanMasterMapper.mapAccountLoanMasterListToAccountLoanMasterDtoList(
				accountLoanMasterDao.searchByDeptAndPurpose(loanId,lnDeptname, lnPurpose, orgId,loanCode));
	}

}


