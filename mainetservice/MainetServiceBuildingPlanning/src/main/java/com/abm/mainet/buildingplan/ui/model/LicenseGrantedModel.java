package com.abm.mainet.buildingplan.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.buildingplan.domain.LicenseGrantedEntity;
import com.abm.mainet.buildingplan.dto.LicenseGrantedDTO;
import com.abm.mainet.buildingplan.service.ISiteAffecService;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.UserSession;

@Component
@Scope("session")
public class LicenseGrantedModel extends AbstractFormModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	ISiteAffecService siteAffecService;

	private List<LicenseGrantedDTO> listLicenseDto = new ArrayList<LicenseGrantedDTO>();

	private Long applicationId;

	private Long gmId;

	private Long slLabelId;

	private Long level;
	
	private Long taskId;

	@Override
	public boolean saveForm() {

		List<LicenseGrantedEntity> listLicenseEntity = new ArrayList<LicenseGrantedEntity>();
		final Employee emp = UserSession.getCurrent().getEmployee();
		for (LicenseGrantedDTO licenseDto : listLicenseDto) {
			LicenseGrantedEntity entity = new LicenseGrantedEntity();

			licenseDto.setCfcApplicationId(applicationId);
			licenseDto.setGmId(gmId);
			licenseDto.setLevel(level);
			licenseDto.setStatus("A");
			licenseDto.setSlLabelId(slLabelId);
			if (licenseDto.getdId() != null) {
				licenseDto.setUpdatedBy(emp.getEmpId());
				licenseDto.setUpdatedDate(new Date());
				licenseDto.setLgIpMacUpd(emp.getLgIpMac());
			} else {
				licenseDto.setCreatedBy(emp.getEmpId());
				licenseDto.setCreatedDate(new Date());
				licenseDto.setLgIpMacUpd(emp.getLgIpMac());
			}
			BeanUtils.copyProperties(licenseDto, entity);
			entity.setTaskId(taskId);
			listLicenseEntity.add(entity);

		}
		siteAffecService.saveLicenseData(listLicenseEntity);
		setSuccessMessage("Records has been saved successfully");
		return true;
	}

	public List<LicenseGrantedDTO> getListLicenseDto() {
		return listLicenseDto;
	}

	public void setListLicenseDto(List<LicenseGrantedDTO> listLicenseDto) {
		this.listLicenseDto = listLicenseDto;
	}

	public ISiteAffecService getSiteAffecService() {
		return siteAffecService;
	}

	public void setSiteAffecService(ISiteAffecService siteAffecService) {
		this.siteAffecService = siteAffecService;
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

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

}
