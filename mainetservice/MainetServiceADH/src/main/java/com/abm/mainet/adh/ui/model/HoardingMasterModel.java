package com.abm.mainet.adh.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.adh.dto.HoardingMasterDto;
import com.abm.mainet.adh.service.IHoardingMasterService;
import com.abm.mainet.adh.ui.validator.HoardingMasterValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;

/**
 * 
 * @author Anwarul.Hassan
 * @since 07-Aug-2019
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class HoardingMasterModel extends AbstractFormModel {

	private static final long serialVersionUID = 1L;

	@Autowired
	private IHoardingMasterService hoardingMasterSevice;
	private HoardingMasterDto hoardingMasterDto = new HoardingMasterDto();

	private List<HoardingMasterDto> hoardingMasterDtoList = new ArrayList<>();

	private String saveMode;
	private Long deptId;
	private String reportType;

	private List<TbLocationMas> locationList = new ArrayList<>();

	private String gisValue;
	private String gISUri;

	/*
	 * Saving Hoarding master Data (non-Javadoc)
	 * 
	 * @see com.abm.mainet.common.ui.model.AbstractFormModel#saveForm()
	 */
	@Override
	public boolean saveForm() {
		boolean status = false;

		validateBean(this, HoardingMasterValidator.class);
		if (hasValidationErrors()) {
			return status;
		}
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Long empId = UserSession.getCurrent().getEmployee().getEmpId();
		Date date = new Date();
		String clientIpAddress = getClientIpAddress();
		Long valueOf = Long.valueOf(UserSession.getCurrent().getLanguageId());
		Long deptId = getDeptId();
		// Here setting Hoarding Master Data to save
		if (hoardingMasterDto.getHoardingId() == null) {
			hoardingMasterDto.setOrgId(orgId);
			hoardingMasterDto.setCreatedBy(empId);
			hoardingMasterDto.setCreatedDate(date);
			hoardingMasterDto.setLgIpMac(clientIpAddress);
			hoardingMasterDto.setLangId(valueOf);
			hoardingMasterDto.setDeptId(deptId);
			HoardingMasterDto masterDto = hoardingMasterSevice.saveHoardingMasterData(hoardingMasterDto);
			setSuccessMessage(ApplicationSession.getInstance().getMessage("hoarding.master.success.message")
					+ MainetConstants.WHITE_SPACE + masterDto.getHoardingNumber());

			status = true;
		} else {
			// Here setting Hoarding Master Data to update
			hoardingMasterDto.setOrgId(orgId);
			hoardingMasterDto.setUpdatedBy(empId);
			hoardingMasterDto.setUpdatedDate(date);
			hoardingMasterDto.setLgIpMacUpd(clientIpAddress);
			hoardingMasterDto.setDeptId(deptId);
			hoardingMasterSevice.updateHoardingMasterData(hoardingMasterDto);
			setSuccessMessage(ApplicationSession.getInstance().getMessage("hoarding.master.update.message"));
			status = true;
		}
		return status;
	}

	public HoardingMasterDto getHoardingMasterDto() {
		return hoardingMasterDto;
	}

	public void setHoardingMasterDto(HoardingMasterDto hoardingMasterDto) {
		this.hoardingMasterDto = hoardingMasterDto;
	}

	public List<HoardingMasterDto> getHoardingMasterDtoList() {
		return hoardingMasterDtoList;
	}

	public void setHoardingMasterDtoList(List<HoardingMasterDto> hoardingMasterDtoList) {
		this.hoardingMasterDtoList = hoardingMasterDtoList;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public List<TbLocationMas> getLocationList() {
		return locationList;
	}

	public void setLocationList(List<TbLocationMas> locationList) {
		this.locationList = locationList;
	}

	public String getGisValue() {
		return gisValue;
	}

	public void setGisValue(String gisValue) {
		this.gisValue = gisValue;
	}

	public String getgISUri() {
		return gISUri;
	}

	public void setgISUri(String gISUri) {
		this.gISUri = gISUri;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
}
