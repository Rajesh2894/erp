package com.abm.mainet.cms.ui.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cms.domain.EIPAnnouncement;
import com.abm.mainet.cms.service.IEipAnnouncementService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.util.UserSession;

@Component
@Scope(value = "session")
public class EipAnnouncementModel extends AbstractFormModel {

    /**
     *
     */
    private static final long serialVersionUID = 6151785269991330395L;
    String makkerchekkerflag;

    public String getMakkerchekkerflag() {
        return makkerchekkerflag;
    }

    public void setMakkerchekkerflag(final String makkerchekkerflag) {
        this.makkerchekkerflag = makkerchekkerflag;
    }

    @Autowired
    private IEipAnnouncementService iEipAnnouncementService;

    public List<EIPAnnouncement> querySearchResults(String Flag, String  env) {
    	return iEipAnnouncementService.getAllEIPAnnouncement(UserSession.getCurrent().getOrganisation(), Flag, env);
    }
    
  
    
   

    /*
     * @Override public List<EIPAnnouncement> getResults() { return
     * iEipAnnouncementService.getAllEIPAnnouncement(UserSession.getCurrent().getOrganisation()); }
     */
}
