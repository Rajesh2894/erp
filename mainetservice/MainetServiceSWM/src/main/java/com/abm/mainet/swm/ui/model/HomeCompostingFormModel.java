/**
 * 
 */
package com.abm.mainet.swm.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.swm.dto.HomeComposeDetailsDto;
import com.abm.mainet.swm.dto.SolidWasteConsumerMasterDTO;
import com.abm.mainet.swm.service.IHomeCompostingService;

/**
 * @author cherupelli.srikanth
 *
 */

@Component
@Scope("session")
public class HomeCompostingFormModel extends AbstractFormModel {

    private static final long serialVersionUID = -8948954598945326214L;

    public static int FIRST_LEVEL = 1;

    private SolidWasteConsumerMasterDTO masterDto = new SolidWasteConsumerMasterDTO();

    private List<SolidWasteConsumerMasterDTO> masterDtoList = new ArrayList<>();

    private List<HomeComposeDetailsDto> homeComposeDetailList = new ArrayList<>();

    private Date composeDate;

    private Long prefixLevel;

    private Long orgId;
    
    private String removeWetWaste;

    private List<LookUp> lookupList = new ArrayList<>();

    @Autowired
    private IHomeCompostingService homeCompostingService;

    public SolidWasteConsumerMasterDTO getMasterDto() {
	return masterDto;
    }

    public void setMasterDto(SolidWasteConsumerMasterDTO masterDto) {
	this.masterDto = masterDto;
    }

    public List<SolidWasteConsumerMasterDTO> getMasterDtoList() {
	return masterDtoList;
    }

    public void setMasterDtoList(List<SolidWasteConsumerMasterDTO> masterDtoList) {
	this.masterDtoList = masterDtoList;
    }

    public List<HomeComposeDetailsDto> getHomeComposeDetailList() {
	return homeComposeDetailList;
    }

    public void setHomeComposeDetailList(List<HomeComposeDetailsDto> homeComposeDetailList) {
	this.homeComposeDetailList = homeComposeDetailList;
    }

    public Date getComposeDate() {
	return composeDate;
    }

    public void setComposeDate(Date composeDate) {
	this.composeDate = composeDate;
    }

    public Long getPrefixLevel() {
	return prefixLevel;
    }

    public void setPrefixLevel(Long prefixLevel) {
	this.prefixLevel = prefixLevel;
    }

    public Long getOrgId() {
	return orgId;
    }

    public void setOrgId(Long orgId) {
	this.orgId = orgId;
    }

    public List<LookUp> getLookupList() {
        return lookupList;
    }

    public void setLookupList(List<LookUp> lookupList) {
        this.lookupList = lookupList;
    }

    
    public String getRemoveWetWaste() {
        return removeWetWaste;
    }

    public void setRemoveWetWaste(String removeWetWaste) {
        this.removeWetWaste = removeWetWaste;
    }

    @Override
    public boolean saveForm() {
	boolean status = true;
	if (masterDto.getRegistrationId() == null) {

	    masterDto.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
	    masterDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
	    masterDto.setCreationDate(new Date());
	    masterDto.setLgIpMac(getClientIpAddress());

	    for (HomeComposeDetailsDto composeDetailDto : masterDto.getHomeComposeDetailList()) {
		composeDetailDto.setSwHomeCompDate(getComposeDate());
		composeDetailDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		composeDetailDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
		composeDetailDto.setCreatedDate(new Date());
		composeDetailDto.setLgIpMac(getClientIpAddress());
	    }
	    homeCompostingService.saveHomeComposting(masterDto);
	    setSuccessMessage(ApplicationSession.getInstance().getMessage("Successfully added Home Composting"));
	    status = true;
	} else {
	    masterDto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
	    masterDto.setUpdatedDate(new Date());
	    masterDto.setLgIpMacUpd(getClientIpAddress());

	    for (HomeComposeDetailsDto composeDetailDto : masterDto.getHomeComposeDetailList()) {
		composeDetailDto.setSwHomeCompDate(getComposeDate());
		composeDetailDto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
		composeDetailDto.setUpdatedDate(new Date());
		composeDetailDto.setLgIpMacUpd(getClientIpAddress());
	    }
	    
	    List<Long> removeWasteIds = new ArrayList<>();
	    String ids = getRemoveWetWaste();
	    if(ids != null && !ids.isEmpty()) {
		String wasteId[] = ids.split(MainetConstants.operator.COMMA);
		for (String id : wasteId) {
		    removeWasteIds.add(Long.valueOf(id));
		}
	    }
	    
	    homeCompostingService.updateHomeComposting(masterDto,removeWasteIds);
	    setSuccessMessage(ApplicationSession.getInstance().getMessage("Successfully edited Home Composting"));
	    status = true;
	}
	return status;

    }
 

}