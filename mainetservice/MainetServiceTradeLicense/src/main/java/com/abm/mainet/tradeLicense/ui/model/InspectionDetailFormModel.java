package com.abm.mainet.tradeLicense.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;
import com.abm.mainet.tradeLicense.dto.InspectionDetailDto;
import com.abm.mainet.tradeLicense.dto.NoticeDetailDto;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;
import com.abm.mainet.tradeLicense.service.InspectionDetailService;
import com.abm.mainet.tradeLicense.ui.validator.InspectionDetailValidator;

@Component
@Scope("session")
public class InspectionDetailFormModel extends AbstractFormModel {

	private static final long serialVersionUID = 1L;

	@Autowired
	private InspectionDetailService inspectionDetailService;

	@Resource
	private ISMSAndEmailService ismsAndEmailService;

	private TradeMasterDetailDTO tradeDetailDTO = new TradeMasterDetailDTO();

	private List<TradeMasterDetailDTO> tradeMasterDetailDTOList = new ArrayList<>();

	private InspectionDetailDto inspectionDetailDto = new InspectionDetailDto();

	private List<NoticeDetailDto> noticeDetailDtoList = new ArrayList<>();

	private NoticeDetailDto noticeDetailDto = new NoticeDetailDto();

	public TradeMasterDetailDTO getTradeDetailDTO() {
		return tradeDetailDTO;
	}

	public void setTradeDetailDTO(TradeMasterDetailDTO tradeDetailDTO) {
		this.tradeDetailDTO = tradeDetailDTO;
	}

	public InspectionDetailDto getInspectionDetailDto() {
		return inspectionDetailDto;
	}

	public void setInspectionDetailDto(InspectionDetailDto inspectionDetailDto) {
		this.inspectionDetailDto = inspectionDetailDto;
	}

	public List<TradeMasterDetailDTO> getTradeMasterDetailDTOList() {
		return tradeMasterDetailDTOList;
	}

	public void setTradeMasterDetailDTOList(List<TradeMasterDetailDTO> tradeMasterDetailDTOList) {
		this.tradeMasterDetailDTOList = tradeMasterDetailDTOList;
	}

	public List<NoticeDetailDto> getNoticeDetailDtoList() {
		return noticeDetailDtoList;
	}

	public void setNoticeDetailDtoList(List<NoticeDetailDto> noticeDetailDtoList) {
		this.noticeDetailDtoList = noticeDetailDtoList;
	}

	public NoticeDetailDto getNoticeDetailDto() {
		return noticeDetailDto;
	}

	public void setNoticeDetailDto(NoticeDetailDto noticeDetailDto) {
		this.noticeDetailDto = noticeDetailDto;
	}

	public boolean saveForm() {
		boolean status = true;
		validateBean(inspectionDetailDto, InspectionDetailValidator.class);
		if (hasValidationErrors()) {
			return false;
		}
		Employee employee = getUserSession().getEmployee();
		InspectionDetailDto inspectionDto = getInspectionDetailDto();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		inspectionDto.setCreatedBy(employee.getEmpId());
		inspectionDto.setCreatedDate(new Date());
		inspectionDto.setUpdatedBy(employee.getEmpId());
		inspectionDto.setUpdatedDate(new Date());
		inspectionDto.setLgIpMac(employee.getEmppiservername());
		inspectionDto.setLgIpMacUpd(employee.getEmppiservername());
		inspectionDto.setOrgId(orgId);
		Long inspNo = inspectionDetailService.generateInspectionNo(orgId);
		inspectionDto.setInspNo(inspNo);
		inspectionDetailService.saveInspection(inspectionDto, this);
		this.setSuccessMessage(getAppSession().getMessage("success.message") + "" + inspectionDto.getInspNo());
		return status;

	}

	public boolean saveNoticeDetails() {
		boolean status = false;

		Employee employee = getUserSession().getEmployee();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Date date = new Date();
		NoticeDetailDto noticeDto = getNoticeDetailDto();
		List<NoticeDetailDto> noticeDetDtoList = new ArrayList<>();
		LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp("SCN", "NTP",
				UserSession.getCurrent().getOrganisation());
		Long noticeNo = inspectionDetailService.generateNoticeNo(orgId);
		for (NoticeDetailDto noticeDetailDto : getNoticeDetailDtoList()) {
			NoticeDetailDto dto = new NoticeDetailDto();
			dto.setCreatedBy(employee.getEmpId());
			dto.setCreatedDate(date);
			dto.setUpdatedBy(employee.getEmpId());
			dto.setUpdatedDate(date);
			dto.setLgIpMac(employee.getEmppiservername());
			dto.setLgIpMacUpd(employee.getEmppiservername());
			dto.setOrgId(orgId);
			dto.setLicNo(noticeDto.getLicNo());
			dto.setNoticeDate(date);
			dto.setReason(noticeDetailDto.getReason());
			dto.setNoticeTypeId(lookUp.getLookUpId());
			dto.setNoticeNo(noticeNo);
			noticeDetDtoList.add(dto);
		}
		inspectionDetailService.saveNoticeDetails(noticeDetDtoList, this);
		this.getNoticeDetailDto().setNoticeNo(noticeNo);
		// D#129843
		sendSMSEmail();
		this.setSuccessMessage(getAppSession().getMessage("success.notice.message"));
		status = true;
		return status;
	}

	// D#129843
	private void sendSMSEmail() {
		final SMSAndEmailDTO dto = new SMSAndEmailDTO();
		dto.setAppName(getInspectionDetailDto().getApplicantName());
		dto.setMobnumber(getInspectionDetailDto().getMobNo());
		dto.setNoticeNo(getNoticeDetailDto().getNoticeNo().toString());
		//setting License no in sms email dto
		dto.setLicNo(getNoticeDetailDto().getLicNo());
		dto.setEmail(getInspectionDetailDto().getEmailId());
		String paymentUrl = "InspectionDetailForm.html";
		Organisation org = new Organisation();
		org.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
		int langId = Utility.getDefaultLanguageId(org);
		dto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		ismsAndEmailService.sendEmailSMS(MainetConstants.TradeLicense.MARKET_LICENSE, paymentUrl,
				PrefixConstants.SMS_EMAIL_ALERT_TYPE.SUBMITTED, dto, org, langId);

	}

}
