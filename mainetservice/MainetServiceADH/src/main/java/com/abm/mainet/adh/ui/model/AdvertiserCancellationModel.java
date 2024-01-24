package com.abm.mainet.adh.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.adh.dto.AdvertiserMasterDto;
import com.abm.mainet.adh.service.IAdvertiserMasterService;
import com.abm.mainet.adh.ui.validator.AdvertiserCancellationValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

/**
 * @author cherupelli.srikanth
 * @since 25 september 2019
 */
@Component
@Scope("session")
public class AdvertiserCancellationModel extends AbstractFormModel {

    @Autowired
    IAdvertiserMasterService advertiserMasterService;

    private AdvertiserMasterDto advertiserDto = new AdvertiserMasterDto();

    private List<String[]> agencyLicNoAndNameList = new ArrayList<>();

    public boolean saveForm() {
	boolean status = false;
	validateBean(this, AdvertiserCancellationValidator.class);
	if (hasValidationErrors()) {
	    return false;
	}
	advertiserDto.setAgencyStatus(MainetConstants.FlagC);
	advertiserMasterService.updateAdvertiserMasterData(advertiserDto);
	sendSmsEmail(advertiserDto, "AdvertiserCancellation.html",
		MainetConstants.AdvertisingAndHoarding.REJECTED_MESSAGE);
	setSuccessMessage(ApplicationSession.getInstance().getMessage("Cancelled Succesfully"));
	status = true;
	return status;
    }

    public void sendSmsEmail(AdvertiserMasterDto masterDto, String menuUrl, String msgType) {
	ServiceMaster serviceMaster = ApplicationContextProvider.getApplicationContext()
		.getBean(ServiceMasterService.class)
		.getServiceByShortName(MainetConstants.AdvertisingAndHoarding.AGENCY_REG_SHORT_CODE,
			UserSession.getCurrent().getOrganisation().getOrgid());
	SMSAndEmailDTO smsDto = new SMSAndEmailDTO();
	smsDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
	smsDto.setLangId(UserSession.getCurrent().getLanguageId());
	smsDto.setAppNo(String.valueOf(masterDto.getAgencyLicNo()));
	smsDto.setServName(serviceMaster.getSmServiceName());
	smsDto.setMobnumber(masterDto.getAgencyContactNo());
	smsDto.setAppName(masterDto.getAgencyOwner());
	smsDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
	if (StringUtils.isNotBlank(masterDto.getAgencyEmail())) {
	    smsDto.setEmail(masterDto.getAgencyEmail());
	}

	ApplicationContextProvider.getApplicationContext().getBean(ISMSAndEmailService.class).sendEmailSMS(
		MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE, menuUrl, msgType, smsDto,
		UserSession.getCurrent().getOrganisation(), UserSession.getCurrent().getLanguageId());

    }

    public AdvertiserMasterDto getAdvertiserDto() {
	return advertiserDto;
    }

    public void setAdvertiserDto(AdvertiserMasterDto advertiserDto) {
	this.advertiserDto = advertiserDto;
    }

    public List<String[]> getAgencyLicNoAndNameList() {
	return agencyLicNoAndNameList;
    }

    public void setAgencyLicNoAndNameList(List<String[]> agencyLicNoAndNameList) {
	this.agencyLicNoAndNameList = agencyLicNoAndNameList;
    }

}
