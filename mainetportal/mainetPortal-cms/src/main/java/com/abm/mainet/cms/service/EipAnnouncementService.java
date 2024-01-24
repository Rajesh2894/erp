package com.abm.mainet.cms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.cms.dao.IEipAnnouncementDAO;
import com.abm.mainet.cms.domain.EIPAnnouncement;
import com.abm.mainet.cms.domain.EIPAnnouncementHistory;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;

@Service
public class EipAnnouncementService implements IEipAnnouncementService {

    /**
    *
    */
    private static final long serialVersionUID = 5926370569974974790L;
    @Autowired
    IEipAnnouncementDAO EipAnnouncementDAO;
    
    @Override
    @Transactional
    public boolean saveOrUpdate(final EIPAnnouncement EIPAnnouncement) {
	
		/* EIPAnnouncement.setIsDeleted(MainetConstants.IsDeleted.NOT_DELETE); */
			
      
        EIPAnnouncement.updateAuditFields();
        return EipAnnouncementDAO.saveOrUpdate(EIPAnnouncement);
    }

    @Override
    @Transactional
    public List<EIPAnnouncement> getAllEIPAnnouncement(final Organisation organisation, String flag,String env) {
        return EipAnnouncementDAO.getEIPAnnouncement(organisation, flag, env);
    }

    @Transactional
    @Override
    public EIPAnnouncement getEIPAnnouncement(final long announceId) {
        return EipAnnouncementDAO.getEIPAnnouncementById(announceId);
    }

    @Transactional
    @Override
    public boolean delete(final long announceId) {
        final EIPAnnouncement EIPAnnouncement = getEIPAnnouncement(announceId);
        EIPAnnouncement.setIsDeleted(MainetConstants.IsDeleted.DELETE);
        EIPAnnouncement.updateAuditFields();
        return EipAnnouncementDAO.saveOrUpdate(EIPAnnouncement);
    }

    @Override
    public List<EIPAnnouncementHistory> getGuestHomeEIPAnnouncement(
            final Organisation organisation) {

        return EipAnnouncementDAO.getGuestHomeEIPAnnouncement(organisation);
    }

}
