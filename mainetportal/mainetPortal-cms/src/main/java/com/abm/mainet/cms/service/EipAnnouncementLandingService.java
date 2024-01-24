package com.abm.mainet.cms.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.cms.dao.IEipAnnouncementLandingDAO;
import com.abm.mainet.cms.domain.EIPAnnouncementLanding;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.util.ApplicationSession;

@Service
public class EipAnnouncementLandingService implements IEipAnnouncementLandingService, Serializable {

    /**
     * /*@author rajdeep.sinha
     */
    private static final long serialVersionUID = -3889699001601462434L;

    @Autowired
    IEipAnnouncementLandingDAO EipAnnouncementLandingDAO;

    @Override
    @Transactional
    public List<EIPAnnouncementLanding> getAllEIPAnnouncementLanding(final Organisation organisation) {

        return EipAnnouncementLandingDAO.getEIPAnnouncementLanding(organisation);
    }

    @Override
    @Transactional
    public boolean saveOrUpdate(final EIPAnnouncementLanding eipAnnouncementLanding) {

        eipAnnouncementLanding.setIsDeleted(MainetConstants.IsDeleted.NOT_DELETE);
        eipAnnouncementLanding.updateAuditFields();
        eipAnnouncementLanding.setOrgId(ApplicationSession.getInstance().getSuperUserOrganization());

        return EipAnnouncementLandingDAO.saveOrUpdate(eipAnnouncementLanding);

    }

    @Transactional
    @Override
    public EIPAnnouncementLanding getEipAnnouncementLanding(final long announceId) {

        return EipAnnouncementLandingDAO.getEipAnnouncementLandingById(announceId);
    }

    @Transactional
    @Override
    public boolean delete(final long announceId) {

        final EIPAnnouncementLanding eipAnnouncementLanding = getEipAnnouncementLanding(announceId);
        eipAnnouncementLanding.setIsDeleted(MainetConstants.IsDeleted.DELETE);
        eipAnnouncementLanding.updateAuditFields();

        return EipAnnouncementLandingDAO.saveOrUpdate(eipAnnouncementLanding);
    }

    @Override
    public List<EIPAnnouncementLanding> getLandingEIPAnnouncementLanding(
            final Organisation organisation) {
        
        return EipAnnouncementLandingDAO.getLandingpageEIPAnnouncement(organisation);
    }

}
