package com.abm.mainet.cms.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.abm.mainet.cms.domain.EIPAnnouncementLanding;
import com.abm.mainet.cms.domain.EIPAnnouncementLandingHistory;
import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.domain.Organisation;

/**
 * /*@author rajdeep.sinha
 */

@Repository
public class EipAnnouncementLandingDAO extends AbstractDAO<EIPAnnouncementLanding> implements IEipAnnouncementLandingDAO {

    private static final Logger LOG = Logger.getLogger(EipAnnouncementLandingDAO.class);

    @Autowired
    private AuditService auditService;

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.IEipAnnouncementLandingDAO#getEIPAnnouncementLanding()
     */
    @Override
    public List<EIPAnnouncementLanding> getEIPAnnouncementLanding(final Organisation organisation) {

        final Query query = createQuery(
                "select e from EIPAnnouncementLanding e where e.isDeleted = ?1 and e.orgId = ?2   order by e.lmodDate desc");
        query.setParameter(1, MainetConstants.IsDeleted.NOT_DELETE);
        query.setParameter(2, organisation);
        @SuppressWarnings("unchecked")
        final List<EIPAnnouncementLanding> list = query.getResultList();
        return list;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.IEipAnnouncementLandingDAO#saveOrUpdate(com.abm.mainet.eip.domain.core.EIPAnnouncementLanding)
     */
    @Override
    public boolean saveOrUpdate(final EIPAnnouncementLanding eipAnnouncementLanding) {

        try {

            entityManager.merge(eipAnnouncementLanding);

            EIPAnnouncementLandingHistory landingHistory = new EIPAnnouncementLandingHistory();

            if (eipAnnouncementLanding.getAnnounceId() == 0L) {
                landingHistory.setStatus(MainetConstants.InsertMode.ADD.getStatus());
                auditService.createHistory(eipAnnouncementLanding, landingHistory);
            } else {
                if (eipAnnouncementLanding.getIsDeleted().equalsIgnoreCase(MainetConstants.IsDeleted.DELETE)) {
                    landingHistory.setStatus(MainetConstants.InsertMode.DELETE.getStatus());
                } else {
                    landingHistory.setStatus(MainetConstants.InsertMode.UPDATE.getStatus());
                }
                auditService.createHistory(eipAnnouncementLanding, landingHistory);

            }
            return true;
        } catch (

        final Exception e) {
            LOG.error(MainetConstants.ERROR_OCCURED_SAVEORUPDATE, e);
            return false;
        }
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.IEipAnnouncementLandingDAO#getEipAnnouncementLandingById(long)
     */
    @Override
    public EIPAnnouncementLanding getEipAnnouncementLandingById(final long announceId) {

        final Query query = createQuery("select e from EIPAnnouncementLanding e where e.announceId = ?1");
        query.setParameter(1, announceId);
        @SuppressWarnings("unchecked")
        final List<EIPAnnouncementLanding> eipAnnouncementLandings = query.getResultList();
        if ((eipAnnouncementLandings == null) || eipAnnouncementLandings.isEmpty()) {
            return null;
        } else {
            return eipAnnouncementLandings.get(0);
        }
    }

    @Override
    public List<EIPAnnouncementLanding> getLandingpageEIPAnnouncement(
            final Organisation organisation) {
        final Query query = createQuery(
                "select e from EIPAnnouncementLanding e where e.isDeleted = ?1 and e.orgId = ?2 and e.chekkerflag='Y'  order by e.lmodDate desc");
        query.setParameter(1, MainetConstants.IsDeleted.NOT_DELETE);
        query.setParameter(2, organisation);
        @SuppressWarnings("unchecked")
        final List<EIPAnnouncementLanding> list = query.getResultList();
        return list;
    }

}
