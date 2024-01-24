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
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.sfac.dto.CBBOMasterDto;
import com.abm.mainet.sfac.dto.MilestoneMasterDto;
import com.abm.mainet.sfac.service.MilestoneEntryService;

/**
 * @author pooja.maske
 *
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class MilestoneEntryFormModel extends AbstractFormModel{

	/**
	 * 
	 */
	
	@Autowired
	private IOrganisationService orgService;
	
	private static final long serialVersionUID = -260607134224129657L;
	
	private MilestoneMasterDto milestoneMasterDto = new MilestoneMasterDto();
	
	private List<MilestoneMasterDto> milestoneMasterDtos = new ArrayList<>();
	
	private String viewMode;
	
	private List<CBBOMasterDto> cbboMasterDtos = new ArrayList<>();
	
	@Autowired MilestoneEntryService milestoneService;

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
	

	public List<MilestoneMasterDto> getMilestoneMasterDtos() {
		return milestoneMasterDtos;
	}

	public void setMilestoneMasterDtos(List<MilestoneMasterDto> milestoneMasterDtos) {
		this.milestoneMasterDtos = milestoneMasterDtos;
	}

	public MilestoneMasterDto getMilestoneMasterDto() {
		return milestoneMasterDto;
	}

	public void setMilestoneMasterDto(MilestoneMasterDto milestoneMasterDto) {
		this.milestoneMasterDto = milestoneMasterDto;
	}
	
	
	@Override
	public boolean saveForm() throws FrameworkException{
		Organisation org = orgService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.IA);
		
		Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
		String lgIp = UserSession.getCurrent().getEmployee().getEmppiservername();
		Date newDate = new Date();
		MilestoneMasterDto mastDto = getMilestoneMasterDto();

		if (mastDto.getCreatedBy() == null) {
			mastDto.setCreatedBy(createdBy);
			mastDto.setCreatedDate(newDate);
			mastDto.setOrgId(org.getOrgid());
			mastDto.setLgIpMac(lgIp);
			

			mastDto.getMilestoneDeliverablesDtos().forEach(detDto -> {
				detDto.setCreatedBy(createdBy);
				detDto.setCreatedDate(newDate);
				detDto.setOrgId(org.getOrgid());
				detDto.setLgIpMac(lgIp);
			});
			mastDto.getMilestoneCBBODetDtos().forEach(cbboDetDto -> {
				
				cbboDetDto.setCreatedBy(createdBy);
				cbboDetDto.setCreatedDate(newDate);
				cbboDetDto.setOrgId(org.getOrgid());
				cbboDetDto.setLgIpMac(lgIp);
			});


		} else {
			mastDto.setUpdatedBy(createdBy);
			mastDto.setUpdatedDate(newDate);
			mastDto.setOrgId(org.getOrgid());
			mastDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());

			mastDto.getMilestoneDeliverablesDtos().forEach(detailDto -> {
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

			mastDto.getMilestoneCBBODetDtos().forEach(cbboDetDto -> {
			
				if (cbboDetDto.getCreatedBy() == null) {
					cbboDetDto.setCreatedBy(createdBy);
					cbboDetDto.setCreatedDate(newDate);
					cbboDetDto.setOrgId(org.getOrgid());
					cbboDetDto.setLgIpMac(lgIp);
				} else {
					cbboDetDto.setUpdatedBy(createdBy);
					cbboDetDto.setUpdatedDate(newDate);
					cbboDetDto.setOrgId(org.getOrgid());
					cbboDetDto.setLgIpMac(lgIp);
				}
			});


		}

	

		if (this.viewMode.equals(MainetConstants.FlagA)) {
			mastDto = milestoneService.saveAndUpdateApplication(mastDto);
			setMilestoneMasterDto(mastDto);
			this.setSuccessMessage(getAppSession().getMessage("sfac.milestone.save.msg"));
			return true;
		}
		else {

			mastDto = milestoneService.saveAndUpdateApplication(mastDto);
			setMilestoneMasterDto(mastDto);

			this.setSuccessMessage(getAppSession().getMessage("sfac.milestone.update.msg"));
			return true;
		}

	}

}
