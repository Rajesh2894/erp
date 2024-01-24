/**
 *
 */
package com.abm.mainet.cms.service;

import java.io.Serializable;
import java.util.List;

import com.abm.mainet.cms.domain.ProfileMaster;
import com.abm.mainet.cms.domain.ProfileMasterHistory;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.util.LookUp;

/**
 * @author swapnil.shirke
 */
public interface IProfileMasterService extends Serializable {
    public List<ProfileMaster> getAllProfileMasterCPDSection(LookUp lookUp, Organisation organisation, String flag);

    public ProfileMaster getProfileMasterById(long rowId);

    public boolean delete(long rowId);

    public boolean saveOrUpdate(ProfileMaster profileMasterObj, String chekkerflag);

    public List<ProfileMaster> getAllProfileMaster(List<LookUp> lookUps, Organisation organisation);

    public List<ProfileMasterHistory> getGuestAllProfileMaster(List<LookUp> lookUps, Organisation organisation);

    public ProfileMaster getProfileMasterByViewId(long rowId);
}
