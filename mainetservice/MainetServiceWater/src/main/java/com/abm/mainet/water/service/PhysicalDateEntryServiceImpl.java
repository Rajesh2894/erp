package com.abm.mainet.water.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.domain.TbWorkOrderEntity;
import com.abm.mainet.common.dto.GridSearchDTO;
import com.abm.mainet.common.dto.PagingDTO;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;
import com.abm.mainet.water.dao.PhysicalDateEntryJpaRepository;
import com.abm.mainet.water.domain.TbKCsmrInfoMH;
import com.abm.mainet.water.dto.PhysicalDateEntryDTO;

/**
 * Implementation of TbCsmrInfoService
 */
@Service
public class PhysicalDateEntryServiceImpl implements PhysicalDateEntryService {

	@Resource
	private PhysicalDateEntryJpaRepository physicalDateEntryJpaRepository;

	@Autowired
	private ICFCApplicationMasterService icfcApplicationMasterService;

	@Autowired
	NewWaterConnectionService newWaterConnectionService;

	@Autowired
	private ISMSAndEmailService ismsAndEmailService;

	@Resource
	private AuditService auditService;

	@Override
	@Transactional
	public TbKCsmrInfoMH saveFormData(final PhysicalDateEntryDTO dto, final Long orgId) {
		final TbKCsmrInfoMH queryEntity = physicalDateEntryJpaRepository
				.queryDataAgainstAppliNum(dto.getApplicationNumber());
		if (queryEntity != null && queryEntity.getCsCcn() == null) {
			String ccnNo = newWaterConnectionService.generateWaterConnectionNumber(dto.getApplicationNumber(),
					dto.getServiceId(), queryEntity.getOrgId(),queryEntity);
			queryEntity.setCsCcn(ccnNo);
			final TbCfcApplicationMstEntity appMasEntity = icfcApplicationMasterService
					.getCFCApplicationByApplicationId(dto.getApplicationNumber(), queryEntity.getOrgId());
			final CFCApplicationAddressEntity appAddEntity = icfcApplicationMasterService
					.getApplicantsDetails(dto.getApplicationNumber());
			String userName = (appMasEntity.getApmFname() == null ? MainetConstants.BLANK
					: appMasEntity.getApmFname() + MainetConstants.WHITE_SPACE);
			userName += appMasEntity.getApmMname() == null ? MainetConstants.BLANK
					: appMasEntity.getApmMname() + MainetConstants.WHITE_SPACE;

			userName += appMasEntity.getApmLname() == null ? MainetConstants.BLANK : appMasEntity.getApmLname();
			Organisation org = appMasEntity.getTbOrganisation();
			final SMSAndEmailDTO smsDto = new SMSAndEmailDTO();
			smsDto.setEmail(appAddEntity.getApaEmail());
			smsDto.setMobnumber(appAddEntity.getApaMobilno());
			smsDto.setUserName(userName);
			smsDto.setAppName(userName);
			smsDto.setOrganizationName(org.getONlsOrgname());
			// Added Changes As per told by Rajesh Sir For Sms and Email
			smsDto.setUserId(appMasEntity.getUserId());
			int langId = Utility.getDefaultLanguageId(org);
			ismsAndEmailService.sendEmailSMS(MainetConstants.DEPT_SHORT_NAME.WATER, "NewWaterConnectionForm.html",
					PrefixConstants.SMS_EMAIL_ALERT_TYPE.APPROVAL, smsDto, org, langId);
		}
		queryEntity.setPcFlg(dto.getPhysicalConnFlag().toString());
		queryEntity.setPcDate(dto.getConnectionDate());
		queryEntity.setCsIsBillingActive(MainetConstants.RnLCommon.Flag_A);
		
		Organisation org  = new Organisation();
		org.setOrgid(orgId);
		
			LookUp lookUp = CommonMasterUtility.getDefaultValue("CNS", org);
			queryEntity.setCsCcnstatus(lookUp.getLookUpId());
		
		
		final TbKCsmrInfoMH result = physicalDateEntryJpaRepository.updateFormData(queryEntity);
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public List<PhysicalDateEntryDTO> getApplicantDetailsForGrid(final long orgid, final PagingDTO pagingDTO,
			final GridSearchDTO gridSearchDTO) {
		
		// Here writing 3 queries seperately for diffrent services because there diffrent tables with different conditions
		List<TbWorkOrderEntity> queryEntity = new ArrayList<TbWorkOrderEntity>();
		List<TbWorkOrderEntity> queryEntityForNewConnection = physicalDateEntryJpaRepository
				.queryDataFromWorkOrderTab(orgid, pagingDTO, gridSearchDTO);
		List<TbWorkOrderEntity> queryEntityForDisconnection = physicalDateEntryJpaRepository
				.queryDataFromWorkOrderTabForDisconnection(orgid, pagingDTO, gridSearchDTO);
		List<TbWorkOrderEntity> queryEntityForReconnection = physicalDateEntryJpaRepository
				.queryDataFromWorkOrderTabForReconnection(orgid, pagingDTO, gridSearchDTO);
		queryEntity.addAll(queryEntityForNewConnection);
		queryEntity.addAll(queryEntityForDisconnection);
		queryEntity.addAll(queryEntityForReconnection);
		final List<PhysicalDateEntryDTO> listOfDto = new ArrayList<>();
		PhysicalDateEntryDTO entity = null;
		if ((queryEntity != null) && !queryEntity.isEmpty()) {
			final int totalCount = physicalDateEntryJpaRepository.queryDataFromWorkOrderTotalCount(orgid, pagingDTO,
					gridSearchDTO);
			for (final TbWorkOrderEntity workOrderEntity : queryEntity) {

				final TbCfcApplicationMstEntity appMasEntity = icfcApplicationMasterService
						.getCFCApplicationByApplicationId(Long.valueOf(workOrderEntity.getWoApplicationId().toString()),
								orgid);
				TbKCsmrInfoMH waterEntity = null;
				String serviceShortCode = ApplicationContextProvider.getApplicationContext()
						.getBean(ServiceMasterService.class)
						.fetchServiceShortCode(workOrderEntity.getWoServiceId(), orgid);
				if (StringUtils.isNotBlank(serviceShortCode) && StringUtils.equals(serviceShortCode, "WNC")) {
					waterEntity = physicalDateEntryJpaRepository
							.queryDataAgainstAppliNum(Long.valueOf(workOrderEntity.getWoApplicationId().toString()));
				}

				if (appMasEntity != null) {
					entity = new PhysicalDateEntryDTO();
					entity.setServiceShortCode(serviceShortCode);
					entity.setCount(queryEntity.size());
					entity.setServiceName(appMasEntity.getTbServicesMst().getSmServiceName());
					entity.setApplicantName(appMasEntity.getApmFname());
					entity.setApplicationNumber(appMasEntity.getApmApplicationId());
					entity.setApplicationDate(appMasEntity.getApmApplicationDate());
					if (waterEntity != null) {
						entity.setCsIdn(waterEntity.getCsIdn());
					}
					listOfDto.add(entity);
				}

			}
		}
		return listOfDto;
	}
	
	@Override
	@Transactional
	public String generateConnectionNumber(final Long applicationI, final Long serviceId) {
		String ccnNo = null;
    	final TbKCsmrInfoMH queryEntity = physicalDateEntryJpaRepository
				.queryDataAgainstAppliNum(applicationI);
		if (queryEntity != null && queryEntity.getCsCcn() == null) {
			ccnNo = newWaterConnectionService.generateWaterConnectionNumber(applicationI,
					serviceId, UserSession.getCurrent().getOrganisation().getOrgid() ,queryEntity);
			queryEntity.setCsCcn(ccnNo);
			final TbCfcApplicationMstEntity appMasEntity = icfcApplicationMasterService
					.getCFCApplicationByApplicationId(applicationI, UserSession.getCurrent().getOrganisation().getOrgid());
			final CFCApplicationAddressEntity appAddEntity = icfcApplicationMasterService
					.getApplicantsDetails(applicationI);
			String userName = (appMasEntity.getApmFname() == null ? MainetConstants.BLANK
					: appMasEntity.getApmFname() + MainetConstants.WHITE_SPACE);
			userName += appMasEntity.getApmMname() == null ? MainetConstants.BLANK
					: appMasEntity.getApmMname() + MainetConstants.WHITE_SPACE;

			userName += appMasEntity.getApmLname() == null ? MainetConstants.BLANK : appMasEntity.getApmLname();
			Organisation org = appMasEntity.getTbOrganisation();
			final SMSAndEmailDTO smsDto = new SMSAndEmailDTO();
			smsDto.setEmail(appAddEntity.getApaEmail());
			smsDto.setMobnumber(appAddEntity.getApaMobilno());
			smsDto.setUserName(userName);
			smsDto.setAppName(userName);
			smsDto.setOrganizationName(org.getONlsOrgname());
			// Added Changes As per told by Rajesh Sir For Sms and Email
			smsDto.setUserId(appMasEntity.getUserId());
			int langId = Utility.getDefaultLanguageId(org);
			ismsAndEmailService.sendEmailSMS(MainetConstants.DEPT_SHORT_NAME.WATER, "NewWaterConnectionForm.html",
					PrefixConstants.SMS_EMAIL_ALERT_TYPE.APPROVAL, smsDto, org, langId);
		}
		if(ccnNo != null) {
			final TbKCsmrInfoMH result = physicalDateEntryJpaRepository.updateFormData(queryEntity);
		}
		
		return ccnNo;
    }

}
