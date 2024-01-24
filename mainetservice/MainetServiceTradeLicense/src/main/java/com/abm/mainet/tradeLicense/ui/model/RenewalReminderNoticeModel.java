package com.abm.mainet.tradeLicense.ui.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;
import com.abm.mainet.tradeLicense.dto.NoticeDetailDto;
import com.abm.mainet.tradeLicense.dto.RenewalMasterDetailDTO;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;
import com.abm.mainet.tradeLicense.service.InspectionDetailService;

@Component
@Scope("session")
public class RenewalReminderNoticeModel extends AbstractFormModel{

	private static final long serialVersionUID = 1L;
	
	@Autowired
     private InspectionDetailService inspectionDetailService;
	@Autowired
	 private  ServiceMasterService serviceMaster;
	
	 @Autowired
	 private ISMSAndEmailService iSMSAndEmailService;
	
	private List<LookUp> triCodList1 = new ArrayList<LookUp>();
	private List<LookUp> triCodList2 = new ArrayList<LookUp>();
	List<TradeMasterDetailDTO> trdMasDtoList = new ArrayList<>();
	NoticeDetailDto noticeDetailDto = new NoticeDetailDto();
	TradeMasterDetailDTO trdMastDto = new TradeMasterDetailDTO();
	private String dataDisplayFlag;
	private RenewalMasterDetailDTO renewalDto = new RenewalMasterDetailDTO(); 
	private Long subCatId;
	private String ONlsOrgname;
	private String designation;
	
	 
	public List<TradeMasterDetailDTO> getTrdMasDtoList() {
		return trdMasDtoList;
	}
	public void setTrdMasDtoList(List<TradeMasterDetailDTO> trdMasDtoList) {
		this.trdMasDtoList = trdMasDtoList;
	}

	public RenewalMasterDetailDTO getRenewalDto() {
		return renewalDto;
	}
	public void setRenewalDto(RenewalMasterDetailDTO renewalDto) {
		this.renewalDto = renewalDto;
	}
	public List<LookUp> getTriCodList1() {
		return triCodList1;
	}
	public void setTriCodList1(List<LookUp> triCodList1) {
		this.triCodList1 = triCodList1;
	}
	public List<LookUp> getTriCodList2() {
		return triCodList2;
	}
	public void setTriCodList2(List<LookUp> triCodList2) {
		this.triCodList2 = triCodList2;
	}
	public String getDataDisplayFlag() {
		return dataDisplayFlag;
	}
	public void setDataDisplayFlag(String dataDisplayFlag) {
		this.dataDisplayFlag = dataDisplayFlag;
	}
	
	
	public NoticeDetailDto getNoticeDetailDto() {
		return noticeDetailDto;
	}
	public void setNoticeDetailDto(NoticeDetailDto noticeDetailDto) {
		this.noticeDetailDto = noticeDetailDto;
	}
	
	public TradeMasterDetailDTO getTrdMastDto() {
		return trdMastDto;
	}
	public void setTrdMastDto(TradeMasterDetailDTO trdMastDto) {
		this.trdMastDto = trdMastDto;
	}
	
	public Long getSubCatId() {
		return subCatId;
	}
	public void setSubCatId(Long subCatId) {
		this.subCatId = subCatId;
	}
	
	public String getONlsOrgname() {
		return ONlsOrgname;
	}
	public void setONlsOrgname(String oNlsOrgname) {
		ONlsOrgname = oNlsOrgname;
	}
	
	
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	
	
	public boolean saveNoticeForm() {
		boolean status = false;
		Employee employee = getUserSession().getEmployee();
		if (noticeDetailDto.getNtId() == null) {
			Long orgId= UserSession.getCurrent().getOrganisation().getOrgid();		
			noticeDetailDto.setOrgId(orgId);
			noticeDetailDto.setCreatedDate(new Date());
			noticeDetailDto.setCreatedBy(employee.getEmpId());
			noticeDetailDto.setLgIpMac(employee.getEmppiservername());
			noticeDetailDto.setNoticeNo(inspectionDetailService.generateNoticeNo(orgId));
			noticeDetailDto.setTrdId(trdMastDto.getTrdId());
			LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.LookUpPrefix.RRN, PrefixConstants.LookUpPrefix.NTP,
					UserSession.getCurrent().getOrganisation());
			noticeDetailDto.setNoticeTypeId(lookUp.getLookUpId());
			noticeDetailDto.setNoticeDate(new Date());
			inspectionDetailService.saveRenewalReminderNotice(noticeDetailDto);
			setSuccessMessage(ApplicationSession.getInstance().getMessage("trade.renewal.success.msg"));
		    status = true;
		} else {
			noticeDetailDto.setUpdatedBy(employee.getEmpId());
			noticeDetailDto.setUpdatedDate(new Date());
			noticeDetailDto.setLgIpMacUpd(employee.getEmppiservername());
			inspectionDetailService.updateRenewalReminderNotice(noticeDetailDto);
			setSuccessMessage(ApplicationSession.getInstance().getMessage("trade.renewal.success.msg"));
		    status = true;
		}
		return status;
		
	}
	
	public void sendSmsEmail(TradeMasterDetailDTO trdMastDTO, String menuUrl, String msgType) {
		Long orgId= UserSession.getCurrent().getOrganisation().getOrgid();		
		ServiceMaster sm = serviceMaster.getServiceByShortName(MainetConstants.TradeLicense.RENEWAL_SERVICE_SHORT_CODE,orgId);
		SMSAndEmailDTO smsDto = new SMSAndEmailDTO();
		smsDto.setOrgId(orgId);
		smsDto.setLangId(UserSession.getCurrent().getLanguageId());
		smsDto.setServName(sm.getSmServiceName());
		smsDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		smsDto.setAppNo(String.valueOf(trdMastDTO.getTrdLicno()));
		smsDto.setMobnumber(trdMastDTO.getTradeLicenseOwnerdetailDTO().get(0).getTroMobileno());
		if(trdMastDTO.getTradeLicenseOwnerdetailDTO().get(0).getTroName() != null && trdMastDTO.getTradeLicenseOwnerdetailDTO().get(0).getTroMname() != null) {
		String applName= trdMastDTO.getTradeLicenseOwnerdetailDTO().get(0).getTroName() +" " + trdMastDTO.getTradeLicenseOwnerdetailDTO().get(0).getTroMname();
		smsDto.setAppName(applName);}
		smsDto.setFrmDt(new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(trdMastDTO.getTrdLicfromDate()));
		smsDto.setToDt(new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(trdMastDTO.getTrdLictoDate()));
		smsDto.setDueDt(new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(trdMastDTO.getTrdLictoDate()));		
		iSMSAndEmailService.sendEmailSMS(MainetConstants.TradeLicense.MARKET_LICENSE, menuUrl, msgType, smsDto,
				UserSession.getCurrent().getOrganisation(), UserSession.getCurrent().getLanguageId());
	}
	
	
    
    

}
