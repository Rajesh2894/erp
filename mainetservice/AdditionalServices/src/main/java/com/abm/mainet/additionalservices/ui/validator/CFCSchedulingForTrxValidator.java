package com.abm.mainet.additionalservices.ui.validator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.abm.mainet.additionalservices.dto.CFCCounterMasterDto;
import com.abm.mainet.additionalservices.service.CFCSchedulingTrxService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;

@Component
public class CFCSchedulingForTrxValidator extends BaseEntityValidator<List<CFCCounterMasterDto>> {

	//121825  Added Validation for Date and time for comparison
	
	@Autowired
    private CFCSchedulingTrxService tbCfcservice;
	
	@Override
	protected void performValidations(List<CFCCounterMasterDto> cfcCounterDtos,
			EntityValidationContext<List<CFCCounterMasterDto>> entityValidationContext) {
		final ApplicationSession session = ApplicationSession.getInstance();
		boolean flag = false;
	
		//Defect #127209 - to check duplicate counter scheduling
		for (CFCCounterMasterDto dto : cfcCounterDtos) {
			boolean reuslt = false;
			for (int i = 0; i < dto.getCfcCounterScheduleDtos().size(); i++) {
				Date fromDate = stringToDateConvert(dto.getCfcCounterScheduleDtos().get(i).getCsFromTime());
				Date toTime = stringToDateConvert(dto.getCfcCounterScheduleDtos().get(i).getCsToTime());
				if (StringUtils.isNotEmpty(dto.getCmCollncentreno()))
					reuslt = tbCfcservice.getScheduleDetails(dto.getCuCountcentreno(), dto.getCmCollncentreno(),
							UserSession.getCurrent().getOrganisation().getOrgid(), fromDate, toTime);
				if (reuslt == true) {
					entityValidationContext.addOptionConstraint(
							getApplicationSession().getMessage("cfc.counter.already.present"));
					break;
				}
		
			}

		}
     
		cfcCounterDtos.forEach(dto -> {			
 			for (int i = 0; i < dto.getCfcCounterScheduleDtos().size(); i++) {
				for (int j = i + 1; j < dto.getCfcCounterScheduleDtos().size(); j++) {

					Date fromDate = stringToDateConvert(dto.getCfcCounterScheduleDtos().get(i).getCsFromTime());
					Date toTime = stringToDateConvert(dto.getCfcCounterScheduleDtos().get(i).getCsToTime());
					long fromTime = fromDate.getTime();
					long toDateTime = toTime.getTime();
				
					
					Date checkFromTime = stringToDateConvert(dto.getCfcCounterScheduleDtos().get(j).getCsFromTime());
					Date checkTotime = stringToDateConvert(dto.getCfcCounterScheduleDtos().get(j).getCsToTime());
					long checkFromTimeLong=checkFromTime.getTime();
					long checkToTimeLong=checkTotime.getTime();
					//#132448
					if (checkFromTime.equals(toTime) && checkTotime.after(toTime) || (checkFromTime.equals(toTime) &&  checkTotime.after(toTime))) {
						entityValidationContext.addOptionConstraint(
								getApplicationSession().getMessage("SFT.validation.duplicate"));
					}
					if ((checkFromTime.equals(fromDate) && checkTotime.equals(toTime)) || (checkFromTime.before(toTime) && checkTotime.equals(toTime))) {
						entityValidationContext.addOptionConstraint(
								getApplicationSession().getMessage("SFT.validation.duplicate"));
					}
					if(checkTotime.equals(fromDate)) {
						entityValidationContext.addOptionConstraint(
								getApplicationSession().getMessage("SFT.validation.duplicate"));
					}
				    if (checkTotime.before(fromDate) && checkTotime.after(checkTotime)) {
				    	entityValidationContext.addOptionConstraint(
								getApplicationSession().getMessage("SFT.validation.duplicate"));
				    }
				    if (checkFromTime.equals(fromDate) && checkTotime.after(toTime)) {
					 entityValidationContext.addOptionConstraint(
								getApplicationSession().getMessage("SFT.validation.duplicate"));
				     }
				
					if (checkFromTime.before(toTime) && checkTotime.after(toTime)) {
						entityValidationContext
								.addOptionConstraint(getApplicationSession().getMessage("SFT.validation.duplicate"));
					}

					if (checkFromTime.before(fromDate) && checkFromTime.after(toTime)) {
						entityValidationContext.addOptionConstraint(
								getApplicationSession().getMessage("SFT.validation.duplicate"));
					}
					
					
					if (checkFromTime.before(fromDate) && checkTotime.after(toTime)) {
						entityValidationContext.addOptionConstraint(
								getApplicationSession().getMessage("SFT.validation.duplicate"));
					}
					if (checkTotime.getTime() < toTime.getTime() && checkTotime.getTime() > fromDate.getTime()) {
						entityValidationContext.addOptionConstraint(
								getApplicationSession().getMessage("SFT.validation.duplicate"));
					}
					if (checkFromTime.equals(fromDate) && checkFromTime.before(toTime)){
						if(fromTime < checkFromTimeLong) {
							entityValidationContext.addOptionConstraint(
									getApplicationSession().getMessage("SFT.validation.duplicate"));
						}
					}
					if (checkTotime.equals(toTime)  && checkTotime.after(fromDate)){
						if(checkToTimeLong < toDateTime) {
							entityValidationContext.addOptionConstraint(
									getApplicationSession().getMessage("SFT.validation.duplicate"));
						}
					}
			
					if(checkFromTime.equals(fromDate) && checkTotime.equals(toTime)) {
						if(fromTime < checkFromTimeLong || checkToTimeLong < toDateTime) {
							entityValidationContext.addOptionConstraint(
									getApplicationSession().getMessage("SFT.validation.duplicate"));
						}
					}

					/*
					 * String fromTime = dto.getCfcCounterScheduleDtos().get(i).getCsFromTime();
					 * String toTime1 = dto.getCfcCounterScheduleDtos().get(i).getCsToTime();
					 * fromTime=fromTime.replace("/", "-"); fromTime=fromTime.replace(" ", "T");
					 * LocalDateTime localFromTime = LocalDateTime.parse(fromTime); LocalDateTime
					 * localToTime = LocalDateTime.parse(toTime1);
					 * 
					 * String checkFromTime1 =
					 * dto.getCfcCounterScheduleDtos().get(j).getCsFromTime(); String checkFromTime2
					 * = dto.getCfcCounterScheduleDtos().get(j).getCsToTime();
					 * 
					 * LocalDateTime localFromTimeCheck = LocalDateTime.parse(checkFromTime1);
					 * LocalDateTime localToTimeCheck = LocalDateTime.parse(checkFromTime2);
					 * 
					 * 
					 * 
					 * if (localFromTimeCheck.isBefore(localFromTime) &&
					 * localToTimeCheck.isAfter(localToTime)) {
					 * entityValidationContext.addOptionConstraint( getApplicationSession().
					 * getMessage("Entry already exist for given time at row" + i)); }
					 */
							
				}
			}
		});
		
	}

	public Date stringToDateConvert(String time) {
		DateFormat formatter = new SimpleDateFormat(MainetConstants.WorksManagement.DATE_FORMAT);
		Date timeValue = null;
		if (time != null) {
			try {
				timeValue = formatter.parse(time);
				// timeValue = new Date(formatter.parse(time).getTime());
			} catch (ParseException e) {
				e.printStackTrace();
				throw new FrameworkException(e);
			}
		}
		return timeValue;

	}
}
