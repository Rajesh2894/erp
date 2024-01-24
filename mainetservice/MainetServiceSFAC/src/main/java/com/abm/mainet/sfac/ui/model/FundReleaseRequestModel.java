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
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.sfac.dto.FundReleaseRequestMasterDto;
import com.abm.mainet.sfac.service.FundReleaseRequestService;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class FundReleaseRequestModel extends AbstractFormModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4202975307476228323L;

	private ServiceMaster serviceMaster = new ServiceMaster();
	
	@Autowired FundReleaseRequestService fundReleaseRequestService;
	
	@Autowired
	IFileUploadService fileUpload;

	@Autowired
	private IOrganisationService orgService;

	private FundReleaseRequestMasterDto dto = new FundReleaseRequestMasterDto();

	private List<FundReleaseRequestMasterDto> fundReleaseRequestMasterDtos = new ArrayList<>();

	private List<TbFinancialyear> faYears = new ArrayList<>();

	private String viewMode;
	
	private List<Long> removedIds;
	
	

	public FundReleaseRequestMasterDto getDto() {
		return dto;
	}

	public void setDto(FundReleaseRequestMasterDto dto) {
		this.dto = dto;
	}

	public String getViewMode() {
		return viewMode;
	}

	public void setViewMode(String viewMode) {
		this.viewMode = viewMode;
	}

	public List<TbFinancialyear> getFaYears() {
		return faYears;
	}

	public void setFaYears(List<TbFinancialyear> faYears) {
		this.faYears = faYears;
	}

	public List<FundReleaseRequestMasterDto> getFundReleaseRequestMasterDtos() {
		return fundReleaseRequestMasterDtos;
	}

	public void setFundReleaseRequestMasterDtos(List<FundReleaseRequestMasterDto> fundReleaseRequestMasterDtos) {
		this.fundReleaseRequestMasterDtos = fundReleaseRequestMasterDtos;
	}

	@Override
	public boolean saveForm() throws FrameworkException{
		Organisation org = orgService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.IA);
		Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
		String lgIp = UserSession.getCurrent().getEmployee().getEmppiservername();
		Date newDate = new Date();
		FundReleaseRequestMasterDto mastDto = getDto();

		if (mastDto.getCreatedBy() == null) {
			mastDto.setCreatedBy(createdBy);
			mastDto.setCreatedDate(newDate);
			mastDto.setOrgId(org.getOrgid());
			mastDto.setLgIpMac(lgIp);
			mastDto.setStatus(MainetConstants.WorkFlow.Status.PENDING);
			

			mastDto.getFundReleaseRequestDetailDtos().forEach(detDto -> {
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
		
			mastDto.getFundReleaseRequestDetailDtos().forEach(detailDto -> {
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
				.getServiceMasterByShortCode(MainetConstants.Sfac.FUND_REL_BPM_SHORTCODE,
						UserSession.getCurrent().getOrganisation().getOrgid());
		this.setServiceMaster(service);

		if (this.viewMode.equals(MainetConstants.FlagA)) {
			mastDto = fundReleaseRequestService.saveAndUpdateApplication(mastDto, removedIds);
			setDto(mastDto);
			this.setSuccessMessage(getAppSession().getMessage("sfac.fund.release.req.save.msg") + " " + mastDto.getApplicationNumber());
			return true;
		}
		else {

			mastDto = fundReleaseRequestService.saveAndUpdateApplication(mastDto, removedIds);
			setDto(mastDto);

			this.setSuccessMessage(getAppSession().getMessage("sfac.fund.release.req.update.msg") + " " + mastDto.getApplicationNumber());
			return true;
		}

	}

	public ServiceMaster getServiceMaster() {
		return serviceMaster;
	}

	public void setServiceMaster(ServiceMaster serviceMaster) {
		this.serviceMaster = serviceMaster;
	}

	public List<Long> getRemovedIds() {
		return removedIds;
	}

	public void setRemovedIds(List<Long> removedIds) {
		this.removedIds = removedIds;
	}


	

}
