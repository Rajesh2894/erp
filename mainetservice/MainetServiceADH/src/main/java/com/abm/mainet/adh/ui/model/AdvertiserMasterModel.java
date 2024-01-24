package com.abm.mainet.adh.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.adh.dto.AdvertiserMasterDto;
import com.abm.mainet.adh.dto.LicenseLetterDto;
import com.abm.mainet.adh.service.IAdvertiserMasterService;
import com.abm.mainet.adh.ui.validator.AdverstiserMasterValidator;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;

/**
 * @author cherupelli.srikanth
 * @since 02 august 2019
 */
@Component
@Scope("session")
public class AdvertiserMasterModel extends AbstractFormModel {

    private static final long serialVersionUID = 1L;

    @Autowired
    private IAdvertiserMasterService advertiserMasterService;

    private AdvertiserMasterDto advertiserMasterDto = new AdvertiserMasterDto();

    private List<AdvertiserMasterDto> advertiserMasterDtoList = new ArrayList<>();

    private List<LicenseLetterDto> licenseDto = new ArrayList<>();

    private String saveMode;
    
    private Long fayear;
    
    private List<Object[]> dateList = new ArrayList<>();
    
    @Resource
	private TbFinancialyearService tbFinancialyearService;

    /**
     * Saving Advertiser Master Data in Data Base
     */
    @Override
    public boolean saveForm() {
        boolean status = false;
        if (fayear != null) {
			setDateList(tbFinancialyearService.getFromDateAndToDateByFinancialYearId(fayear));
			if (dateList != null && !dateList.isEmpty()) {
	            for (Object[] dateLists : dateList) {
	                fromDate = (Date) dateLists[0];
	                toDate = (Date) dateLists[1];
	                break;
	            }
	        }
			advertiserMasterDto.setAgencyLicFromDate(fromDate);
			advertiserMasterDto.setAgencyLicToDate(toDate);
			
		}
        validateBean(advertiserMasterDto, AdverstiserMasterValidator.class);

        if (hasValidationErrors()) {
            return status;
        }
        // Here setting Advertiser Master Data to save in Data Base
        if (advertiserMasterDto.getAgencyId() == null) {
            advertiserMasterDto.setAgencyStatus("A");
            advertiserMasterDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
            advertiserMasterDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            advertiserMasterDto.setCreatedDate(new Date());
            advertiserMasterDto.setLgIpMac(getClientIpAddress());
            advertiserMasterDto.setLangId(Long.valueOf(UserSession.getCurrent().getLanguageId()));
            String agencyLicNum = advertiserMasterService.generateAgencyLicenceNumber(
                    UserSession.getCurrent().getOrganisation(), advertiserMasterDto.getAgencyCategory());
            advertiserMasterDto.setAgencyLicNo(agencyLicNum);
            
            advertiserMasterService.saveAdevertiserMasterData(advertiserMasterDto);
            
            if (agencyLicNum != null) {
                setSuccessMessage(
                        ApplicationSession.getInstance().getMessage("advertiser.master.success.message") + " : " + agencyLicNum);
            } else {
                setSuccessMessage(
                        ApplicationSession.getInstance().getMessage("advertiser.master.success.message"));
            }

            status = true;
        } else {
            // Here setting Advertiser Master Data to Update in Data Base
            advertiserMasterDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
            advertiserMasterDto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            advertiserMasterDto.setUpdatedDate(new Date());
            advertiserMasterDto.setLgIpMacUpd(getClientIpAddress());
            if (fayear != null) {
    			setDateList(tbFinancialyearService.getFromDateAndToDateByFinancialYearId(fayear));
    			if (dateList != null && !dateList.isEmpty()) {
    	            for (Object[] dateLists : dateList) {
    	                fromDate = (Date) dateLists[0];
    	                toDate = (Date) dateLists[1];
    	                break;
    	            }
    	        }
    			advertiserMasterDto.setAgencyLicFromDate(fromDate);
    			advertiserMasterDto.setAgencyLicToDate(toDate);
    			
    		}
            advertiserMasterService.updateAdvertiserMasterData(advertiserMasterDto);
            if (advertiserMasterDto.getAgencyLicNo() != null) {
                setSuccessMessage(ApplicationSession.getInstance().getMessage("advertiser.master.update.message") + " : "
                        + advertiserMasterDto.getAgencyLicNo());
            } else {
                setSuccessMessage(ApplicationSession.getInstance().getMessage("advertiser.master.update.message"));
            }

            status = true;
        }

        return status;

    }

    public AdvertiserMasterDto getAdvertiserMasterDto() {
        return advertiserMasterDto;
    }

    public void setAdvertiserMasterDto(AdvertiserMasterDto advertiserMasterDto) {
        this.advertiserMasterDto = advertiserMasterDto;
    }

    public List<LicenseLetterDto> getLicenseDto() {
        return licenseDto;
    }

    public void setLicenseDto(List<LicenseLetterDto> licenseDto) {
        this.licenseDto = licenseDto;
    }

    public String getSaveMode() {
        return saveMode;
    }

    public void setSaveMode(String saveMode) {
        this.saveMode = saveMode;
    }

    public List<AdvertiserMasterDto> getAdvertiserMasterDtoList() {
        return advertiserMasterDtoList;
    }

    public void setAdvertiserMasterDtoList(List<AdvertiserMasterDto> advertiserMasterDtoList) {
        this.advertiserMasterDtoList = advertiserMasterDtoList;
    }

    private Date fromDate;

    private Date toDate;

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

	public Long getFayear() {
		return fayear;
	}

	public void setFayear(Long fayear) {
		this.fayear = fayear;
	}

	public List<Object[]> getDateList() {
		return dateList;
	}

	public void setDateList(List<Object[]> dateList) {
		this.dateList = dateList;
	}

}
