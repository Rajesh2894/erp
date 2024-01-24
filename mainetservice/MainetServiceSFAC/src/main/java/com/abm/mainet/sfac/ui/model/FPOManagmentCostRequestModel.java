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
import com.abm.mainet.common.master.dto.TbFinancialyear;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.sfac.dto.CBBOMasterDto;
import com.abm.mainet.sfac.dto.FPOManagementCostMasterDTO;
import com.abm.mainet.sfac.dto.FPOMasterDto;
import com.abm.mainet.sfac.dto.IAMasterDto;
import com.abm.mainet.sfac.service.FPOManagementCostMasterService;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class FPOManagmentCostRequestModel extends AbstractFormModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6989883899177343734L;

	FPOManagementCostMasterDTO dto  = new FPOManagementCostMasterDTO();

	CBBOMasterDto masterDto = new CBBOMasterDto();

	List<CBBOMasterDto> cbboMasterList = new ArrayList<>();

	private ServiceMaster serviceMaster = new ServiceMaster();

	private List<LookUp> parList = new ArrayList<>();

	@Autowired
	IFileUploadService fileUpload;

	private List<TbFinancialyear> faYears = new ArrayList<>();

	private List<FPOMasterDto> masterDtoList = new ArrayList<>();
	private List<FPOManagementCostMasterDTO> fpoManagementCostMasterDTOs = new ArrayList<>();
	private List<IAMasterDto> iaMasterDtoList = new ArrayList<>();
	private List<CBBOMasterDto> iaNameList = new ArrayList<>();
	private String viewMode;
	private List<Long> removedIds;


	@Autowired FPOManagementCostMasterService fpoManagementCostMasterService;

	@Autowired
	private IOrganisationService orgService;

	public List<CBBOMasterDto> getIaNameList() {
		return iaNameList;
	}

	public void setIaNameList(List<CBBOMasterDto> iaNameList) {
		this.iaNameList = iaNameList;
	}

	public String getViewMode() {
		return viewMode;
	}

	public void setViewMode(String viewMode) {
		this.viewMode = viewMode;
	}

	public FPOManagementCostMasterDTO getDto() {
		return dto;
	}

	public void setDto(FPOManagementCostMasterDTO dto) {
		this.dto = dto;
	}

	public CBBOMasterDto getMasterDto() {
		return masterDto;
	}

	public void setMasterDto(CBBOMasterDto masterDto) {
		this.masterDto = masterDto;
	}

	public List<CBBOMasterDto> getCbboMasterList() {
		return cbboMasterList;
	}

	public void setCbboMasterList(List<CBBOMasterDto> cbboMasterList) {
		this.cbboMasterList = cbboMasterList;
	}

	public List<TbFinancialyear> getFaYears() {
		return faYears;
	}

	public void setFaYears(List<TbFinancialyear> faYears) {
		this.faYears = faYears;
	}

	public List<FPOMasterDto> getMasterDtoList() {
		return masterDtoList;
	}

	public void setMasterDtoList(List<FPOMasterDto> masterDtoList) {
		this.masterDtoList = masterDtoList;
	}



	public List<FPOManagementCostMasterDTO> getFpoManagementCostMasterDTOs() {
		return fpoManagementCostMasterDTOs;
	}

	public void setFpoManagementCostMasterDTOs(List<FPOManagementCostMasterDTO> fpoManagementCostMasterDTOs) {
		this.fpoManagementCostMasterDTOs = fpoManagementCostMasterDTOs;
	}

	public List<IAMasterDto> getIaMasterDtoList() {
		return iaMasterDtoList;
	}

	public void setIaMasterDtoList(List<IAMasterDto> iaMasterDtoList) {
		this.iaMasterDtoList = iaMasterDtoList;
	}

	@Override
	public boolean saveForm() throws FrameworkException{
		Organisation org = orgService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.FPO);
		Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
		String lgIp = UserSession.getCurrent().getEmployee().getEmppiservername();
		Date newDate = new Date();
		FPOManagementCostMasterDTO mastDto = getDto();

		if (mastDto.getCreatedBy() == null) {
			mastDto.setCreatedBy(createdBy);
			mastDto.setCreatedDate(newDate);
			mastDto.setOrgId(org.getOrgid());
			mastDto.setLgIpMac(lgIp);
			mastDto.setStatus(MainetConstants.WorkFlow.Status.PENDING);

			mastDto.getFpoManagementCostDetailDTOs().forEach(detDto -> {
				detDto.setCreatedBy(createdBy);
				detDto.setCreatedDate(newDate);
				detDto.setOrgId(org.getOrgid());
				detDto.setLgIpMac(lgIp);
			});
			mastDto.getFpoManagementCostDocDetailDTOs().forEach(bnkDto -> {
				bnkDto.setAttachments(fileUpload.setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));
				bnkDto.setStatus(MainetConstants.FlagA);
				bnkDto.setCreatedBy(createdBy);
				bnkDto.setCreatedDate(newDate);
				bnkDto.setOrgId(org.getOrgid());
				bnkDto.setLgIpMac(lgIp);
			});


		} else {
			mastDto.setUpdatedBy(createdBy);
			mastDto.setUpdatedDate(newDate);
			mastDto.setOrgId(org.getOrgid());
			mastDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());

			mastDto.getFpoManagementCostDetailDTOs().forEach(detailDto -> {
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

			mastDto.getFpoManagementCostDocDetailDTOs().forEach(bnkDto -> {
				bnkDto.setAttachments(fileUpload.setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));
				bnkDto.setStatus(MainetConstants.FlagA);
				if (bnkDto.getCreatedBy() == null) {
					bnkDto.setCreatedBy(createdBy);
					bnkDto.setCreatedDate(newDate);
					bnkDto.setOrgId(org.getOrgid());
					bnkDto.setLgIpMac(lgIp);
				} else {
					bnkDto.setUpdatedBy(createdBy);
					bnkDto.setUpdatedDate(newDate);
					bnkDto.setOrgId(org.getOrgid());
					bnkDto.setLgIpMac(lgIp);
				}
			});


		}

		ServiceMaster service = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.getServiceMasterByShortCode("FMC",
						UserSession.getCurrent().getOrganisation().getOrgid());
		this.setServiceMaster(service);

		if (this.viewMode.equals(MainetConstants.FlagA)) {
			mastDto = fpoManagementCostMasterService.saveAndUpdateApplication(mastDto,removedIds);
			setDto(mastDto);
			this.setSuccessMessage(getAppSession().getMessage("sfac.fpo.mgmt.save.msg") + " " + mastDto.getApplicationNumber());
			return true;
		}
		else {

			mastDto = fpoManagementCostMasterService.saveAndUpdateApplication(mastDto, removedIds);
			setDto(mastDto);

			this.setSuccessMessage(getAppSession().getMessage("sfac.fpo.mgmt.update.msg") + " " + mastDto.getApplicationNumber());
			return true;
		}

	}

	public List<Long> getRemovedIds() {
		return removedIds;
	}

	public void setRemovedIds(List<Long> removedIds) {
		this.removedIds = removedIds;
	}

	public ServiceMaster getServiceMaster() {
		return serviceMaster;
	}

	public void setServiceMaster(ServiceMaster serviceMaster) {
		this.serviceMaster = serviceMaster;
	}

	public List<LookUp> getParList() {
		return parList;
	}

	public void setParList(List<LookUp> parList) {
		this.parList = parList;
	}

	

}
