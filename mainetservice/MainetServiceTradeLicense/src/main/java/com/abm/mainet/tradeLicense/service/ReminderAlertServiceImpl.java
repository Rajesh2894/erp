package com.abm.mainet.tradeLicense.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.utility.UtilityService;
import com.abm.mainet.quartz.domain.QuartzSchedulerMaster;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;
@Service
public class ReminderAlertServiceImpl implements IReminderAlertService {
	
	private static final Logger LOGGER = Logger.getLogger(ReminderAlertServiceImpl.class);

	@Autowired
	ISMSAndEmailService iSMSAndEmailService;
	@Autowired
	ITradeLicenseApplicationService iTradeLicenseApplicationService;

	@Transactional
	@Override
	public void sendLicenseRenewalReminderAlertMsg(QuartzSchedulerMaster runtimeBean, List<Object> parameterList) {
	
		LOGGER.info("Enter In sendLicenseRenewalReminderAlertMsg");
		Long orgId = runtimeBean.getOrgId().getOrgid();

		Organisation org = new Organisation();
		org.setOrgid(orgId);
		Integer langId = Utility.getDefaultLanguageId(org);

		List<TradeMasterDetailDTO> activeTradeLicenseDTOList = iTradeLicenseApplicationService
				.getActiveApplicationIdByOrgId(orgId);

		final LookUp lookUp = CommonMasterUtility.getLookUpFromPrefixLookUpDesc(
				MainetConstants.TradeLicense.RENEWAL_SERVICE_SHORT_CODE, PrefixConstants.LQP_PREFIX.ALERT_REMINDER_DAYS,
				langId, org);
		Integer noOfDays = Integer.parseInt(lookUp.getLookUpCode());

		if (CollectionUtils.isNotEmpty(activeTradeLicenseDTOList)) {
			activeTradeLicenseDTOList.stream()
					.filter(k -> (k.getTrdLicfromDate() != null && k.getTrdLictoDate() != null)).forEach(dto -> {

						Date dueDate = getDueDate(dto.getTrdLictoDate(), noOfDays);

						// compare due date and current date if same then send mail&sms
						if (DateUtils.isSameDay(new Date(), dueDate)){

							if (CollectionUtils.isNotEmpty(dto.getTradeLicenseOwnerdetailDTO())) {
								dto.getTradeLicenseOwnerdetailDTO().forEach(ownDto -> {
									// send MSG and mail
									final SMSAndEmailDTO smsEmailDto = new SMSAndEmailDTO();
									smsEmailDto.setMobnumber(ownDto.getTroMobileno());
									smsEmailDto.setEmail(ownDto.getTroEmailid());
									smsEmailDto.setAppNo(dto.getTrdLicno());
									smsEmailDto.setServName(MainetConstants.TradeLicense.RENEWAL_SERVICE_SHORT_CODE);
									smsEmailDto.setAppName(ownDto.getTroName());
									smsEmailDto.setDueDt(Utility.dateToString(dto.getTrdLictoDate()));
									smsEmailDto.setReferenceNo("");
									smsEmailDto.setUserId(dto.getCreatedBy());
									if (dto.getUpdatedBy() != null) {
										smsEmailDto.setUserId(dto.getUpdatedBy());
									}
									String menuUrl = "RenewalRemainderNotice.html";
									try {
										iSMSAndEmailService.sendEmailSMS("ML", menuUrl,
												PrefixConstants.SMS_EMAIL_ALERT_TYPE.SUBMITTED, smsEmailDto, org, langId);
									} catch (Exception e) {
										LOGGER.error("Exception occur while sending SMS and Email for License NO:"+" "+dto.getTrdLicno(), e);
									}

								});
							}
						}

					});
		}

	}

	private Date getDueDate(Date date, Integer days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -days);
		return calendar.getTime();
	}
}
