package com.abm.mainet.buildingplan.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.buildingplan.domain.TbLicPerfSiteAffecEntity;
import com.abm.mainet.buildingplan.dto.SiteAffectedDTO;
import com.abm.mainet.buildingplan.service.ISiteAffecService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.UserSession;

@Component
@Scope("session")
public class SiteAffectedModel extends AbstractFormModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	ISiteAffecService siteAffecService;

	private SiteAffectedDTO siteAffDto = new SiteAffectedDTO();

	private String flag;

	private List<SiteAffectedDTO> listSiteAffDto = new ArrayList<SiteAffectedDTO>();

	private List<DocumentDetailsVO> attachments = new ArrayList<>();
	
	 List<CFCAttachment> downloadDocs = new ArrayList<>();

	private Long applicationId;

	private Long gmId;

	private Long slLabelId;

	private Long level;
	
	private Long taskId;

	@Override
	public boolean saveForm() {

		List<TbLicPerfSiteAffecEntity> listSite = new ArrayList<TbLicPerfSiteAffecEntity>();
		final Employee emp = UserSession.getCurrent().getEmployee();
		for (SiteAffectedDTO siteAffectDto : listSiteAffDto) {
			TbLicPerfSiteAffecEntity siteAff = new TbLicPerfSiteAffecEntity();

			siteAffectDto.setCfcApplicationId(applicationId);
			siteAffectDto.setGmId(gmId);
			siteAffectDto.setLevel(level);
			siteAffectDto.setStatus("A");
			siteAffectDto.setSlLabelId(slLabelId);
			if (siteAffectDto.getsId() != null) {
				siteAffectDto.setUpdatedBy(emp.getEmpId());
				siteAffectDto.setUpdatedDate(new Date());
				siteAffectDto.setLgIpMacUpd(emp.getLgIpMac());
			} else {
				siteAffectDto.setCreatedBy(emp.getEmpId());
				siteAffectDto.setCreatedDate(new Date());
				siteAffectDto.setLgIpMacUpd(emp.getLgIpMac());
				siteAffectDto.setMainLine(flag);
			}
			BeanUtils.copyProperties(siteAffectDto, siteAff);
			siteAff.setTaskId(taskId);
			listSite.add(siteAff);

		}
		siteAffecService.saveSiteList(listSite);
		setSuccessMessage("Records has been saved successfully");
		return true;
	}

	public boolean saveDocument() {

		RequestDTO requestDTO = new RequestDTO();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		requestDTO.setOrgId(orgId);
		requestDTO.setStatus(MainetConstants.FlagA);
		requestDTO.setDepartmentName("ML");
		requestDTO.setServiceId(getServiceId());
		requestDTO.setIdfId(String.valueOf(applicationId));
		requestDTO.setApplicationId(applicationId);
		requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		setCommonFileAttachment(ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class)
				.setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));
		if(flag.equals("D")) {
			getCommonFileAttachment().get(0).setDoc_DESC_ENGL("Applied land to be shown on the Development Plan(.pdf)");
			getCommonFileAttachment().get(1).setDoc_DESC_ENGL("Applied land to be shown on the sectoral plan(.pdf)");
			getCommonFileAttachment().get(2).setDoc_DESC_ENGL("Site plan vis-a-vis planning parameters(.pdf)");
			requestDTO.setReferenceId(applicationId+MainetConstants.WINDOWS_SLASH+"S"+taskId);
			
		}else {
			getCommonFileAttachment().get(0).setDoc_DESC_ENGL("Shajra cum demacration plan (.pdf)");
			getCommonFileAttachment().get(1).setDoc_DESC_ENGL("Site cum demacration plan (.dwg)");
			getCommonFileAttachment().get(2).setDoc_DESC_ENGL("Site cum demacration plan (.pdf)");
			requestDTO.setReferenceId(applicationId+MainetConstants.WINDOWS_SLASH+"J"+taskId);
		}
		ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class)
				.doFileUpload(getCommonFileAttachment(), requestDTO);
		setSuccessMessage("Records has been saved successfully");
		return true;
	}

	public SiteAffectedDTO getSiteAffDto() {
		return siteAffDto;
	}

	public void setSiteAffDto(SiteAffectedDTO siteAffDto) {
		this.siteAffDto = siteAffDto;
	}

	public List<SiteAffectedDTO> getListSiteAffDto() {
		return listSiteAffDto;
	}

	public void setListSiteAffDto(List<SiteAffectedDTO> listSiteAffDto) {
		this.listSiteAffDto = listSiteAffDto;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public Long getGmId() {
		return gmId;
	}

	public void setGmId(Long gmId) {
		this.gmId = gmId;
	}

	public Long getSlLabelId() {
		return slLabelId;
	}

	public void setSlLabelId(Long slLabelId) {
		this.slLabelId = slLabelId;
	}

	public Long getLevel() {
		return level;
	}

	public void setLevel(Long level) {
		this.level = level;
	}

	public List<DocumentDetailsVO> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<DocumentDetailsVO> attachments) {
		this.attachments = attachments;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public List<CFCAttachment> getDownloadDocs() {
		return downloadDocs;
	}

	public void setDownloadDocs(List<CFCAttachment> downloadDocs) {
		this.downloadDocs = downloadDocs;
	}

}
