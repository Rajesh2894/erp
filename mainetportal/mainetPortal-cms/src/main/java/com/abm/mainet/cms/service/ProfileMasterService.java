/**
 *
 */
package com.abm.mainet.cms.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.cms.dao.IProfileMasterDAO;
import com.abm.mainet.cms.domain.ProfileMaster;
import com.abm.mainet.cms.domain.ProfileMasterHistory;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.service.IEntitlementService;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import org.apache.log4j.Logger;

/**
 * @author swapnil.shirke
 */
@Service
public class ProfileMasterService implements Serializable, IProfileMasterService {

    private static final long serialVersionUID = -6605468767130080148L;
    private static final Logger LOG = Logger.getLogger(ProfileMasterService.class);
    
    @Autowired
    private IProfileMasterDAO profileMasterDAO;
    
    @Autowired
    private IEntitlementService iEntitlementService;

    @Override
    @Transactional
    public List<ProfileMaster> getAllProfileMasterCPDSection(final LookUp lookUp, final Organisation organisation, String Flag) {
        return profileMasterDAO.getAllProfileMastersBySectionId(lookUp, organisation, Flag);
    }

    @Override
    @Transactional
    public ProfileMaster getProfileMasterById(final long rowId) {
        return profileMasterDAO.getProfileMasterByProfileId(rowId);
    }

    @Override
    @Transactional
    public boolean delete(final long rowId) {
        final ProfileMaster profileMaster = getProfileMasterById(rowId);
        profileMaster.setIsDeleted(MainetConstants.IsDeleted.DELETE);

        return profileMasterDAO.saveOrUpdate(profileMaster,profileMaster.getChekkerflag1());
    }

    @Override
    @Transactional
    public boolean saveOrUpdate(final ProfileMaster profileMasterObj,String chekkerflag) {

        profileMasterObj.updateAuditFields();
        if(MainetConstants.FLAGY.equalsIgnoreCase(profileMasterObj.getChekkerflag())){
    		final String isSuperAdmin = iEntitlementService.getGroupCodeById(UserSession.getCurrent().getEmployee().getGmid(),UserSession.getCurrent().getOrganisation().getOrgid());
	    	if(!(isSuperAdmin.equalsIgnoreCase(MainetConstants.GROUPMASTER.ADMIN_GROUP_CODE) || isSuperAdmin.equalsIgnoreCase(MainetConstants.GROUPMASTER.ADMIN_GR_BOTH))) {
             	long gmid = iEntitlementService.getGroupIdByName(MainetConstants.MENU.APPROVER,
                         UserSession.getCurrent().getOrganisation().getOrgid());
                 if (gmid != UserSession.getCurrent().getEmployee().getGmid()) {
                 	LOG.error(MainetConstants.MAKER_CHEKER_ERROR +  UserSession.getCurrent().getEmployee().getEmploginname() +  UserSession.getCurrent().getEmployee().getEmpmobno());
                 	profileMasterObj.setChekkerflag(MainetConstants.FLAGN);
                 }
	    	}
         }
        return profileMasterDAO.saveOrUpdate(profileMasterObj,chekkerflag);

    }

    @Override
    @Transactional
    public List<ProfileMaster> getAllProfileMaster(final List<LookUp> lookUps, final Organisation organisation) {
        return profileMasterDAO.getAllProfileMasters(lookUps, organisation);
    }

    @Override
    @Transactional
    public ProfileMaster getProfileMasterByViewId(final long rowId) {
        return profileMasterDAO.getProfileMasterByProfileId(rowId);
    }

    @Override
    public List<ProfileMasterHistory> getGuestAllProfileMaster(
            final List<LookUp> lookUps, final Organisation organisation) {

        return profileMasterDAO.getGuestAllProfileMasters(lookUps, organisation);
    }
}
