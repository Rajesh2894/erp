/**
 * 
 */
package com.abm.mainet.additional.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.additional.dto.EChallanItemDetailsDto;
import com.abm.mainet.additional.dto.EChallanMasterDto;
import com.abm.mainet.additional.service.EChallanEntryService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.UserSession;

/**
 * @author cherupelli.srikanth
 *
 */

@Component
@Scope("session")
public class EChallanEntryModel extends AbstractFormModel{
	
	private static final long serialVersionUID = 5557913163427738405L;

	@Autowired
	private EChallanEntryService eChallanService;
	
	private EChallanMasterDto challanMasterDto = new EChallanMasterDto();
	private List<EChallanMasterDto> challanMasterDtoList = new ArrayList();
	private EChallanItemDetailsDto challanItemDetailsDto = new EChallanItemDetailsDto();
	private List<EChallanItemDetailsDto> challanItemDetailsDtoList = new ArrayList();
	
	private ApplicantDetailDTO applicantDetailDto = new ApplicantDetailDTO();
	private ServiceMaster serviceMaster = new ServiceMaster();
	private double amountToPay;
	private String formType;
	
	private String saveMode;
	
	private ChallanReceiptPrintDTO receiptDTO = null;
	
	private List<DocumentDetailsVO> documentList = new ArrayList<>();

	
	
	@Override
	public boolean saveForm() {
		boolean status = false;
		final UserSession session = UserSession.getCurrent();
		// to save data and for receipt

			if (challanMasterDto.getChallanId() == null) {
				challanMasterDto.setOrgid(session.getOrganisation().getOrgid());
				challanMasterDto.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
				challanMasterDto.setCreatedDate(new Date());
				challanMasterDto.setCreatedBy(session.getEmployee().getEmpId());
				challanMasterDto.setUpdatedDate(new Date());
				challanMasterDto.setUpdatedBy(session.getEmployee().getEmpId());
				challanMasterDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
				challanMasterDto.setLangId((long) session.getLanguageId());

				if (challanMasterDto.getEchallanItemDetDto() != null) {
					for (EChallanItemDetailsDto itemDetails : challanItemDetailsDtoList) {
						itemDetails.setOrgid(session.getOrganisation().getOrgid());
						itemDetails.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
						itemDetails.setCreatedDate(new Date());
						itemDetails.setCreatedBy(session.getEmployee().getEmpId());
						itemDetails.setUpdatedDate(new Date());
						itemDetails.setUpdatedBy(session.getEmployee().getEmpId());
						itemDetails.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
						itemDetails.setLangId((long) session.getLanguageId());
					}
				}
			}

			boolean saveEChallanEntry = eChallanService.saveEChallanEntry(challanMasterDto);
			
				setSuccessMessage(ApplicationSession.getInstance()
		                .getMessage("Your Raid No." + MainetConstants.WHITE_SPACE + challanMasterDto.getRaidNo()
		                        + MainetConstants.WHITE_SPACE + "has been submitted successfully."));
			
		
		      
		status = true;
		return status;
	}
	
	public EChallanMasterDto getChallanMasterDto() {
		return challanMasterDto;
	}

	public void setChallanMasterDto(EChallanMasterDto challanMasterDto) {
		this.challanMasterDto = challanMasterDto;
	}

	public List<EChallanMasterDto> getChallanMasterDtoList() {
		return challanMasterDtoList;
	}

	public void setChallanMasterDtoList(List<EChallanMasterDto> challanMasterDtoList) {
		this.challanMasterDtoList = challanMasterDtoList;
	}

	public EChallanItemDetailsDto getChallanItemDetailsDto() {
		return challanItemDetailsDto;
	}

	public void setChallanItemDetailsDto(EChallanItemDetailsDto challanItemDetailsDto) {
		this.challanItemDetailsDto = challanItemDetailsDto;
	}

	public List<EChallanItemDetailsDto> getChallanItemDetailsDtoList() {
		return challanItemDetailsDtoList;
	}

	public void setChallanItemDetailsDtoList(List<EChallanItemDetailsDto> challanItemDetailsDtoList) {
		this.challanItemDetailsDtoList = challanItemDetailsDtoList;
	}

	public ApplicantDetailDTO getApplicantDetailDto() {
		return applicantDetailDto;
	}

	public void setApplicantDetailDto(ApplicantDetailDTO applicantDetailDto) {
		this.applicantDetailDto = applicantDetailDto;
	}

	public ServiceMaster getServiceMaster() {
		return serviceMaster;
	}

	public void setServiceMaster(ServiceMaster serviceMaster) {
		this.serviceMaster = serviceMaster;
	}

	public double getAmountToPay() {
		return amountToPay;
	}

	public void setAmountToPay(double amountToPay) {
		this.amountToPay = amountToPay;
	}

	public String getFormType() {
		return formType;
	}

	public void setFormType(String formType) {
		this.formType = formType;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public ChallanReceiptPrintDTO getReceiptDTO() {
		return receiptDTO;
	}

	public void setReceiptDTO(ChallanReceiptPrintDTO receiptDTO) {
		this.receiptDTO = receiptDTO;
	}

	public List<DocumentDetailsVO> getDocumentList() {
		return documentList;
	}

	public void setDocumentList(List<DocumentDetailsVO> documentList) {
		this.documentList = documentList;
	}
}
