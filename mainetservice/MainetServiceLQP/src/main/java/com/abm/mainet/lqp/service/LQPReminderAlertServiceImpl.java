package com.abm.mainet.lqp.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.lqp.dto.QueryRegistrationMasterDto;
import com.abm.mainet.quartz.domain.QuartzSchedulerMaster;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

@Service
public class LQPReminderAlertServiceImpl implements ILQPReminderAlertService{
	
	@Autowired
	IQueryRegistrationService iQueryRegistrationService;
	
	@Autowired
	ISMSAndEmailService iSMSAndEmailService;
	
	@Transactional
	@Override
	public void sendReminderAlertMsg(QuartzSchedulerMaster runtimeBean, List<Object> parameterList) {
		
		 
		Long orgId = runtimeBean.getOrgId().getOrgid();
		Organisation org = new Organisation();
		org.setOrgid(orgId);
        Integer langId = 1;
        
        List<QueryRegistrationMasterDto> queryRegistrationMasterDTOs = iQueryRegistrationService
                .fetchQueryRegisterMasterDataByOrgId(orgId);
        
        final LookUp lookUp = CommonMasterUtility.getLookUpFromPrefixLookUpDesc(MainetConstants.LQP.LQP_DEPT_CODE,
        		PrefixConstants.LQP_PREFIX.ALERT_REMINDER_DAYS,langId, org);
        Integer noOfDays = Integer.parseInt(lookUp.getLookUpCode());
        
        queryRegistrationMasterDTOs.forEach(queryRegistrationMasterDTO -> {
           
                if (queryRegistrationMasterDTO.getDeadlineDate() != null) {
                	
                	Date dueDate = getDueDate(queryRegistrationMasterDTO.getDeadlineDate(), noOfDays);
                	
                    // compare due date and current date if same then send mail&sms
                    if (DateUtils.isSameDay(new Date(), dueDate)) {
                        // send MSG and mail
                        final SMSAndEmailDTO dto = new SMSAndEmailDTO();
                        dto.setMobnumber(queryRegistrationMasterDTO.getEmployee().getEmpmobno());
                        dto.setEmail(queryRegistrationMasterDTO.getEmployee().getEmpemail());
                        dto.setServName(MainetConstants.LQP.SERVICE_CODE.LQE);
                        dto.setAppName("Notification alert");
                        //Data added for message template
                        dto.setAppNo(queryRegistrationMasterDTO.getQuestionId());
                        dto.setDueDt(Utility.dateToString(queryRegistrationMasterDTO.getDeadlineDate()));
                        dto.setFrmDt(Utility.dateToString(queryRegistrationMasterDTO.getQuestionDate()));
                        dto.setReferenceNo(queryRegistrationMasterDTO.getQuestionId());
                        
                        dto.setUserId(queryRegistrationMasterDTO.getEmployee().getEmpId());
                        String menuUrl = "LegislativeAnswer.html";
                       iSMSAndEmailService.sendEmailSMS(MainetConstants.LQP.LQP_DEPT_CODE,menuUrl,
                                PrefixConstants.SMS_EMAIL_ALERT_TYPE.SUBMITTED, dto, org, langId);
                    }

                }
            });
	}
	
	private Date getDueDate(Date date, Integer days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -days);
        return calendar.getTime();
    }
}
