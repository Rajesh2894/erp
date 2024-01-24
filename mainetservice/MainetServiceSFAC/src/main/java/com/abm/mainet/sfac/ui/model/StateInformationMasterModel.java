/**
 * 
 */
package com.abm.mainet.sfac.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.sfac.dto.StateInformationDto;
import com.abm.mainet.sfac.service.StateInformationMasterService;

/**
 * @author pooja.maske
 *
 */
@Component
@Scope("session")
public class StateInformationMasterModel extends AbstractFormModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6482607262348794261L;

	private List<LookUp> stateList = new ArrayList<>();

	private List<LookUp> districtList = new ArrayList<>();

	private StateInformationDto stateInfoDto = new StateInformationDto();

	@Autowired
	private StateInformationMasterService stateInfoMastService;

	private String viewMode;

	private String dataExistcheck;

	/**
	 * @return the stateList
	 */
	public List<LookUp> getStateList() {
		return stateList;
	}

	/**
	 * @param stateList the stateList to set
	 */
	public void setStateList(List<LookUp> stateList) {
		this.stateList = stateList;
	}

	/**
	 * @return the districtList
	 */
	public List<LookUp> getDistrictList() {
		return districtList;
	}

	/**
	 * @param districtList the districtList to set
	 */
	public void setDistrictList(List<LookUp> districtList) {
		this.districtList = districtList;
	}

	/**
	 * @return the stateInfoDto
	 */
	public StateInformationDto getStateInfoDto() {
		return stateInfoDto;
	}

	/**
	 * @param stateInfoDto the stateInfoDto to set
	 */
	public void setStateInfoDto(StateInformationDto stateInfoDto) {
		this.stateInfoDto = stateInfoDto;
	}

	/**
	 * @return the viewMode
	 */
	public String getViewMode() {
		return viewMode;
	}

	/**
	 * @param viewMode the viewMode to set
	 */
	public void setViewMode(String viewMode) {
		this.viewMode = viewMode;
	}

	/**
	 * @return the dataExistcheck
	 */
	public String getDataExistcheck() {
		return dataExistcheck;
	}

	/**
	 * @param dataExistcheck the dataExistcheck to set
	 */
	public void setDataExistcheck(String dataExistcheck) {
		this.dataExistcheck = dataExistcheck;
	}

	@Override
	public boolean saveForm() {
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
		String lgIp = UserSession.getCurrent().getEmployee().getEmppiservername();
		Date newDate = new Date();
		StateInformationDto mastDto = getStateInfoDto();

		if (mastDto.getCreatedBy() == null) {
			mastDto.setCreatedBy(createdBy);
			mastDto.setCreatedDate(newDate);
			mastDto.setOrgId(orgId);
			mastDto.setLgIpMac(lgIp);
			mastDto.setLangId((long) UserSession.getCurrent().getLanguageId());
		} else {
			mastDto.setUpdatedBy(createdBy);
			mastDto.setUpdatedDate(newDate);
			mastDto.setOrgId(orgId);
			mastDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
			mastDto.setLangId((long) UserSession.getCurrent().getLanguageId());
		}

		mastDto = stateInfoMastService.saveAndUpdateApplication(mastDto);
		setStateInfoDto(mastDto);
		if (this.getViewMode().equals(MainetConstants.FlagE))
			this.setSuccessMessage(getAppSession().getMessage("sfac.state.master.update.msg"));
		else
			this.setSuccessMessage(getAppSession().getMessage("sfac.state.master.save.msg"));
		return true;

	}

}
