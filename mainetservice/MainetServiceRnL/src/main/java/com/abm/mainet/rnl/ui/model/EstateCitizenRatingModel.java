package com.abm.mainet.rnl.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.rnl.dto.EstateBookingDTO;
import com.abm.mainet.rnl.dto.EstateCitizenRatingDTO;
import com.abm.mainet.rnl.service.IEstateCitizenRatingService;

@Component
@Scope("session")
public class EstateCitizenRatingModel extends AbstractFormModel {

    private static final long serialVersionUID = -5887704462511097078L;

    private EstateCitizenRatingDTO citizenRatingDTO = null;

    private List<EstateBookingDTO> estateBookings = new ArrayList<>();

    private List<LookUp> lookupList = new ArrayList<>();

    public EstateCitizenRatingDTO getCitizenRatingDTO() {
        return citizenRatingDTO;
    }

    public void setCitizenRatingDTO(EstateCitizenRatingDTO citizenRatingDTO) {
        this.citizenRatingDTO = citizenRatingDTO;
    }

    public List<EstateBookingDTO> getEstateBookings() {
        return estateBookings;
    }

    public void setEstateBookings(List<EstateBookingDTO> estateBookings) {
        this.estateBookings = estateBookings;
    }

    public List<LookUp> getLookupList() {
        return lookupList;
    }

    public void setLookupList(List<LookUp> lookupList) {
        this.lookupList = lookupList;
    }

    @Autowired
    IEstateCitizenRatingService citizenRatingService;

    @Override
    public boolean saveForm() {
        // insert in TB_RL_CITIZEN_RATING table
        getCitizenRatingDTO().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        getCitizenRatingDTO().setCreatedDate(new Date());
        getCitizenRatingDTO().setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
        getCitizenRatingDTO().setLgIpMac(UserSession.getCurrent().getEmployee().getLgIpMac());
        citizenRatingService.saveRatingAndFeedback(getCitizenRatingDTO());
        setSuccessMessage(ApplicationSession.getInstance().getMessage("rnl.rating.save"));
        return true;
    }

}
