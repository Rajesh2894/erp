package com.abm.mainet.sfac.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.sfac.dto.CBBOMasterDto;
import com.abm.mainet.sfac.dto.MilestoneCompletionMasterDto;
import com.abm.mainet.sfac.dto.MilestoneMasterDto;
import com.abm.mainet.sfac.service.MilestoneCompletionService;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class MilestoneCompletionFormModel extends AbstractFormModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3135284789726116311L;
	
	MilestoneCompletionMasterDto dto = new MilestoneCompletionMasterDto();
	
	@Autowired MilestoneCompletionService milestoneCompletionService;
	
	@Autowired
	IFileUploadService fileUpload;
	
	List<MilestoneCompletionMasterDto> milestoneCompletionMasterDtos = new ArrayList<>();
	
	private String viewMode;
	
	private CBBOMasterDto cbboMasterDto;
	
	private List<CBBOMasterDto> cbboMasterDtos = new ArrayList<>();
	
	private List<MilestoneMasterDto> milestoneMasterDtos = new ArrayList<MilestoneMasterDto>();
	
	private Long iaId;
	
	private ServiceMaster serviceMaster = new ServiceMaster();
	
	@Autowired
	private IOrganisationService orgService;

	public Long getIaId() {
		return iaId;
	}

	public void setIaId(Long iaId) {
		this.iaId = iaId;
	}

	public List<CBBOMasterDto> getCbboMasterDtos() {
		return cbboMasterDtos;
	}

	public void setCbboMasterDtos(List<CBBOMasterDto> cbboMasterDtos) {
		this.cbboMasterDtos = cbboMasterDtos;
	}

	public CBBOMasterDto getCbboMasterDto() {
		return cbboMasterDto;
	}

	public void setCbboMasterDto(CBBOMasterDto cbboMasterDto) {
		this.cbboMasterDto = cbboMasterDto;
	}

	public MilestoneCompletionMasterDto getDto() {
		return dto;
	}

	public void setDto(MilestoneCompletionMasterDto dto) {
		this.dto = dto;
	}

	public String getViewMode() {
		return viewMode;
	}

	public void setViewMode(String viewMode) {
		this.viewMode = viewMode;
	}

	public List<MilestoneMasterDto> getMilestoneMasterDtos() {
		return milestoneMasterDtos;
	}

	public void setMilestoneMasterDtos(List<MilestoneMasterDto> milestoneMasterDtos) {
		this.milestoneMasterDtos = milestoneMasterDtos;
	}

	public List<MilestoneCompletionMasterDto> getMilestoneCompletionMasterDtos() {
		return milestoneCompletionMasterDtos;
	}

	public void setMilestoneCompletionMasterDtos(List<MilestoneCompletionMasterDto> milestoneCompletionMasterDtos) {
		this.milestoneCompletionMasterDtos = milestoneCompletionMasterDtos;
	}
	
	
	@Override
	public boolean saveForm() throws FrameworkException{
		Organisation org = orgService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.CBBO);
		Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
		String lgIp = UserSession.getCurrent().getEmployee().getEmppiservername();
		Date newDate = new Date();
		MilestoneCompletionMasterDto mastDto = getDto();

		if (mastDto.getCreatedBy() == null) {
			mastDto.setCreatedBy(createdBy);
			mastDto.setCreatedDate(newDate);
			mastDto.setOrgId(org.getOrgid());
			mastDto.setLgIpMac(lgIp);
			mastDto.setStatus(MainetConstants.WorkFlow.Status.PENDING);
			mastDto.setAttachments(fileUpload.setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));

			mastDto.getMilestoneCompletionDocDetailsDtos().forEach(detDto -> {
				detDto.setAttachments(fileUpload.setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));
				detDto.setCreatedBy(createdBy);
				detDto.setCreatedDate(newDate);
				detDto.setOrgId(org.getOrgid());
				detDto.setLgIpMac(lgIp);
			});
			


		} else {
			mastDto.setUpdatedBy(createdBy);
			mastDto.setUpdatedDate(newDate);
			mastDto.setOrgId(org.getOrgid());
			mastDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
			mastDto.setAttachments(fileUpload.setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));
			mastDto.getMilestoneCompletionDocDetailsDtos().forEach(detailDto -> {
				detailDto.setAttachments(fileUpload.setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));
				if (detailDto.getCreatedBy() == null || detailDto.getCreatedBy() == 0) {
					detailDto.setCreatedBy(createdBy);
					detailDto.setCreatedDate(newDate);
					detailDto.setOrgId(org.getOrgid());
					detailDto.setLgIpMac(lgIp);
				} else {
					detailDto.setUpdatedBy(createdBy);
					detailDto.setUpdatedDate(newDate);
					detailDto.setOrgId(org.getOrgid());
					detailDto.setLgIpMac(lgIp);
				}
			});

			

		}

		ServiceMaster service = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.getServiceMasterByShortCode(MainetConstants.Sfac.MILESTONE_COM_BPM_SHORTCODE,
						UserSession.getCurrent().getOrganisation().getOrgid());
		this.setServiceMaster(service);

		if (this.viewMode.equals(MainetConstants.FlagA)) {
			mastDto = milestoneCompletionService.saveAndUpdateApplication(mastDto);
			setDto(mastDto);
			this.setSuccessMessage(getAppSession().getMessage("sfac.milestone.comp.save.msg") + " " + mastDto.getApplicationNumber());
			return true;
		}
		else {

			mastDto = milestoneCompletionService.saveAndUpdateApplication(mastDto);
			setDto(mastDto);

			this.setSuccessMessage(getAppSession().getMessage("sfac.milestone.comp.update.msg") + " " + mastDto.getApplicationNumber());
			return true;
		}

	}

	public ServiceMaster getServiceMaster() {
		return serviceMaster;
	}

	public void setServiceMaster(ServiceMaster serviceMaster) {
		this.serviceMaster = serviceMaster;
	}
	
	

}
