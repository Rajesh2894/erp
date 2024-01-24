package com.abm.mainet.cms.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Hibernate;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.abm.mainet.cms.domain.PublicNotices;
import com.abm.mainet.cms.domain.PublicNoticesHistory;
import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.domain.Organisation;

/**
 * @author swapnil.shirke
 *
 */
@Repository
@Primary
public class PublicNoticesDAO extends AbstractDAO<PublicNotices> implements IPublicNoticesDAO {
    private static final Logger LOG = Logger.getLogger(PublicNoticesDAO.class);

    @Autowired
    private AuditService auditService;

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.IPublicNoticesDAO#getPublicNotices()
     */
    @Override
    public List<PublicNotices> getPublicNotices(final Organisation organisation) {
        final Query query = createQuery(
                "select p from PublicNotices p where p.orgId = ?1 and p.isDeleted = ?2 and p.publishFlag = ?3 and p.validityDate > ?4");
        query.setParameter(1, organisation);
        query.setParameter(2, MainetConstants.IsDeleted.NOT_DELETE);
        query.setParameter(3, MainetConstants.Common_Constant.NUMBER.ONE);
        final Calendar calender = Calendar.getInstance();
        calender.set(Calendar.MINUTE, 0);
        calender.set(Calendar.MILLISECOND, 0);
        calender.set(Calendar.HOUR_OF_DAY, 0);
        calender.set(Calendar.SECOND, 0);
        query.setParameter(4, calender.getTime());
        // query.setParameter(5, calender.getTime());
        @SuppressWarnings("unchecked")
        final List<PublicNotices> list = query.getResultList();
        for (final PublicNotices publicNotices : list) {
            Hibernate.initialize(publicNotices.getDepartment());
        }
        return list;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.IPublicNoticesDAO#getPublicNotices()
     */
    @Override
    public List<PublicNotices> getAdminPublicNotices(final Organisation organisation, String flag) {

        final StringBuilder queryAppender = new StringBuilder(
                "select p from PublicNotices p where p.orgId = ?1 and p.isDeleted = ?2");
        if (flag != null) {
            queryAppender
                    .append(" and p.chekkerflag =?3");
        } else {
            queryAppender
                    .append(" and p.chekkerflag is null");
        }
        queryAppender
                .append(" order by p.pnId desc");
        final Query query = createQuery(queryAppender.toString());
        query.setParameter(1, organisation);
        query.setParameter(2, MainetConstants.IsDeleted.NOT_DELETE);
        if (flag != null) {
            query.setParameter(3, flag);
        }
        final List<PublicNotices> publicNotices = query.getResultList();

        return publicNotices;

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.IPublicNoticesDAO#getPublicNoticesByDeptId(long)
     */
    @Override
    public List<PublicNotices> getPublicNoticesByDeptId(final long deptId, final Organisation organisation) {
        final Query query = createQuery(
                "select p from PublicNotices p where p.orgId = ?1 and p.isDeleted = ?2 and p.department.dpDeptid = ?3 ");
        query.setParameter(1, organisation);
        query.setParameter(2, MainetConstants.IsDeleted.NOT_DELETE);
        query.setParameter(3, deptId);
        @SuppressWarnings("unchecked")
        final List<PublicNotices> list = query.getResultList();
        return list;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.IPublicNoticesDAO#saveOrUpdate(com.abm.mainet.eip.domain.core.PublicNotices)
     */
    @Override
    public boolean saveOrUpdate(final PublicNotices publicNotices) {
        try {

            PublicNoticesHistory noticesHistory = new PublicNoticesHistory();

            if (publicNotices.getPnId() == 0) {
                noticesHistory.setStatus(MainetConstants.InsertMode.ADD.getStatus());
                entityManager.persist(publicNotices);
            } else {
                if (publicNotices.getIsDeleted().equalsIgnoreCase(MainetConstants.IsDeleted.DELETE)) {
                    noticesHistory.setStatus(MainetConstants.InsertMode.DELETE.getStatus());
                } else {
                    noticesHistory.setStatus(MainetConstants.InsertMode.UPDATE.getStatus());
                }
                if (publicNotices.getChekkerflag1()!=null && !publicNotices.getChekkerflag1().isEmpty() && publicNotices.getChekkerflag1().equalsIgnoreCase("Y")) {
                    entityManager.merge(publicNotices);
                } else {
                    publicNotices.setChekkerflag(null);
                    entityManager.merge(publicNotices);
                }
            }
            auditService.createHistory(publicNotices, noticesHistory);
            return true;
        } catch (final Exception e) {
            LOG.error(MainetConstants.ERROR_OCCURED_SAVEORUPDATE, e);

            return false;
        }
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.IPublicNoticesDAO#getpublicNoticesById(long)
     */
    @Override
    public PublicNotices getpublicNoticesById(final long pnId) {
        final Query query = createQuery("select p from PublicNotices p where p.pnId = ?1 ");
        query.setParameter(1, pnId);
        final PublicNotices publicNotices = (PublicNotices) query.getSingleResult();
        Hibernate.initialize(publicNotices.getDepartment());
        return publicNotices;
    }

    @Override
    public List<PublicNoticesHistory> getGuestPublicNotices(final Organisation organisation) {
        List<PublicNoticesHistory> announcementHistories = new ArrayList<>();
        final Query query = createQuery(
                "select p.pnId from PublicNotices p where p.chekkerflag='Y' and p.orgId = ?1 and p.isDeleted = ?2 and p.publishFlag = ?3 and p.validityDate >= ?4");
        query.setParameter(1, organisation);
        query.setParameter(2, MainetConstants.IsDeleted.NOT_DELETE);
        query.setParameter(3, MainetConstants.Common_Constant.NUMBER.ONE);
        /*final Calendar calender = Calendar.getInstance();
        calender.set(Calendar.MINUTE, 0);
        calender.set(Calendar.MILLISECOND, 0);
        calender.set(Calendar.HOUR_OF_DAY, 0);
        calender.set(Calendar.SECOND, 0);
        query.setParameter(4, calender.getTime());*/
        query.setParameter(4, new Date());
        // query.setParameter(5, calender.getTime());
        @SuppressWarnings("unchecked")
        final List<Long> noticeIdList = query.getResultList();

        if (noticeIdList != null && !noticeIdList.isEmpty()) {
            final Query query2 = createQuery(
                    "select h from PublicNoticesHistory h where h.chekkerflag='Y' and h.pnId in ?1 and (h.pnId,h.updatedDate) in (select eh.pnId,max(eh.updatedDate) from PublicNoticesHistory eh where eh.chekkerflag='Y' and eh.pnId in ?1 group by eh.pnId) order by h.issueDate desc");
            query2.setParameter(1, noticeIdList);
            announcementHistories = query2.getResultList();
        }

        return announcementHistories;
    }
    
    @Override
    public boolean checkSequence(final long sequenceNo,Long orgId) {
    	  final Query query = createQuery("select p.pnId from PublicNotices p where p.orgId.orgid = ?1 and p.isDeleted = ?2 and p.seqNo >= ?3");
          query.setParameter(1, orgId);
          query.setParameter(2, MainetConstants.IsDeleted.NOT_DELETE);
          query.setParameter(3, sequenceNo);
          final List<Long> noticeIdList = query.getResultList();
          if(noticeIdList.size()>0)
          {
        	return true;  
          }
          return false;
    }
    
}
