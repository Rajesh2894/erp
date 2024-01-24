package com.abm.mainet.swm.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.swm.dto.SanitationMasterDTO;
import com.abm.mainet.swm.service.IPublicToiletMasterService;
import com.abm.mainet.swm.ui.validator.SanitationMasterValidator;

/**
 * @author Ajay.Kumar
 *
 */
@Component
@Scope("session")
public class PublicToiletMasterModel extends AbstractFormModel {
    private static final long serialVersionUID = 3678313964175441099L;
    @Autowired
    private IPublicToiletMasterService publicToiletMasterService;
    private SanitationMasterDTO sanitationMasterDTO = new SanitationMasterDTO();
    private List<SanitationMasterDTO> sanitationMasterList;
    private List<TbLocationMas> locList = new ArrayList<>();
    private Long locationCat;

    @Override
    public boolean saveForm() {

        boolean status = true;
        validateBean(sanitationMasterDTO, SanitationMasterValidator.class);
        if (hasValidationErrors()) {
            return false;
        } else {
            if (sanitationMasterDTO.getSanId() == null) {
                sanitationMasterDTO.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
                sanitationMasterDTO.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                sanitationMasterDTO.setCreatedDate(new Date());
                sanitationMasterDTO.setLgIpMac(Utility.getMacAddress());
                sanitationMasterDTO.setSanActive(MainetConstants.FlagY);
                setSuccessMessage(ApplicationSession.getInstance().getMessage("public.toilet.master.add.success"));
            } else {
                sanitationMasterDTO.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
                sanitationMasterDTO.setUpdatedDate(new Date());
                sanitationMasterDTO.setLgIpMacUpd(Utility.getMacAddress());
                setSuccessMessage(ApplicationSession.getInstance().getMessage("public.toilet.master.edit.success"));
            }
            
            if (status) {
                publicToiletMasterService.savePublicToilet(sanitationMasterDTO);

            } else {
                addValidationError(ApplicationSession.getInstance().getMessage("public.toilet.master.exists"));

            }
        }
        return status;
    }

    @Override
    protected final String findPropertyPathPrefix(final String parentCode) {
        return MainetConstants.SOLID_WATSE_FLOWTYPE.SANITAION_MAS_WARD_ZONE_LEVEL;
    }

    public IPublicToiletMasterService getPublicToiletMasterService() {
        return publicToiletMasterService;
    }

    public void setPublicToiletMasterService(IPublicToiletMasterService publicToiletMasterService) {
        this.publicToiletMasterService = publicToiletMasterService;
    }

    public SanitationMasterDTO getSanitationMasterDTO() {
        return sanitationMasterDTO;
    }

    public void setSanitationMasterDTO(SanitationMasterDTO sanitationMasterDTO) {
        this.sanitationMasterDTO = sanitationMasterDTO;
    }

    public List<SanitationMasterDTO> getSanitationMasterList() {
        return sanitationMasterList;
    }

    public List<TbLocationMas> getLocList() {
        return locList;
    }

    public void setLocList(List<TbLocationMas> locList) {
        this.locList = locList;
    }

    public void setSanitationMasterList(List<SanitationMasterDTO> sanitationMasterList) {
        this.sanitationMasterList = sanitationMasterList;
    }

    public Long getLocationCat() {

        return CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix("PT", "LCT",
                UserSession.getCurrent().getOrganisation().getOrgid());
    }

    public void setLocationCat(Long locationCat) {
        this.locationCat = locationCat;
    }

}
