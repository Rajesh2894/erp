package com.abm.mainet.cms.dao;

import java.util.List;

import com.abm.mainet.cms.domain.ProfileMaster;
import com.abm.mainet.cms.domain.ProfileMasterHistory;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.util.LookUp;

public interface IProfileMasterDAO {

    public abstract List<ProfileMaster> getAllProfileMastersBySectionId(LookUp lookUp, Organisation organisation, String flag);

    public abstract ProfileMaster getProfileMasterByProfileId(long profileId);

    public abstract boolean saveOrUpdate(ProfileMaster profileMaster, String chekkerflag);

    public abstract List<ProfileMaster> getAllProfileMasters(List<LookUp> lookUp, Organisation organisation);

    public abstract List<ProfileMasterHistory> getGuestAllProfileMasters(List<LookUp> lookUp, Organisation organisation);

}