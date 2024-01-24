package com.abm.mainet.account.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.abm.mainet.account.dao.AccountInvestMentDao;
import com.abm.mainet.account.domain.AccountInvestmentMasterEntity;
import com.abm.mainet.account.domain.AccountInvestmentMasterHistEntity;
import com.abm.mainet.account.dto.AccountInvestmentMasterDto;
import com.abm.mainet.account.mapper.AccountInvestMentMasterMapper;
import com.abm.mainet.account.repository.AccoutInvestMentMasterJpaRepository;
import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;

@Service
public class AccountInvestmentServiceImpl implements AccountInvestmentService {
	@Autowired
	private AccountInvestMentMasterMapper accountInvestMentMasterMapper;

	@Autowired
	private AccoutInvestMentMasterJpaRepository accoutInvestMentMasterJpaRepository;

	@Autowired
	private AccountInvestMentDao accountInvestMentDao;

	@Resource
	private AuditService auditService;

	private static Logger LOGGER = Logger.getLogger(AccountGrantMasterServiceImpl.class);

	@Override
	@Transactional
	public AccountInvestmentMasterDto saveInvestMentMaster(AccountInvestmentMasterDto acInvstMasterDto) {
		AccountInvestmentMasterEntity master = accountInvestMentMasterMapper
				.mapAccountInvestmentMasterDtoToAccountInvestmentMaster(acInvstMasterDto);
		master = accoutInvestMentMasterJpaRepository.save(master);
		if ((acInvstMasterDto.getInvstId() != null) && (acInvstMasterDto.getInvstId() > 0L)) {
			AccountInvestmentMasterHistEntity accountInvestmentMasterHistEntity = new AccountInvestmentMasterHistEntity();
			accountInvestmentMasterHistEntity.sethStatus(MainetConstants.CommonConstants.CHAR_U);
			try {
				auditService.createHistory(master, accountInvestmentMasterHistEntity);
			} catch (Exception ex) {
				LOGGER.error("Could not make audit entry for " + master, ex);
			}
		} else {
			AccountInvestmentMasterHistEntity accountInvestmentMasterHistEntity = new AccountInvestmentMasterHistEntity();
			accountInvestmentMasterHistEntity.sethStatus(MainetConstants.CommonConstants.CHAR_C);
			try {
				auditService.createHistory(master, accountInvestmentMasterHistEntity);
			} catch (Exception ex) {
				LOGGER.error("Could not make audit entry for " + master, ex);
			}
		}
		return accountInvestMentMasterMapper.mapAccountInvestmentMasterToAccountInvestmentMasterDto(master);
	}


	@Transactional(readOnly = true)
	public List<AccountInvestmentMasterDto> findByBankIdInvestmentData(String invstNo,Long invstId, Long bankId, BigDecimal invstAmount,Long fundId,Date fromDate,Date toDate,Long orgId) {
		Assert.notNull(orgId, MainetConstants.ORGANISATION_ID_NOT_NULL);
		return accountInvestMentMasterMapper.mapAccountInvestmentMasterListToAccountInvestmentMasterDtoList(
				accountInvestMentDao.searchByBankId(invstNo,invstId, bankId, invstAmount, fundId,fromDate,toDate, orgId));
		
	}

	@Override
	@Transactional(readOnly = true)
	public AccountInvestmentMasterDto findByInvstIdAndOrgId(Long invstId, Long orgId) {
		// TODO Auto-generated method stub
		Assert.notNull(orgId, MainetConstants.ORGANISATION_ID_NOT_NULL);
		AccountInvestmentMasterEntity master = new AccountInvestmentMasterEntity();
		master = accoutInvestMentMasterJpaRepository.findByOrgId(orgId);
		AccountInvestmentMasterDto AccountInvestmentMasterDto = new AccountInvestmentMasterDto();
		BeanUtils.copyProperties(master, AccountInvestmentMasterDto);
		return accountInvestMentMasterMapper.mapAccountInvestmentMasterToAccountInvestmentMasterDto(master);
	}


	@Override
	@Transactional(readOnly = true)
	public List<AccountInvestmentMasterDto> findAllInvestmentDataByOrgId(Long orgId) {
		 List<AccountInvestmentMasterDto> masterDtoList = new ArrayList<>();
	        try {
	        	ApplicationSession.getInstance();
	            List<AccountInvestmentMasterEntity> entityList = accoutInvestMentMasterJpaRepository.findByOnlyOrgId(orgId);
	            if (CollectionUtils.isNotEmpty(entityList)) {
	                entityList.forEach(entity -> {
	                	AccountInvestmentMasterDto masterDto = new AccountInvestmentMasterDto();
	                    BeanUtils.copyProperties(entity, masterDto);
	                    masterDtoList.add(masterDto);
	                });
	            }
	        } catch (Exception exception) {
	            throw new FrameworkException("Error Occured While fetching the Investment Master List", exception);
	        }
	        return masterDtoList;
	}


	
}
