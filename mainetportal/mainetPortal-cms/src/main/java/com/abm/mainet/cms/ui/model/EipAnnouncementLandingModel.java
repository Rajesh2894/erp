package com.abm.mainet.cms.ui.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cms.domain.EIPAnnouncementLanding;
import com.abm.mainet.cms.service.IEipAnnouncementLandingService;
import com.abm.mainet.common.ui.model.AbstractSearchFormModel;
import com.abm.mainet.common.util.UserSession;

@Component
@Scope(value = "session")
public class EipAnnouncementLandingModel extends AbstractSearchFormModel<EIPAnnouncementLanding> {

    /**
     * @author rajdeep.sinha
     */
    private static final long serialVersionUID = -862525156358257108L;
    public String makkerchekkerflag;

    public String getMakkerchekkerflag() {

        return makkerchekkerflag;

    }

    public void setMakkerchekkerflag(final String makkerchekkerflag) {
        this.makkerchekkerflag = makkerchekkerflag;

    }

    @Autowired
    private IEipAnnouncementLandingService iEipAnnouncementLandingService;

    @Override
    protected List<EIPAnnouncementLanding> querySearchResults() {
        return iEipAnnouncementLandingService.getAllEIPAnnouncementLanding(UserSession.getCurrent().getOrganisation());
    }

    @Override
    public List<EIPAnnouncementLanding> getResults() {
        return iEipAnnouncementLandingService.getAllEIPAnnouncementLanding(UserSession.getCurrent().getOrganisation());
    }

}
