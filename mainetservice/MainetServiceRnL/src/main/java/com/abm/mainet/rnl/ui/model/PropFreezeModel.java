package com.abm.mainet.rnl.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.rnl.dto.EstateBookingDTO;
import com.abm.mainet.rnl.dto.PropFreezeDTO;
import com.abm.mainet.rnl.service.IEstateBookingService;
import com.abm.mainet.rnl.ui.validator.PropFreezeFormValidator;

/**
 * @author ritesh.patil
 *
 */
@Component
@Scope("session")
public class PropFreezeModel extends AbstractFormModel {
    private static final long serialVersionUID = -3203025868739923716L;
    private String modeType;
    private EstateBookingDTO estateBookingDTO;
    private List<String> fromAndToDate;
    private List<Object[]> locationList;
    private Long locId, estateId;
    private List<PropFreezeDTO> freezeDTOList = new ArrayList<PropFreezeDTO>();

    @Autowired
    private IEstateBookingService iEstateBookingService;

    
    @Override
    public boolean saveForm() {

        validateBean(this, PropFreezeFormValidator.class);
        if (hasValidationErrors()) {
            return false;
        }
        estateBookingDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        estateBookingDTO.setLangId(UserSession.getCurrent().getLanguageId());
        estateBookingDTO.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
        estateBookingDTO.setCreatedDate(new Date());
        estateBookingDTO.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
        iEstateBookingService.saveFreezeProperty(estateBookingDTO);
        setSuccessMessage(ApplicationSession.getInstance().getMessage("rnl.property.freeze"));
        return true;

    }
    
    public String getModeType() {
        return modeType;
    }

    public void setModeType(final String modeType) {
        this.modeType = modeType;
    }

    public EstateBookingDTO getEstateBookingDTO() {
        return estateBookingDTO;
    }

    public void setEstateBookingDTO(final EstateBookingDTO estateBookingDTO) {
        this.estateBookingDTO = estateBookingDTO;
    }

    public List<String> getFromAndToDate() {
        return fromAndToDate;
    }

    public void setFromAndToDate(final List<String> fromAndToDate) {
        this.fromAndToDate = fromAndToDate;
    }

    public List<Object[]> getLocationList() {
        return locationList;
    }

    public void setLocationList(final List<Object[]> locationList) {
        this.locationList = locationList;
    }

    public Long getLocId() {
        return locId;
    }

    public void setLocId(final Long locId) {
        this.locId = locId;
    }

    public Long getEstateId() {
        return estateId;
    }

    public void setEstateId(final Long estateId) {
        this.estateId = estateId;
    }

	public List<PropFreezeDTO> getFreezeDTOList() {
		return freezeDTOList;
	}

	public void setFreezeDTOList(List<PropFreezeDTO> freezeDTOList) {
		this.freezeDTOList = freezeDTOList;
	}

}
