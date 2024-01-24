package com.abm.mainet.cms.ui.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cms.domain.PublicNotices;
import com.abm.mainet.cms.service.IAdminPublicNoticesService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.util.UserSession;

/**
 * @author swapnil.shirke
 */
@Component
@Scope("session")
public class AdminPublicNoticesModel extends AbstractFormModel {
    private static final long serialVersionUID = 3098893916325519727L;

    @Autowired
    private IAdminPublicNoticesService iAdminPublicNoticesService;

    String makkerchekkerflag;

    public String getMakkerchekkerflag() {
        return makkerchekkerflag;
    }

    public void setMakkerchekkerflag(final String makkerchekkerflag) {
        this.makkerchekkerflag = makkerchekkerflag;
    }

    public List<PublicNotices> querySearchResults(String Flag) {
        return iAdminPublicNoticesService.getAllPublicNotices(UserSession.getCurrent().getOrganisation(), Flag);
    }

    /*
     * public List<PublicNotices> getResults() { final List<PublicNotices> publicList = iAdminPublicNoticesService
     * .getAllPublicNotices(UserSession.getCurrent().getOrganisation()); for (final PublicNotices notice : publicList) { final
     * SimpleDateFormat mdyFormat = new SimpleDateFormat(MainetConstants.COMMON_DATE_FORMAT); final String date =
     * mdyFormat.format(notice.getIssueDate()); notice.setNewDate(date); } return publicList; }
     */
}
