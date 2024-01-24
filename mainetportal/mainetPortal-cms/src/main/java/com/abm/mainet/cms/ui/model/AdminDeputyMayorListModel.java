package com.abm.mainet.cms.ui.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cms.domain.ProfileMaster;
import com.abm.mainet.cms.service.IProfileMasterService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;

/**
 * @author swapnil.shirke
 */
@Component
@Scope("session")
public class AdminDeputyMayorListModel extends AbstractFormModel {

    private static final long serialVersionUID = 3604492149919160597L;

    @Autowired
    private IProfileMasterService iProfileMasterService;

    private long count;

    public String makkerchekkerflag;

    public String getMakkerchekkerflag() {
        return makkerchekkerflag;
    }

    public void setMakkerchekkerflag(final String makkerchekkerflag) {
        this.makkerchekkerflag = makkerchekkerflag;
    }

    public long getCount() {
        return count;
    }

    public void setCount(final long count) {
        this.count = count;
    }

    @Override
    protected void initializeModel() {
        super.setCommonHelpDocs("AdminDeputyMayorList.html");
    }

    /*
     * public void gridSize() { final List<ProfileMaster> profileMasterList = generateProfileMasterList();
     * setCount(profileMasterList.size()); }
     */
    private LookUp getProfileMasterSection(final String prefix) {
        LookUp quickLink = null;

        final List<LookUp> lookUps = getLevelData(prefix);

        if (lookUps == null) {
            return null;
        }

        for (final LookUp lookUp2 : lookUps) {
            final LookUp lookUp = lookUp2;

            if (lookUp.getLookUpCode().equals(MainetConstants.DEPT_SHORT_NAME.DEPUTY_MAYOR)) {
                quickLink = lookUp;
                break;
            }

        }
        return quickLink;

    }

    public List<ProfileMaster> generateProfileMasterList(String flag) {
        final LookUp lookUp = getProfileMasterSection(PrefixConstants.Prefix.PMS);
        final List<ProfileMaster> profileMasters = iProfileMasterService.getAllProfileMasterCPDSection(lookUp,
                UserSession.getCurrent().getOrganisation(), flag);
        return profileMasters;
    }
}
