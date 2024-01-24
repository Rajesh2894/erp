package com.abm.mainet.cms.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.abm.mainet.cms.domain.EIPAnnouncement;
import com.abm.mainet.cms.domain.EIPAnnouncementHistory;
import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.domain.Organisation;

@Repository
@Primary
public class EipAnnouncementDAO extends AbstractDAO<EIPAnnouncement> implements IEipAnnouncementDAO {

    private static final Logger LOG = Logger.getLogger(EipAnnouncementDAO.class);

    @Autowired
    private AuditService auditService;

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.IEipAnnouncementDAO#getEIPAnnouncement()
     */
    @Override
    public List<EIPAnnouncement> getEIPAnnouncement(final Organisation organisation, String flag ,String env) {

        final StringBuilder queryAppender = new StringBuilder(
                "select e from EIPAnnouncement e where e.orgId = ?2 ");
        
        if(!"PSCL".equals(env)) {
        	queryAppender
            .append("and e.isDeleted ='N' ");
        	
        }
        if (flag != null) {
            queryAppender
                    .append("and e.chekkerflag =?3 ");
        } else {
            queryAppender
                    .append("and e.chekkerflag is null ");
        }
        queryAppender
                .append("order by e.lmodDate desc");
        final Query query = createQuery(queryAppender.toString());
      //  query.setParameter(1, MainetConstants.IsDeleted.NOT_DELETE);
        query.setParameter(2, organisation);
        if (flag != null) {
            query.setParameter(3, flag);
        }

        final List<EIPAnnouncement> list = query.getResultList();
        return list;

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.IEipAnnouncementDAO#getEIPAnnouncementById(long)
     */
    @Override
    public EIPAnnouncement getEIPAnnouncementById(final long announceId) {
        final Query query = createQuery("select e from EIPAnnouncement e where e.announceId = ?1");
        query.setParameter(1, announceId);
        @SuppressWarnings("unchecked")
        final List<EIPAnnouncement> eipAnnouncements = query.getResultList();
        if ((eipAnnouncements == null) || eipAnnouncements.isEmpty()) {
            return null;
        } else {
            return eipAnnouncements.get(0);
        }

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.IEipAnnouncementDAO#saveOrUpdate(com.abm.mainet.eip.domain.core.EIPAnnouncement)
     */
    @Override
    public boolean saveOrUpdate(final EIPAnnouncement EIPAnnouncement) {

        try {

            EIPAnnouncementHistory announcementHistory = new EIPAnnouncementHistory();

            if (EIPAnnouncement.getAnnounceId() == 0) {
                announcementHistory.setStatus(MainetConstants.InsertMode.ADD.getStatus());
                entityManager.persist(EIPAnnouncement);
            } else {
                if (EIPAnnouncement.getIsDeleted().equalsIgnoreCase(MainetConstants.IsDeleted.DELETE)) {
                    announcementHistory.setStatus(MainetConstants.InsertMode.DELETE.getStatus());
                } else {
                    announcementHistory.setStatus(MainetConstants.InsertMode.UPDATE.getStatus());
                }
                if (EIPAnnouncement.getChekkerflag1()!=null && !EIPAnnouncement.getChekkerflag1().isEmpty() && EIPAnnouncement.getChekkerflag1().equalsIgnoreCase("Y")) {
                	 entityManager.merge(EIPAnnouncement);
                }else {
                    EIPAnnouncement.setChekkerflag(null);
                    entityManager.merge(EIPAnnouncement);
                	}
            }
            auditService.createHistory(EIPAnnouncement, announcementHistory);
            return true;
        } catch (final Exception e) {
            LOG.error(MainetConstants.ERROR_OCCURED_SAVEORUPDATE, e);
            return false;
        }

    }

    @Override
    public List<EIPAnnouncementHistory> getGuestHomeEIPAnnouncement(
            final Organisation organisation) {
        List<EIPAnnouncementHistory> announcementHistories = new ArrayList();
        final Query query = createQuery(
                "select e.announceId from EIPAnnouncement e where e.chekkerflag='Y' and e.isDeleted = ?1 and e.orgId = ?2 and e.validityDate >= ?3");
        query.setParameter(1, MainetConstants.IsDeleted.NOT_DELETE);
        query.setParameter(2, organisation);
        /*final Calendar calender = Calendar.getInstance();
        calender.set(Calendar.MINUTE, 0);
        calender.set(Calendar.MILLISECOND, 0);
        calender.set(Calendar.HOUR_OF_DAY, 0);
        calender.set(Calendar.SECOND, 0);
        query.setParameter(3, calender.getTime());*/
		query.setParameter(3, new Date());
        @SuppressWarnings("unchecked")
        final List<Long> eipAnnouncementList = query.getResultList();

        if (eipAnnouncementList != null && !eipAnnouncementList.isEmpty()) {
            final Query query2 = createQuery(
                    "select h from EIPAnnouncementHistory  h where h.chekkerflag='Y' and h.announceId in ?1  and (h.announceId,h.updatedDate) in (select eh.announceId,max(eh.updatedDate) from EIPAnnouncementHistory eh where eh.chekkerflag='Y' and eh.announceId in ?1 group by eh.announceId) order by h.newsDate desc, h.announceHistId desc");
            query2.setParameter(1, eipAnnouncementList);
            announcementHistories = query2.getResultList();
        }
        return announcementHistories;
    }
}
