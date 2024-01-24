package com.abm.mainet.account.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.abm.mainet.account.dao.AccountGrantMasterDao;
import com.abm.mainet.account.dao.AccountInvestMentDao;
import com.abm.mainet.account.domain.AccountBudgetProjectedRevenueEntryHistEntity;
import com.abm.mainet.account.domain.AccountGrantMasterEntity;
import com.abm.mainet.account.domain.AccountGrantMasterHistEntity;
import com.abm.mainet.account.dto.AccountGrantMasterDto;
import com.abm.mainet.account.mapper.AccountGrantMasterServiceMapper;
import com.abm.mainet.account.repository.AccountFinancialReportRepository;
import com.abm.mainet.account.repository.AccountGrantMasterJpaRepository;
import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.utility.Utility;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@WebService(endpointInterface = "com.abm.mainet.account.service.AccountGrantMasterService")
@Api(value = "/grantMaster")
@Path("/grantMaster")
@Service
public class AccountGrantMasterServiceImpl implements AccountGrantMasterService {

	@Autowired
	private AccountGrantMasterServiceMapper accountGrantMasterServiceMapper;

	@Autowired
	private AccountGrantMasterJpaRepository accountGrantMasterJpaRepository;

	@Autowired
	private AccountGrantMasterDao accountGrantMasterDao;

	@Resource
	private AuditService auditService;
	
	@Resource
	private AccountFinancialReportRepository accountFinancialReportRepository;

	private static Logger LOGGER = Logger.getLogger(AccountGrantMasterServiceImpl.class);

	@Override
	@WebMethod(exclude = true)
	@Transactional
	public AccountGrantMasterDto saveGrantMaster(AccountGrantMasterDto accountGrantMasterDto) {
		AccountGrantMasterEntity master = accountGrantMasterServiceMapper
				.mapAccountGrantMasterDtoToAccountGranttMasterEntity(accountGrantMasterDto);
		master = accountGrantMasterJpaRepository.save(master);
		if ((accountGrantMasterDto.getGrntId() != null) && (accountGrantMasterDto.getGrntId() > 0L)) {
			AccountGrantMasterHistEntity accountGrantMasterHistEntity = new AccountGrantMasterHistEntity();
			accountGrantMasterHistEntity.sethStatus(MainetConstants.CommonConstants.CHAR_U);
			try {
				auditService.createHistory(master, accountGrantMasterHistEntity);
			} catch (Exception ex) {
				LOGGER.error("Could not make audit entry for " + master, ex);
			}
		} else {
			AccountGrantMasterHistEntity accountGrantMasterHistEntity = new AccountGrantMasterHistEntity();
			accountGrantMasterHistEntity.sethStatus(MainetConstants.CommonConstants.CHAR_C);
			try {
				auditService.createHistory(master, accountGrantMasterHistEntity);
			} catch (Exception ex) {
				LOGGER.error("Could not make audit entry for " + master, ex);
			}
		}
		return accountGrantMasterServiceMapper.mapAccountGrantMasterEntityToAccountGrantMasterDto(master);
	}

	@Override
	@POST
	@ApiOperation(value = "Fetch Grant MasterData", notes = "Response", response = Object.class)
	@Path("/grantDetails")
	@Transactional(readOnly = true)
	public List<AccountGrantMasterDto> findByGrntIdAndOrgId(@QueryParam("grntId") Long grntId,
			@QueryParam("orgId") Long orgId) {
		// TODO Auto-generated method stub
		Assert.notNull(orgId, MainetConstants.ORGANISATION_ID_NOT_NULL);
		List<AccountGrantMasterEntity> master = new ArrayList<AccountGrantMasterEntity>();
		master = accountGrantMasterJpaRepository.findByGrntIdAndOrgId(grntId, orgId);
		return accountGrantMasterServiceMapper.mapAccountInvestmentMasterListToAccountInvestmentMasterDtoList(master);
	}

	@Override
	@POST
	@ApiOperation(value = "Fetch Grant MasterData", notes = "Response", response = Object.class)
	@Path("/grantDetails/{grtNo}")
	@Transactional(readOnly = true)
	public List<AccountGrantMasterDto> findByNameAndNature(String grtType, String grtName, @PathParam("grtNo")String grtNo, Long fundId,String faYearId,Date from, Date to,
			Long orgId) {
		// TODO Auto-generated method stub
		Date fromDate = null, toDate = null;
		if(faYearId != null && !faYearId.isEmpty() && faYearId != "")
		{
				Long yearId = Long.valueOf(faYearId);
				List<Object[]> frmdateTodate = accountFinancialReportRepository.getAllFinincialFromDate(yearId);
				
				for (Object[] dateEntity : frmdateTodate) {
					
					fromDate =  Utility.converObjectToDate(dateEntity[1]);
					toDate = Utility.converObjectToDate(dateEntity[2]);
				}
		}
		if(from != null && to!=null)
		{
			fromDate = from;
			toDate = to;
		}
		
		
		Assert.notNull(orgId, MainetConstants.ORGANISATION_ID_NOT_NULL);
		return accountGrantMasterServiceMapper.mapAccountInvestmentMasterListToAccountInvestmentMasterDtoList(
				accountGrantMasterDao.findByNameAndNature(grtType, grtName, grtNo, fundId,fromDate, toDate, orgId));
	}

	@Override
	public AccountGrantMasterDto getGrantDetailsByGrntIdAndOrgId(Long grantId, Long orgId) {
		AccountGrantMasterDto accountGrantDto = new AccountGrantMasterDto();
		AccountGrantMasterEntity accountGrantEntity = accountGrantMasterJpaRepository.findByGrantIdAndOrgId(grantId, orgId);
		BeanUtils.copyProperties(accountGrantEntity, accountGrantDto);
		return accountGrantDto;
	}

}
