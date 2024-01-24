package com.abm.mainet.adh.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.adh.dto.AdvertiserMasterDto;
import com.abm.mainet.adh.dto.NewAdvertisementApplicationDto;
import com.abm.mainet.adh.dto.NewAdvertisementReqDto;
import com.abm.mainet.adh.service.IAdvertisementDataEntryService;
import com.abm.mainet.adh.ui.validator.AdvertisementDataEntryValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;

/**
 * @author Anwarul.Hassan
 * @since 26-Sep-2019
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class AdvertisementDataEntryModel extends AbstractFormModel {

    @Autowired
    private IAdvertisementDataEntryService dataEntryService;
    private static final long serialVersionUID = 1L;
    private List<TbLocationMas> locationList = new ArrayList<>();
    private List<NewAdvertisementApplicationDto> advertisementDtoList = new ArrayList<>();
    private NewAdvertisementApplicationDto advertisementDto = new NewAdvertisementApplicationDto();
    private List<AdvertiserMasterDto> advertiserMasterDtoList = new ArrayList<>();
    private List<String> agencyNameList = new ArrayList<>();
    private AdvertiserMasterDto advertiserMasterDto = new AdvertiserMasterDto();
    private String saveMode;
    private Long deptId;
    private Long licMaxTenureDays;
   

    String removeAdverId;
    private NewAdvertisementReqDto advertisementReqDto;
    /*
     * (non-Javadoc)
     * @see com.abm.mainet.common.ui.model.AbstractFormModel#saveForm()
     */
    @Override
    public boolean saveForm() {
        boolean status = false;
        advertisementDto.setNewAdvertisementReqDto(getAdvertisementReqDto());
       
        validateBean(this, AdvertisementDataEntryValidator.class);
        if (hasValidationErrors()) {
            return false;
        }
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        Date newDate = new Date();
        Long empId = UserSession.getCurrent().getEmployee().getEmpId();
        String ipAddress = getClientIpAddress();
        if (advertisementDto.getAdhId() == null) {
            advertisementDto.setOrgId(orgId);
            advertisementDto.setCreatedBy(empId);
            advertisementDto.setCreatedDate(newDate);
            advertisementDto.setLgIpMac(getClientIpAddress());
            if (!advertisementDto.getNewAdvertDetDtos().isEmpty()) {
            advertisementDto.getNewAdvertDetDtos().forEach(detailsDto -> {
            	if(detailsDto.getAdhHrdDetId() == null) {
            		detailsDto.setCreatedBy(empId);
            	detailsDto.setCreatedDate(newDate);
            	detailsDto.setOrgId(orgId);
            	detailsDto.setLgIpMac(ipAddress);
            	}else {
            		detailsDto.setUpdatedBy(empId);
                	detailsDto.setUpdatedDate(newDate);
                	detailsDto.setOrgId(orgId);
                	detailsDto.setLgIpMacUpd(ipAddress);
            	}
            });
            }
            dataEntryService.saveAdvertisementData(advertisementDto);
            setSuccessMessage(
                    ApplicationSession.getInstance().getMessage("Advertisement Data Entry saved successfully" +" "+advertisementDto.getLicenseNo() ));
            status = true;
        } else {
            advertisementDto.setOrgId(orgId);
            /*advertisementDto.setUpdatedBy(empId);
            advertisementDto.setUpdatedDate(newDate);*/
            advertisementDto.setLgIpMacUpd(ipAddress);
            advertisementDto.getNewAdvertDetDtos().forEach(detailsDto -> {
            	if(detailsDto.getAdhHrdDetId() == null) {
            	detailsDto.setCreatedBy(empId);
            	detailsDto.setCreatedDate(newDate);
            	detailsDto.setOrgId(orgId);
            	detailsDto.setLgIpMac(ipAddress);
            	}
            	else {
            		/*detailsDto.setUpdatedBy(empId);
                	detailsDto.setUpdatedDate(newDate);*/
                	detailsDto.setOrgId(orgId);
                	detailsDto.setLgIpMacUpd(ipAddress);
            	}
            });
            /*  Defect #79106*/
            List<Long> removeAdverId = new ArrayList<>();
    	    String ids = getRemoveAdverId();
    	    if (StringUtils.isNotBlank(ids)) {
    		String wasteId[] = ids.split(MainetConstants.operator.COMMA);
    		for (String id : wasteId) {
    			removeAdverId.add(Long.valueOf(id));
    		}
    	    }
            
            dataEntryService.saveAdvertisementData(advertisementDto);
          /*  Defect #79106*/
            if(!removeAdverId.isEmpty()) {
            dataEntryService.deleteAdvertisementId(removeAdverId);
            }
            setSuccessMessage(
                    ApplicationSession.getInstance().getMessage("Advertisement Data Entry updated successfully" +" "+advertisementDto.getLicenseNo()));
            status = true;
        }
        return status;
    }

    public List<TbLocationMas> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<TbLocationMas> locationList) {
        this.locationList = locationList;
    }

    public List<NewAdvertisementApplicationDto> getAdvertisementDtoList() {
        return advertisementDtoList;
    }

    public void setAdvertisementDtoList(List<NewAdvertisementApplicationDto> advertisementDtoList) {
        this.advertisementDtoList = advertisementDtoList;
    }

    public NewAdvertisementApplicationDto getAdvertisementDto() {
        return advertisementDto;
    }

    public void setAdvertisementDto(NewAdvertisementApplicationDto advertisementDto) {
        this.advertisementDto = advertisementDto;
    }

    public List<AdvertiserMasterDto> getAdvertiserMasterDtoList() {
        return advertiserMasterDtoList;
    }

    public void setAdvertiserMasterDtoList(List<AdvertiserMasterDto> advertiserMasterDtoList) {
        this.advertiserMasterDtoList = advertiserMasterDtoList;
    }

    public List<String> getAgencyNameList() {
        return agencyNameList;
    }

    public void setAgencyNameList(List<String> agencyNameList) {
        this.agencyNameList = agencyNameList;
    }

    public AdvertiserMasterDto getAdvertiserMasterDto() {
        return advertiserMasterDto;
    }

    public void setAdvertiserMasterDto(AdvertiserMasterDto advertiserMasterDto) {
        this.advertiserMasterDto = advertiserMasterDto;
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

	/**
	 * @return the removeAdverId
	 */
	public String getRemoveAdverId() {
		return removeAdverId;
	}

	/**
	 * @param removeAdverId the removeAdverId to set
	 */
	public void setRemoveAdverId(String removeAdverId) {
		this.removeAdverId = removeAdverId;
	}
	
	public Long getLicMaxTenureDays() {
        return licMaxTenureDays;
    }

    public void setLicMaxTenureDays(Long licMaxTenureDays) {
        this.licMaxTenureDays = licMaxTenureDays;
    }

	public NewAdvertisementReqDto getAdvertisementReqDto() {
		return advertisementReqDto;
	}

	public void setAdvertisementReqDto(NewAdvertisementReqDto advertisementReqDto) {
		this.advertisementReqDto = advertisementReqDto;
	}


}
