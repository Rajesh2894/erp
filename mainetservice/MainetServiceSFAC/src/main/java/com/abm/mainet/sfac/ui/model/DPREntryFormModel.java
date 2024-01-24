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
import com.abm.mainet.sfac.dto.DPREntryMasterDto;
import com.abm.mainet.sfac.dto.FPOMasterDto;
import com.abm.mainet.sfac.service.DPREntryRequestService;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class DPREntryFormModel extends AbstractFormModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3995396236855522715L;
	
	@Autowired
	IFileUploadService fileUpload;
	
	@Autowired DPREntryRequestService dprEntryRequestService;

	private String viewMode;
	
	private ServiceMaster serviceMaster = new ServiceMaster();

	private DPREntryMasterDto dto = new DPREntryMasterDto();

	private List<DPREntryMasterDto> dprEntryMasterDtos = new ArrayList<DPREntryMasterDto>();

	private CBBOMasterDto cbboMasterDto;

	private List<CBBOMasterDto> cbboMasterDtos = new ArrayList<>();
	
	@Autowired
	private IOrganisationService orgService;
	
	private List<Long> removedIds;
	
	private List<FPOMasterDto> fpoMasterDtos = new ArrayList<>();

	
	
	public List<Long> getRemovedIds() {
		return removedIds;
	}

	public void setRemovedIds(List<Long> removedIds) {
		this.removedIds = removedIds;
	}

	public List<FPOMasterDto> getFpoMasterDtos() {
		return fpoMasterDtos;
	}

	public void setFpoMasterDtos(List<FPOMasterDto> fpoMasterDtos) {
		this.fpoMasterDtos = fpoMasterDtos;
	}

	public CBBOMasterDto getCbboMasterDto() {
		return cbboMasterDto;
	}

	public void setCbboMasterDto(CBBOMasterDto cbboMasterDto) {
		this.cbboMasterDto = cbboMasterDto;
	}

	public List<CBBOMasterDto> getCbboMasterDtos() {
		return cbboMasterDtos;
	}

	public void setCbboMasterDtos(List<CBBOMasterDto> cbboMasterDtos) {
		this.cbboMasterDtos = cbboMasterDtos;
	}

	public String getViewMode() {
		return viewMode;
	}

	public void setViewMode(String viewMode) {
		this.viewMode = viewMode;
	}

	public DPREntryMasterDto getDto() {
		return dto;
	}

	public void setDto(DPREntryMasterDto dto) {
		this.dto = dto;
	}

	public List<DPREntryMasterDto> getDprEntryMasterDtos() {
		return dprEntryMasterDtos;
	}

	public void setDprEntryMasterDtos(List<DPREntryMasterDto> dprEntryMasterDtos) {
		this.dprEntryMasterDtos = dprEntryMasterDtos;
	}
	
	@Override
	public boolean saveForm() throws FrameworkException{
		Organisation org = orgService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.CBBO);
		Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
		String lgIp = UserSession.getCurrent().getEmployee().getEmppiservername();
		Date newDate = new Date();
		DPREntryMasterDto mastDto = getDto();

		if (mastDto.getCreatedBy() == null) {
			mastDto.setCreatedBy(createdBy);
			mastDto.setCreatedDate(newDate);
			mastDto.setOrgId(org.getOrgid());
			mastDto.setLgIpMac(lgIp);
			mastDto.setStatus(MainetConstants.WorkFlow.Status.PENDING);
			mastDto.setAttachments(fileUpload.setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));
			mastDto.getDprEntryDetailsDtos().forEach(detDto -> {
				detDto.setAttachmentsDet(fileUpload.setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));
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
			mastDto.getDprEntryDetailsDtos().forEach(detailDto -> {
				detailDto.setAttachmentsDet(fileUpload.setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));
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
				.getServiceMasterByShortCode(MainetConstants.Sfac.DPR_ENTRY_BPM_SHORTCODE,
						UserSession.getCurrent().getOrganisation().getOrgid());
		this.setServiceMaster(service);

		if (this.viewMode.equals(MainetConstants.FlagA)) {
			mastDto = dprEntryRequestService.saveAndUpdateApplication(mastDto, removedIds);
			setDto(mastDto);
			this.setSuccessMessage(getAppSession().getMessage("sfac.dpr.req.save.msg") + " " + mastDto.getApplicationNumber());
			return true;
		}
		else {

			mastDto = dprEntryRequestService.saveAndUpdateApplication(mastDto, removedIds);
			setDto(mastDto);

			this.setSuccessMessage(getAppSession().getMessage("sfac.dpr.req.update.msg") + " " + mastDto.getApplicationNumber());
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
