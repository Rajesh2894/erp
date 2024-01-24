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
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.sfac.dto.BlockAllocationDetailDto;
import com.abm.mainet.sfac.dto.CBBOFiledStaffDetailsDto;
import com.abm.mainet.sfac.dto.CircularNotificationMasterDto;
import com.abm.mainet.sfac.service.CBBOFiledStaffDetailEntryService;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class CBBOFieldStaffDetailModel extends AbstractFormModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5651342142458874987L;
	
	@Autowired CBBOFiledStaffDetailEntryService cbboFiledStaffDetailEntryService;
	
	@Autowired private IOrganisationService orgService;
	
	private CBBOFiledStaffDetailsDto dto = new CBBOFiledStaffDetailsDto();
	
	private List<CBBOFiledStaffDetailsDto> cbboFiledStaffDetailsDtos = new ArrayList<>();
	private String viewMode;
	
	private List<LookUp> blockList;
	
	private List<LookUp> stateList;
	
	private List<LookUp> districtList;

	public List<LookUp> getBlockList() {
		return blockList;
	}

	public CBBOFiledStaffDetailsDto getDto() {
		return dto;
	}

	public void setDto(CBBOFiledStaffDetailsDto dto) {
		this.dto = dto;
	}



	public String getViewMode() {
		return viewMode;
	}

	public void setViewMode(String viewMode) {
		this.viewMode = viewMode;
	}

	public void setBlockList(List<LookUp> blockList) {
		this.blockList = blockList;
	}

	public List<CBBOFiledStaffDetailsDto> getCbboFiledStaffDetailsDtos() {
		return cbboFiledStaffDetailsDtos;
	}

	public void setCbboFiledStaffDetailsDtos(List<CBBOFiledStaffDetailsDto> cbboFiledStaffDetailsDtos) {
		this.cbboFiledStaffDetailsDtos = cbboFiledStaffDetailsDtos;
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

	@Override
	public boolean saveForm() {
		Organisation org = orgService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.CBBO);
		Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
		String lgIp = UserSession.getCurrent().getEmployee().getEmppiservername();
		Date newDate = new Date();
		CBBOFiledStaffDetailsDto mastDto = getDto();

		if (mastDto.getCreatedBy() == null) {
			mastDto.setCreatedBy(createdBy);
			mastDto.setCreatedDate(newDate);
			mastDto.setOrgId(org.getOrgid());
			mastDto.setLgIpMac(lgIp);
		


		} else {
			mastDto.setUpdatedBy(createdBy);
			mastDto.setUpdatedDate(newDate);
			mastDto.setOrgId(org.getOrgid());
			mastDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());

		}

		
		if (this.viewMode.equals(MainetConstants.FlagA)) {
			mastDto = cbboFiledStaffDetailEntryService.saveAndUpdateApplication(mastDto);
			setDto(mastDto);
			this.setSuccessMessage(getAppSession().getMessage("sfac.cbbo.field.staff.save.msg"));
			return true;
		}
		else {

			mastDto = cbboFiledStaffDetailEntryService.saveAndUpdateApplication(mastDto);
			setDto(mastDto);

			this.setSuccessMessage(getAppSession().getMessage("sfac.cbbo.field.staff.update.msg") );
			return true;
		}

	}
	

}
