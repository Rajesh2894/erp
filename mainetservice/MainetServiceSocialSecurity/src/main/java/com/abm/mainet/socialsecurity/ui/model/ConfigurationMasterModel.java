package com.abm.mainet.socialsecurity.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.socialsecurity.service.ConfigurationMasterService;
import com.abm.mainet.socialsecurity.ui.dto.ConfigurationMasterDto;
import com.abm.mainet.socialsecurity.ui.validator.ConfigurationMasterValidator;

/**
 * @author rahul.chaubey
 * @since 11 Jan 2020
 */

@Component
@Scope("session")
public class ConfigurationMasterModel extends AbstractFormModel {
	private static final long serialVersionUID = -63874063707461754L;

	@Resource
	ConfigurationMasterService configurationMasterServie;

	@Resource
	ServiceMasterService serviceMaster;

	private List<Object[]> serviceList = new ArrayList<>();

	private String saveMode;

	private ConfigurationMasterDto configurtionMasterDto = new ConfigurationMasterDto();

	private List<ConfigurationMasterDto> configurationMasterList = new ArrayList<>();

	@Override
	public boolean saveForm() {

		configurtionMasterDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());

		if(configurtionMasterDto.getConfigurationId()==null)
		{
			validateBean(this, ConfigurationMasterValidator.class);
			if (hasValidationErrors()) {
				return false;
			}
		}

		if (configurtionMasterDto.getConfigurationId() == null) {
			configurtionMasterDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			configurtionMasterDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
			configurtionMasterDto.setCreationDate(new Date());
			configurtionMasterDto.setLgIpMac(getClientIpAddress());

			configurtionMasterDto.setSchemeCode(serviceMaster
					.fetchServiceShortCode(configurtionMasterDto.getSchemeMstId(), configurtionMasterDto.getOrgId()));
			configurtionMasterDto
					.setSchemeName(serviceMaster.getServiceNameByServiceId(configurtionMasterDto.getSchemeMstId()));
			configurationMasterServie.saveConfigurationMaster(configurtionMasterDto);
			setSuccessMessage(ApplicationSession.getInstance().getMessage("Record has been saved Sucessfully"));
		} else {
			configurtionMasterDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			configurtionMasterDto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
			configurtionMasterDto.setUpdatedDate(new Date());
			configurtionMasterDto.setLgIpMacUpd(getClientIpAddress());
			configurtionMasterDto.getBeneficiaryCount();
			configurationMasterServie.updateConfigurationMaster(configurtionMasterDto);
			setSuccessMessage(ApplicationSession.getInstance().getMessage("Record has been updated sucessfully"));
		}
		return true;
	}

	public List<Object[]> getServiceList() {
		return serviceList;
	}

	public ConfigurationMasterDto getConfigurtionMasterDto() {
		return configurtionMasterDto;
	}

	public void setConfigurtionMasterDto(ConfigurationMasterDto configurtionMasterDto) {
		this.configurtionMasterDto = configurtionMasterDto;
	}

	public void setServiceList(List<Object[]> serviceList) {
		this.serviceList = serviceList;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public List<ConfigurationMasterDto> getConfigurationMasterList() {
		return configurationMasterList;
	}

	public void setConfigurationMasterList(List<ConfigurationMasterDto> configurationMasterList) {
		this.configurationMasterList = configurationMasterList;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

}
