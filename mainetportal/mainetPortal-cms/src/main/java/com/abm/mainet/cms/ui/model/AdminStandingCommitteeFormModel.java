package com.abm.mainet.cms.ui.model;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cms.domain.ProfileMaster;
import com.abm.mainet.cms.service.IProfileMasterService;
import com.abm.mainet.cms.ui.validator.AdminProfileMasterValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.ui.model.AbstractEntryFormModel;
import com.abm.mainet.common.util.LookUp;

@Component
@Scope("session")
public class AdminStandingCommitteeFormModel extends AbstractEntryFormModel<ProfileMaster> implements Serializable {

    private static final long serialVersionUID = -7167854008394331581L;

    @Autowired
    private IProfileMasterService iProfileMasterService;

    private ProfileMaster profileMaster;

    private String mode;

    @Override
    public void addForm() {
        final LookUp lookUpObj = getProfileMasterSection(PrefixConstants.Prefix.PMS);
        final ProfileMaster profileMaster = new ProfileMaster();
        profileMaster.setCpdSection(lookUpObj.getLookUpId());
        setMode(MainetConstants.Transaction.Mode.ADD);
        setProfileMaster(profileMaster);
        setEntity(profileMaster);
    }

    @Override
    public void editForm(final long rowId) {
        setMode(MainetConstants.Transaction.Mode.UPDATE);
        final ProfileMaster profileMaster = iProfileMasterService.getProfileMasterById(rowId);
        setProfileMaster(profileMaster);
        setEntity(profileMaster);
    }

    private boolean saveCommitteeForm() throws FrameworkException {

        final ProfileMaster profileMasterObj = getProfileMaster();
        profileMasterObj.setIsDeleted(MainetConstants.IsDeleted.NOT_DELETE);
        return iProfileMasterService.saveOrUpdate(profileMasterObj,profileMasterObj.getChekkerflag1());
    }

    private boolean updateCommitteeForm() throws FrameworkException {
        final ProfileMaster profileMasterObj = getProfileMaster();

        return iProfileMasterService.saveOrUpdate(profileMasterObj,profileMasterObj.getChekkerflag1());
    }

    @Override
    public boolean saveOrUpdateForm() {
        validateBean(getEntity(), AdminProfileMasterValidator.class);

        if (hasValidationErrors()) {
            return false;
        }

        if (getMode().equals(MainetConstants.Transaction.Mode.ADD)) {
            if (saveCommitteeForm()) {
                return true;
            } else {
                return false;
            }
        } else {
            if (updateCommitteeForm()) {
                return true;
            } else {
                return false;
            }
        }

    }

    @Override
    public void delete(final long rowId) {
        iProfileMasterService.delete(rowId);

    }

    private LookUp getProfileMasterSection(final String prefix) {
        LookUp quickLink = null;

        final List<LookUp> lookUps = getLevelData(prefix);

        if (lookUps == null) {
            return null;
        }

        for (final LookUp lookUp2 : lookUps) {
            final LookUp lookUp = lookUp2;

            if (lookUp.getLookUpCode().equals("SC")) {
                quickLink = lookUp;
                break;
            }

        }
        return quickLink;

    }

    /**
     * @return the profileMaster
     */
    public ProfileMaster getProfileMaster() {
        return profileMaster;
    }

    /**
     * @param profileMaster the profileMaster to set
     */
    public void setProfileMaster(final ProfileMaster profileMaster) {
        this.profileMaster = profileMaster;
    }

    /**
     * @return the mode
     */
    public String getMode() {
        return mode;
    }

    /**
     * @param mode the mode to set
     */
    public void setMode(final String mode) {
        this.mode = mode;
    }
}
