package com.abm.mainet.cms.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.abm.mainet.cms.domain.ProfileMaster;
import com.abm.mainet.cms.domain.ProfileMasterHistory;
import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.util.LookUp;

@Repository
public class ProfileMasterDAO extends AbstractDAO<ProfileMaster> implements IProfileMasterDAO {
    private static final Logger LOG = Logger.getLogger(ProfileMasterDAO.class);

    @Autowired
    private AuditService auditService;

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.IProfileMasterDAO#getAllProfileMastersBySectionId(com.abm.mainet.ao2.smart.util.LookUp)
     */
    @Override
    public List<ProfileMaster> getAllProfileMastersBySectionId(final LookUp lookUp, final Organisation organisation,
            String flag) {
        final StringBuilder queryAppender = new StringBuilder(
                "Select p from ProfileMaster p where p.cpdSection = ?1 and p.isDeleted = ?2 and p.orgId =?3 ");
        if (flag != null) {
            queryAppender
                    .append("and p.chekkerflag =?4 ");
        } else {
            queryAppender
                    .append("and p.chekkerflag is null");
        }
        queryAppender
                .append(" order by p.lmodDate desc");
        final Query query = createQuery(queryAppender.toString());
        query.setParameter(1, lookUp.getLookUpId());
        query.setParameter(2, MainetConstants.IsDeleted.NOT_DELETE);
        query.setParameter(3, organisation);
        if (flag != null) {
            query.setParameter(4, flag);
        }
        final List<ProfileMaster> profileMasters = query.getResultList();

        return profileMasters;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.IProfileMasterDAO#getProfileMasterByProfileId(long)
     */
    @Override
    public ProfileMaster getProfileMasterByProfileId(final long profileId) {
        final Query query = createQuery("Select p from ProfileMaster p where p.profileId = ?1 ");
        query.setParameter(1, profileId);
        final ProfileMaster profileMaster = (ProfileMaster) query.getSingleResult();
        return profileMaster;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.IProfileMasterDAO#saveOrUpdate(com.abm.mainet.eip.domain.core.ProfileMaster)
     */
    @Override
    public boolean saveOrUpdate(ProfileMaster profileMaster, String chekkerflag) {
        try {
            ProfileMasterHistory profileMasterHistory = new ProfileMasterHistory();
            if (profileMaster.getProfileId() == 0L) {
                profileMasterHistory.setStatus(MainetConstants.InsertMode.ADD.getStatus());
                entityManager.persist(profileMaster);
            } else {
                if (profileMaster.getIsDeleted().equalsIgnoreCase("Y")) {
                    profileMasterHistory.setStatus(MainetConstants.InsertMode.DELETE.getStatus());
                } else {
                    profileMasterHistory.setStatus(MainetConstants.InsertMode.UPDATE.getStatus());
                }
                if (profileMaster.getChekkerflag1() != null && profileMaster.getChekkerflag1().equalsIgnoreCase("Y")) {
                    profileMaster = entityManager.merge(profileMaster);
                } else {
                    profileMaster.setChekkerflag(null);
                    profileMaster = entityManager.merge(profileMaster);
                }
            }
            auditService.createHistory(profileMaster, profileMasterHistory);
            return true;
        } catch (final Exception e) {
            LOG.error(MainetConstants.ERROR_OCCURED_SAVEORUPDATE, e);

            return false;
        }
    }

    @Override
    public List<ProfileMaster> getAllProfileMasters(final List<LookUp> lookUp, final Organisation organisation) {

        final List<Long> list = new ArrayList<>();
        for (final LookUp eachLookup : lookUp) {
            list.add(eachLookup.getLookUpId());
        }

        final Query query = createQuery(
                "Select p from ProfileMaster p where p.cpdSection in ?1 and p.isDeleted = ?2 and p.orgId =?3 order by p.cpdSection asc , p.lmodDate desc");
        query.setParameter(1, list);
        query.setParameter(2, MainetConstants.IsDeleted.NOT_DELETE);
        query.setParameter(3, organisation);

        @SuppressWarnings("unchecked")
        final List<ProfileMaster> profileMasters = query.getResultList();
        return profileMasters;
    }

    @Override
    public List<ProfileMasterHistory> getGuestAllProfileMasters(final List<LookUp> lookUp, final Organisation organisation) {

        final List<Long> list = new ArrayList<>();
        List<ProfileMasterHistory> profileMasterHistroy = new ArrayList<>();
        if (lookUp != null) {
            try {
                for (final LookUp eachLookup : lookUp) {
                    if (null != eachLookup) {
                        list.add(eachLookup.getLookUpId());
                    }
                }
            } catch (final Exception e) {
                LOG.error(MainetConstants.GET_GUESTAll_PROFILEMASTERS_ERROR, e);

                return null;
            }

            final Query query = createQuery(
                    "Select p.profileId from ProfileMaster p where p.cpdSection in ?1 and p.isDeleted = ?2 and p.orgId =?3 order by p.cpdSection asc , p.updatedDate desc");
            query.setParameter(1, list.isEmpty() ? 0L : list);
            query.setParameter(2, MainetConstants.IsDeleted.NOT_DELETE);
            query.setParameter(3, organisation);

            @SuppressWarnings("unchecked")
            final List<Long> profileId = query.getResultList();

            if (profileId != null && !profileId.isEmpty()) {
                final Query query2 = createQuery(
                        "select h from ProfileMasterHistory h where h.chekkerflag='Y' and h.profileId in ?1 and (h.profileId,h.updatedDate) in (select eh.profileId,max(eh.updatedDate) from ProfileMasterHistory eh where eh.chekkerflag='Y' and eh.profileId in ?1 group by eh.profileId) order by h.cpdSection asc , h.updatedDate desc");
                query2.setParameter(1, profileId);
                profileMasterHistroy = query2.getResultList();
            }
        }
        if ((profileMasterHistroy == null) || profileMasterHistroy.isEmpty()) {
            return null;
        } else {
            return profileMasterHistroy;
        }

    }

}
