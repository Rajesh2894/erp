package com.abm.mainet.securitymanagement.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.dto.FileUploadDTO;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.securitymanagement.dto.DailyIncidentRegisterDTO;
import com.abm.mainet.securitymanagement.service.IDailyIncidentRegisterService;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class DailyIncidentRegisterModel extends AbstractFormModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4321168947704174182L;

	@Autowired
	IDailyIncidentRegisterService incidentRegisterService;

	private DailyIncidentRegisterDTO dailyIncidentRegisterDTO = new DailyIncidentRegisterDTO();
	
	private List<DocumentDetailsVO> attachments = new ArrayList<>();

	private String saveMode;
	
	private List<AttachDocs> attachDocsList = new ArrayList<>();
	
	@Resource
	IFileUploadService fileUpload;

	@Override
	public boolean saveForm() {
	
		Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
		Date createdDate = new Date();
		String lgIpMac = UserSession.getCurrent().getEmployee().getEmppiservername();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		dailyIncidentRegisterDTO.setOrgId(orgId);
		if (dailyIncidentRegisterDTO.getIncidentId() == null) {

			dailyIncidentRegisterDTO.setOrgId(orgId);
			dailyIncidentRegisterDTO.setCreatedBy(createdBy);
			dailyIncidentRegisterDTO.setCreatedDate(createdDate);
			dailyIncidentRegisterDTO.setLgIpMac(lgIpMac);
			dailyIncidentRegisterDTO= incidentRegisterService.save(dailyIncidentRegisterDTO);
			this.setSuccessMessage(ApplicationSession.getInstance().getMessage("DailyIncidentRegisterDTO.form.save"));
		} else {
			dailyIncidentRegisterDTO.setUpdatedBy(createdBy);
			dailyIncidentRegisterDTO.setUpdatedDate(createdDate);
			dailyIncidentRegisterDTO.setLgIpMacUpd(lgIpMac);
			this.setSuccessMessage(ApplicationSession.getInstance().getMessage("securityManagement.dailyIncident.recordUpdate"));
			incidentRegisterService.save(dailyIncidentRegisterDTO);
		}
		FileUploadDTO requestDTO = new FileUploadDTO();
        requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        requestDTO.setStatus(MainetConstants.FlagA);
        requestDTO.setIdfId("SM" + MainetConstants.WINDOWS_SLASH+dailyIncidentRegisterDTO.getIncidentId()+MainetConstants.WINDOWS_SLASH +UserSession.getCurrent().getOrganisation().getOrgid());
        requestDTO.setDepartmentName("SM");
        requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        setAttachments(fileUpload.setFileUploadMethod(getAttachments()));
        fileUpload.doMasterFileUpload(getAttachments(), requestDTO);
		return true;
	}

	public IDailyIncidentRegisterService getIncidentRegisterService() {
		return incidentRegisterService;
	}

	public void setIncidentRegisterService(IDailyIncidentRegisterService incidentRegisterService) {
		this.incidentRegisterService = incidentRegisterService;
	}

	public DailyIncidentRegisterDTO getDailyIncidentRegisterDTO() {
		return dailyIncidentRegisterDTO;
	}

	public void setDailyIncidentRegisterDTO(DailyIncidentRegisterDTO dailyIncidentRegisterDTO) {
		this.dailyIncidentRegisterDTO = dailyIncidentRegisterDTO;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<DocumentDetailsVO> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<DocumentDetailsVO> attachments) {
		this.attachments = attachments;
	}

	public List<AttachDocs> getAttachDocsList() {
		return attachDocsList;
	}

	public void setAttachDocsList(List<AttachDocs> attachDocsList) {
		this.attachDocsList = attachDocsList;
	}

}
