package com.abm.mainet.cms.ui.model;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cms.domain.LinksMaster;
import com.abm.mainet.cms.service.IQuickLinkService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.service.IEntitlementService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;

/**
 * @author swapnil.shirke
 */
@Component
@Scope("session")
public class AdminQuickLinkModel extends AbstractFormModel {

    private static final long serialVersionUID = -5106967173698793187L;

    @Autowired
    private IQuickLinkService iAdminQuickLinkService;
    
    @Autowired
    private IEntitlementService iEntitlementService;

    private List<LinksMaster> linksMasters = Collections.emptyList();

    public String makkerchekkerflag;

    public String getMakkerchekkerflag() {
        return makkerchekkerflag;
    }

    public void setMakkerchekkerflag(final String makkerchekkerflag) {
        this.makkerchekkerflag = makkerchekkerflag;
    }

    /**
     * @param flag
     * @return List of {@link LinksMaster} entity object
     */
    public List<LinksMaster> generateQuickLinkList(String flag) {
        final LookUp lookUp = getQuickLinkSection("HQS");
        final List<LinksMaster> linkMasterObj = iAdminQuickLinkService.getAllLinkMasterByCPDSection(lookUp,
                UserSession.getCurrent().getOrganisation(), flag);
        setLinksMasters(linkMasterObj);
        return linkMasterObj;
    }

    @Override
    protected void initializeModel() {
        super.setCommonHelpDocs("AdminQuickLink.html");
    }

    /**
     * @param prefix
     * @return Lookup object for given prefix and its lookup code
     */
    private LookUp getQuickLinkSection(final String prefix) {
        LookUp quickLink = null;

        final List<LookUp> lookUps = getLevelData(prefix);

        if (lookUps == null) {
            return null;
        }

        for (final LookUp lookUp2 : lookUps) {
            final LookUp lookUp = lookUp2;

            if (lookUp.getLookUpCode().equals("Q")) {
                quickLink = lookUp;
                break;
            }

        }
        return quickLink;

    }

    /**
     * @return the linksMasters
     */
    public List<LinksMaster> getLinksMasters() {
        return linksMasters;
    }

    /**
     * @param linksMasters the linksMasters to set
     */
    public void setLinksMasters(final List<LinksMaster> linksMasters) {
        this.linksMasters = linksMasters;
    }

    public boolean getMakerCheckerFlag() {
    	long gmid = iEntitlementService.getGroupIdByName(MainetConstants.MENU.APPROVER,
                UserSession.getCurrent().getOrganisation().getOrgid());
        if (gmid == UserSession.getCurrent().getEmployee().getGmid()) {
            return true;
        } else {
        	return false;
        }
    	
    }
}
