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
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.sfac.dto.CircularNotificationMasterDto;
import com.abm.mainet.sfac.service.CircularNotificationService;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class CircularNotificationFormModel extends AbstractFormModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4607708342295771530L;

	@Autowired CircularNotificationService circularNotificationService;

	@Autowired private IOrganisationService orgService;

	@Autowired IFileUploadService fileUpload;

	CircularNotificationMasterDto dto = new CircularNotificationMasterDto();

	List<CircularNotificationMasterDto> circularNotificationMasterDtos = new ArrayList<CircularNotificationMasterDto>();

	private String viewMode;

	List<LookUp> stateList = new ArrayList<>();

	List<LookUp> districtList = new ArrayList<>();

	private Long orgId;

	private String orgName;

	private List<Object[]> deptList;


	public CircularNotificationMasterDto getDto() {
		return dto;
	}

	public void setDto(CircularNotificationMasterDto dto) {
		this.dto = dto;
	}

	public List<CircularNotificationMasterDto> getCircularNotificationMasterDtos() {
		return circularNotificationMasterDtos;
	}

	public void setCircularNotificationMasterDtos(List<CircularNotificationMasterDto> circularNotificationMasterDtos) {
		this.circularNotificationMasterDtos = circularNotificationMasterDtos;
	}

	public String getViewMode() {
		return viewMode;
	}

	public void setViewMode(String viewMode) {
		this.viewMode = viewMode;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public List<Object[]> getDeptList() {
		return deptList;
	}

	public void setDeptList(List<Object[]> deptList) {
		this.deptList = deptList;
	}

	@Override
	public boolean saveForm() {
		Organisation org = orgService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.NPMA);
		Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
		String lgIp = UserSession.getCurrent().getEmployee().getEmppiservername();
		Date newDate = new Date();
		CircularNotificationMasterDto mastDto = getDto();

		if (mastDto.getCreatedBy() == null) {
			mastDto.setCreatedBy(createdBy);
			mastDto.setCreatedDate(newDate);
			mastDto.setOrgId(org.getOrgid());
			mastDto.setLgIpMac(lgIp);
			mastDto.setStatus(MainetConstants.WorkFlow.Status.PENDING);
			mastDto.setAttachments(fileUpload.setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));
			mastDto.getCircularNotiicationDetDtos().forEach(detDto -> {
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
			mastDto.getCircularNotiicationDetDtos().forEach(detailDto -> {
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


		if (this.viewMode.equals(MainetConstants.FlagA)) {
			mastDto = circularNotificationService.saveAndUpdateApplication(mastDto);
			setDto(mastDto);
			this.setSuccessMessage(getAppSession().getMessage("sfac.circular.save.msg"));
			return true;
		}
		else {

			mastDto = circularNotificationService.saveAndUpdateApplication(mastDto);
			setDto(mastDto);

			this.setSuccessMessage(getAppSession().getMessage("sfac.circular.update.msg") );
			return true;
		}

	}

	public List<LookUp> getStateList() {
		return stateList;
	}

	public void setStateList(List<LookUp> stateList) {
		this.stateList = stateList;
	}

	public List<LookUp> getDistrictList() {
		return districtList;
	}

	public void setDistrictList(List<LookUp> districtList) {
		this.districtList = districtList;
	}

	

	

	
	

	

}
