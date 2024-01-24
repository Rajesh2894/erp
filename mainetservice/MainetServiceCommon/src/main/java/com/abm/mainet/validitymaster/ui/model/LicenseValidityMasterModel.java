package com.abm.mainet.validitymaster.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TbServicesMst;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.validitymaster.dto.LicenseValidityMasterDto;
import com.abm.mainet.validitymaster.service.ILicenseValidityMasterService;
import com.abm.mainet.validitymaster.ui.validator.LicenseValidityMasterValidator;

/**
 * @author cherupelli.srikanth
 * @since 16 september 2019
 */
@Component
@Scope("session")
public class LicenseValidityMasterModel extends AbstractFormModel {

	private static final long serialVersionUID = -5625390576741782991L;

	@Autowired
	private ILicenseValidityMasterService licenseValidityMasterService;

	private List<TbDepartment> deparatmentList = new ArrayList<>();

	private List<TbServicesMst> serviceList = new ArrayList<>();

	private LicenseValidityMasterDto masterDto = new LicenseValidityMasterDto();

	private List<LicenseValidityMasterDto> masterDtoList = new ArrayList<>();
	private List<LookUp> triCodList1 = new ArrayList<LookUp>();
	private List<LookUp> triCodList2 = new ArrayList<LookUp>();
    private String deptShortName;
    private String envSpec;
	private String saveMode;

	@Override
	public boolean saveForm() {
		boolean status = false;
		masterDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		validateBean(this, LicenseValidityMasterValidator.class);
		if (hasValidationErrors()) {
			return false;
		}
		if (masterDto.getLicId() == null) {
			masterDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
			masterDto.setCreatedDate(new Date());
			masterDto.setLangId(Long.valueOf(UserSession.getCurrent().getLanguageId()));
			masterDto.setLgIpMac(getClientIpAddress());
			licenseValidityMasterService.saveLicenseValidityData(masterDto);
			setSuccessMessage(ApplicationSession.getInstance().getMessage("license.validity.saved.successfully"));
			status = true;
		} else {
			masterDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			masterDto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
			masterDto.setUpdatedDate(new Date());
			masterDto.setLgIpMacUpd(getClientIpAddress());
			licenseValidityMasterService.updateLicenseValidityData(masterDto);
			setSuccessMessage(ApplicationSession.getInstance().getMessage("license.validity.updated.successfully"));
			status = true;
		}
		return status;

	}

	public List<TbDepartment> getDeparatmentList() {
		return deparatmentList;
	}

	public void setDeparatmentList(List<TbDepartment> deparatmentList) {
		this.deparatmentList = deparatmentList;
	}

	public List<TbServicesMst> getServiceList() {
		return serviceList;
	}

	public void setServiceList(List<TbServicesMst> serviceList) {
		this.serviceList = serviceList;
	}

	public LicenseValidityMasterDto getMasterDto() {
		return masterDto;
	}

	public void setMasterDto(LicenseValidityMasterDto masterDto) {
		this.masterDto = masterDto;
	}

	public List<LicenseValidityMasterDto> getMasterDtoList() {
		return masterDtoList;
	}

	public void setMasterDtoList(List<LicenseValidityMasterDto> masterDtoList) {
		this.masterDtoList = masterDtoList;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public List<LookUp> getTriCodList1() {
		return triCodList1;
	}

	public void setTriCodList1(List<LookUp> triCodList1) {
		this.triCodList1 = triCodList1;
	}

	public List<LookUp> getTriCodList2() {
		return triCodList2;
	}

	public void setTriCodList2(List<LookUp> triCodList2) {
		this.triCodList2 = triCodList2;
	}

	public String getDeptShortName() {
		return deptShortName;
	}

	public void setDeptShortName(String deptShortName) {
		this.deptShortName = deptShortName;
	}

	public String getEnvSpec() {
		return envSpec;
	}

	public void setEnvSpec(String envSpec) {
		this.envSpec = envSpec;
	}

	

}
