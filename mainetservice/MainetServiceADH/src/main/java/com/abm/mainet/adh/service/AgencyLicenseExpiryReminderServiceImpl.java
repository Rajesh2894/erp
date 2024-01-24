package com.abm.mainet.adh.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abm.mainet.adh.dto.AdvertiserMasterDto;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.quartz.domain.QuartzSchedulerMaster;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

/**
 * @author bhagyashri.dongardive
 * @since 25 November 2020
 */
@Service
public class AgencyLicenseExpiryReminderServiceImpl implements IAgencyLicenseExpiryReminderService {

	@Autowired
	ISMSAndEmailService iSMSAndEmailService;

	@Autowired
	IAdvertiserMasterService iAdvertiserMasterService;

	@Override
	public void sendReminderAlertMsg(QuartzSchedulerMaster runtimeBean, List<Object> parameterList) {
		Organisation org = new Organisation();
		org.setOrgid(runtimeBean.getOrgId().getOrgid());
		Integer langId = 1;

		List<AdvertiserMasterDto> allData = iAdvertiserMasterService.getAllAdvertiserMasterByOrgId(org.getOrgid());

		List<LookUp> lookUps = CommonMasterUtility.getLookUps(MainetConstants.AdvertisingAndHoarding.AGENCY_ALERT_REMINDER, org);
		List<Integer> days = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(lookUps)) {
			lookUps.forEach(lookUp -> {
				days.add(Integer.parseInt(lookUp.getLookUpCode()));
			});
		}
		if (CollectionUtils.isNotEmpty(allData)) {
			allData.forEach(agencydto -> {

				if (agencydto.getAgencyLicToDate() != null) {

					int daysBetweenDates = Utility.getDaysBetweenDates(new Date(), agencydto.getAgencyLicToDate());

					if (days.contains(daysBetweenDates)) {
						// send MSG and mail
						final SMSAndEmailDTO smsdto = new SMSAndEmailDTO();
						smsdto.setMobnumber(agencydto.getAgencyContactNo());
						smsdto.setEmail(agencydto.getAgencyEmail());
						smsdto.setAppName(agencydto.getAgencyOwner());
						// Data added for message template
						smsdto.setAppNo(agencydto.getAgencyLicNo());
						smsdto.setDueDt(Utility.dateToString(agencydto.getAgencyLicToDate()));
						smsdto.setFrmDt(Utility.dateToString(agencydto.getAgencyLicFromDate()));
						smsdto.setReferenceNo(String.valueOf(agencydto.getApmApplicationId()));
						smsdto.setUserId(agencydto.getCreatedBy());
						smsdto.setOrgId(org.getOrgid());
						iSMSAndEmailService.sendEmailSMS(MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE, 
								MainetConstants.AdvertisingAndHoarding.AGENCY_REGISTRATION_SMS_URL,
								PrefixConstants.SMS_EMAIL_ALERT_TYPE.TASK_NOTIFICATION, smsdto, org, langId);
						
					}
				}
			});
		}
	}

}
