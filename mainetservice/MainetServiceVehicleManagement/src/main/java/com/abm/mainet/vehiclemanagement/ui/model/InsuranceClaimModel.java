package com.abm.mainet.vehiclemanagement.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.dto.FileUploadDTO;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.vehiclemanagement.Constants.Constants;
import com.abm.mainet.vehiclemanagement.dto.GenVehicleMasterDTO;
import com.abm.mainet.vehiclemanagement.dto.InsuranceClaimDTO;
import com.abm.mainet.vehiclemanagement.dto.InsuranceDetailsDTO;
import com.abm.mainet.vehiclemanagement.service.IInsuranceClaimService;

@Component
@Scope("session")
public class InsuranceClaimModel extends AbstractFormModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private InsuranceDetailsDTO insuranceDetailsDto;

	private InsuranceClaimDTO insuranceClaimDto;

	private List<GenVehicleMasterDTO> vehicleMasterList = new ArrayList<>();
	
	List<TbAcVendormaster> vendorList;
	
	private String saveMode;
	
	private Long DeptId;
	
	private String departments;
	
	private List<DocumentDetailsVO> attachments = new ArrayList<>();
	
	 private List<DocumentDetailsVO> documents = new ArrayList<>();
	 
	 private List<AttachDocs> attachDocsList = new ArrayList<>();

	@Autowired
	private IInsuranceClaimService insuranceClaimService;
	
	@Resource
	IFileUploadService fileUpload;
	
	@Resource
    private TbAcVendormasterService tbAcVendormasterService;

	@Override
	public boolean saveForm() {

		Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
		Date createdDate = new Date();
		String lgIpMac = UserSession.getCurrent().getEmployee().getEmppiservername();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		InsuranceClaimDTO dto=new InsuranceClaimDTO();
		if (insuranceClaimDto.getInsuranceClaimId() == null) {
			insuranceClaimDto.setOrgid(orgId);
			insuranceClaimDto.setCreatedBy(createdBy);
			insuranceClaimDto.setCreatedDate(createdDate);
			insuranceClaimDto.setLgIpMac(lgIpMac);
			 dto=insuranceClaimService.saveClaim(insuranceClaimDto);
			this.setSuccessMessage(ApplicationSession.getInstance().getMessage("DailyIncidentRegisterDTO.form.save"));
		} else {
			insuranceClaimDto.setUpdatedBy(createdBy);
			insuranceClaimDto.setUpdatedDate(createdDate);
			insuranceClaimDto.setLgIpMacUpd(lgIpMac);
			 dto=insuranceClaimService.saveClaim(insuranceClaimDto);
			this.setSuccessMessage(ApplicationSession.getInstance().getMessage("DailyIncidentRegisterDTO.form.save"));

		}
		
		List<Long> removeFileById = null;
        String fileId = insuranceClaimDto.getRemoveFileById();
		if (fileId != null && !fileId.isEmpty()) {
			removeFileById = new ArrayList<>();
			String fileArray[] = fileId.split(MainetConstants.operator.COMMA);
			for (String fields : fileArray) {
				removeFileById.add(Long.valueOf(fields));
			}
		}
		if (removeFileById != null && !removeFileById.isEmpty()) {				
			tbAcVendormasterService.updateUploadedFileDeleteRecords(removeFileById, insuranceClaimDto.getUpdatedBy());
		}
		FileUploadDTO requestDTO = new FileUploadDTO();
        requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        requestDTO.setStatus(MainetConstants.FlagA);
        requestDTO.setIdfId(Constants.SHORT_CODE + MainetConstants.WINDOWS_SLASH +dto.getInsuranceClaimId().toString() +MainetConstants.WINDOWS_SLASH +UserSession.getCurrent().getOrganisation().getOrgid());
        requestDTO.setDepartmentName(Constants.SHORT_CODE);
        requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        setAttachments(fileUpload.setFileUploadMethod(getAttachments()));
        fileUpload.doMasterFileUpload(getAttachments(), requestDTO);
		return true;
	}

	public InsuranceDetailsDTO getInsuranceDetailsDto() {
		return insuranceDetailsDto;
	}

	public void setInsuranceDetailsDto(InsuranceDetailsDTO insuranceDetailsDto) {
		this.insuranceDetailsDto = insuranceDetailsDto;
	}

	public List<GenVehicleMasterDTO> getVehicleMasterList() {
		return vehicleMasterList;
	}

	public void setVehicleMasterList(List<GenVehicleMasterDTO> vehicleMasterList) {
		this.vehicleMasterList = vehicleMasterList;
	}

	public List<TbAcVendormaster> getVendorList() {
		return vendorList;
	}

	public void setVendorList(List<TbAcVendormaster> vendorList) {
		this.vendorList = vendorList;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public Long getDeptId() {
		return DeptId;
	}

	public void setDeptId(Long deptId) {
		DeptId = deptId;
	}

	public String getDepartments() {
		return departments;
	}

	public void setDepartments(String departments) {
		this.departments = departments;
	}

	public InsuranceClaimDTO getInsuranceClaimDto() {
		return insuranceClaimDto;
	}
	
	public void setInsuranceClaimDto(InsuranceClaimDTO insuranceClaimDto) {
		this.insuranceClaimDto = insuranceClaimDto;
	}

	public List<DocumentDetailsVO> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<DocumentDetailsVO> attachments) {
		this.attachments = attachments;
	}

	public List<DocumentDetailsVO> getDocuments() {
		return documents;
	}

	public void setDocuments(List<DocumentDetailsVO> documents) {
		this.documents = documents;
	}

	public List<AttachDocs> getAttachDocsList() {
		return attachDocsList;
	}

	public void setAttachDocsList(List<AttachDocs> attachDocsList) {
		this.attachDocsList = attachDocsList;
	}

}
