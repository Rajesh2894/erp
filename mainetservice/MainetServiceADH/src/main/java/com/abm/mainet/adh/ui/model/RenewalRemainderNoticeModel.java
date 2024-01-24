package com.abm.mainet.adh.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.adh.dto.AdvertiserMasterDto;
import com.abm.mainet.adh.dto.RenewalRemainderNoticeDto;
import com.abm.mainet.adh.service.IRenewalRemainderNoticeService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

/**
 * @author cherupelli.srikanth
 * @since 27 september 2019
 */
@Component
@Scope("session")
public class RenewalRemainderNoticeModel extends AbstractFormModel {

    @Autowired
    private IRenewalRemainderNoticeService renewalRemainderNoticeService;

    private static final long serialVersionUID = 1364784739777233937L;

    private List<AdvertiserMasterDto> advertiserDtoList = new ArrayList<>();

    private RenewalRemainderNoticeDto remainderNoticeDto = new RenewalRemainderNoticeDto();

    private AdvertiserMasterDto advertiserDto = new AdvertiserMasterDto();

    private LookUp noticeTypeLookUp = new LookUp();

    private Long advertiserType;

    private String advertiserDisplayFlag;

    private String remainNoticeExistFlag;

    public boolean saveNoticeForm() {

	boolean status = false;

	if (remainderNoticeDto.getNoticeId() == null) {
	    remainderNoticeDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
	    remainderNoticeDto.setCreatedDate(new Date());
	    remainderNoticeDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
	    remainderNoticeDto.setLgIpMac(getClientIpAddress());
	    remainderNoticeDto.setNoticeNo(renewalRemainderNoticeService.genearateNoticeNo(
		    UserSession.getCurrent().getOrganisation(), getNoticeTypeLookUp().getLookUpId()));
	    remainderNoticeDto.setNoticeType(getNoticeTypeLookUp().getLookUpId());

	    remainderNoticeDto.setAgencyId(advertiserDto.getAgencyId());
	    renewalRemainderNoticeService.saveRenewalRemainderNotice(remainderNoticeDto);
	    status = true;
	} else {
	    remainderNoticeDto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
	    remainderNoticeDto.setUpdatedDate(new Date());
	    remainderNoticeDto.setLgIpMacUpd(getClientIpAddress());
	    renewalRemainderNoticeService.updateRenealRemainderNotice(remainderNoticeDto);
	    status = true;
	}

	return status;
    }

    public void sendSmsEmail(AdvertiserMasterDto masterDto, String menuUrl, String msgType) {
	ServiceMaster serviceMaster = ApplicationContextProvider.getApplicationContext()
		.getBean(ServiceMasterService.class)
		.getServiceByShortName(MainetConstants.AdvertisingAndHoarding.AGENCY_REG_REN_SHORT_CODE,
			UserSession.getCurrent().getOrganisation().getOrgid());
	SMSAndEmailDTO smsDto = new SMSAndEmailDTO();
	smsDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
	smsDto.setLangId(UserSession.getCurrent().getLanguageId());
	smsDto.setAppNo(String.valueOf(masterDto.getAgencyLicNo()));
	smsDto.setServName(serviceMaster.getSmServiceName());
	smsDto.setMobnumber(masterDto.getAgencyContactNo());
	smsDto.setAppName(masterDto.getAgencyOwner());
	smsDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
	smsDto.setFrmDt(masterDto.getAgencyLicenseFromDate());
	smsDto.setToDt(masterDto.getAgencyLicenseToDate());
	smsDto.setDueDt(masterDto.getAgencyLicenseToDate());
	if (StringUtils.isNotBlank(masterDto.getAgencyEmail())) {
	    smsDto.setEmail(masterDto.getAgencyEmail());
	}

	ApplicationContextProvider.getApplicationContext().getBean(ISMSAndEmailService.class).sendEmailSMS(
		MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE, menuUrl, msgType, smsDto,
		UserSession.getCurrent().getOrganisation(), UserSession.getCurrent().getLanguageId());

    }

    public List<AdvertiserMasterDto> getAdvertiserDtoList() {
	return advertiserDtoList;
    }

    public void setAdvertiserDtoList(List<AdvertiserMasterDto> advertiserDtoList) {
	this.advertiserDtoList = advertiserDtoList;
    }

    public LookUp getNoticeTypeLookUp() {
	return noticeTypeLookUp;
    }

    public void setNoticeTypeLookUp(LookUp noticeTypeLookUp) {
	this.noticeTypeLookUp = noticeTypeLookUp;
    }

    public RenewalRemainderNoticeDto getRemainderNoticeDto() {
	return remainderNoticeDto;
    }

    public void setRemainderNoticeDto(RenewalRemainderNoticeDto remainderNoticeDto) {
	this.remainderNoticeDto = remainderNoticeDto;
    }

    public AdvertiserMasterDto getAdvertiserDto() {
	return advertiserDto;
    }

    public void setAdvertiserDto(AdvertiserMasterDto advertiserDto) {
	this.advertiserDto = advertiserDto;
    }

    public Long getAdvertiserType() {
	return advertiserType;
    }

    public void setAdvertiserType(Long advertiserType) {
	this.advertiserType = advertiserType;
    }

    public String getAdvertiserDisplayFlag() {
	return advertiserDisplayFlag;
    }

    public void setAdvertiserDisplayFlag(String advertiserDisplayFlag) {
	this.advertiserDisplayFlag = advertiserDisplayFlag;
    }

    public String getRemainNoticeExistFlag() {
	return remainNoticeExistFlag;
    }

    public void setRemainNoticeExistFlag(String remainNoticeExistFlag) {
	this.remainNoticeExistFlag = remainNoticeExistFlag;
    }

}
