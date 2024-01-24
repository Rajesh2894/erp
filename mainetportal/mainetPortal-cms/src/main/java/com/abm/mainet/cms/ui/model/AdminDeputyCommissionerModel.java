package com.abm.mainet.cms.ui.model;

import java.io.Serializable;
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
public class AdminDeputyCommissionerModel extends AbstractFormModel implements Serializable {

    private static final long serialVersionUID = 3604492149919160597L;

    @Autowired
    private IProfileMasterService iProfileMasterService;
    public String makkerchekkerflag;

    public String getMakkerchekkerflag() {
        return makkerchekkerflag;
    }

    public void setMakkerchekkerflag(final String makkerchekkerflag) {
        this.makkerchekkerflag = makkerchekkerflag;
    }

    private LookUp getProfileMasterSection(final String prefix) {
        LookUp quickLink = null;

        final List<LookUp> lookUps = getLevelData(prefix);

        if (lookUps == null) {
            return null;
        }

        for (final LookUp lookUp2 : lookUps) {
            final LookUp lookUp = lookUp2;

            if (lookUp.getLookUpCode().equals(MainetConstants.DEPT_SHORT_NAME.DEATH_CERTIFICATE)) {
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
