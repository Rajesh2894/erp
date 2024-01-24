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

import com.abm.mainet.cms.domain.OpinionPoll;
import com.abm.mainet.cms.domain.OpinionPollHistory;
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
public class OpinionPollDAO extends AbstractDAO<OpinionPoll> implements IOpinionPollDAO {
    private static final Logger LOG = Logger.getLogger(PublicNoticesDAO.class);

    @Autowired
    private AuditService auditService;

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.IPublicNoticesDAO#getPublicNotices()
     */
    @Override
    public List<OpinionPoll> getOpinionPolls(final Organisation organisation) {
        final Query query = createQuery(
                "select p from OpinionPoll p where p.orgId = ?1 and p.isDeleted = ?2 and p.publishFlag = ?3 and p.validityDate > ?4");
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
        final List<OpinionPoll> list = query.getResultList();
		/*
		 * for (final OpinionPoll opinionPoll : list) {
		 * Hibernate.initialize(opinionPoll.getDepartment()); }
		 */
        return list;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.IPublicNoticesDAO#getPublicNotices()
     */
    @Override
    public List<OpinionPoll> getAdminOpinionPolls(final Organisation organisation, String flag) {

        final StringBuilder queryAppender = new StringBuilder(
                "select p from OpinionPoll p where p.orgId = ?1 and p.isDeleted = ?2");
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
        final List<OpinionPoll> opinionPoll = query.getResultList();

        return opinionPoll;

    }

    

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.IPublicNoticesDAO#saveOrUpdate(com.abm.mainet.eip.domain.core.PublicNotices)
     */
    @Override
    public boolean saveOrUpdate(final OpinionPoll opinionPoll) {
        try {

        	OpinionPollHistory opinionPollHistory = new OpinionPollHistory();

            if (opinionPoll.getPnId() == 0) {
            	//opinionPollHistory.setStatus(MainetConstants.InsertMode.ADD.getStatus());
                entityManager.persist(opinionPoll);
            } else {
				/*
				 * if
				 * (opinionPoll.getIsDeleted().equalsIgnoreCase(MainetConstants.IsDeleted.DELETE
				 * )) {
				 * opinionPollHistory.setStatus(MainetConstants.InsertMode.DELETE.getStatus());
				 * } else {
				 * opinionPollHistory.setStatus(MainetConstants.InsertMode.UPDATE.getStatus());
				 * }
				 */
                if (opinionPoll.getChekkerflag1()!=null && !opinionPoll.getChekkerflag1().isEmpty() && opinionPoll.getChekkerflag1().equalsIgnoreCase("Y")) {
                    entityManager.merge(opinionPoll);
                } else {
                	opinionPoll.setChekkerflag(null);
                    entityManager.merge(opinionPoll);
                }
            }
            auditService.createHistory(opinionPoll, opinionPollHistory);
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
    public OpinionPoll getOpinionPollById(final long pnId) {
        final Query query = createQuery("select p from OpinionPoll p where p.pnId = ?1 ");
        query.setParameter(1, pnId);
        final OpinionPoll opinionPoll = (OpinionPoll) query.getSingleResult();
        //Hibernate.initialize(opinionPoll.getDepartment());
        return opinionPoll;
    }

    @Override
    public List<OpinionPollHistory> getGuestOpinionPolls(final Organisation organisation) {
        List<OpinionPollHistory> opinionPollHistories = new ArrayList<>();
        final Query query = createQuery(
                "select p.pnId from OpinionPoll p where p.orgId = ?1 and p.isDeleted = ?2 and p.publishFlag = ?3 and p.validityDate >= ?4");
        query.setParameter(1, organisation);
        query.setParameter(2, MainetConstants.IsDeleted.NOT_DELETE);
        query.setParameter(3, MainetConstants.Common_Constant.NUMBER.ONE);
        query.setParameter(4, new Date());
        @SuppressWarnings("unchecked")
        final List<Long> noticeIdList = query.getResultList();

        if (noticeIdList != null && !noticeIdList.isEmpty()) {
            final Query query2 = createQuery(
                    "select h from OpinionPollHistory h where h.chekkerflag='Y' and h.pnId in ?1 and (h.pnId,h.updatedDate) in (select eh.pnId,max(eh.updatedDate) from OpinionPollHistory eh where eh.chekkerflag='Y' and eh.pnId in ?1 group by eh.pnId) order by h.issueDate desc");
            query2.setParameter(1, noticeIdList);
            opinionPollHistories = query2.getResultList();
        }

        return opinionPollHistories;
    }
    
    
    
    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.IPublicNoticesDAO#getPublicNotices()
     */
    @Override
    public List<OpinionPoll> getAdminOpinionPollsByValidityAndIssueDate(final Organisation organisation, String flag) {

		/*
		 * final Calendar calender = Calendar.getInstance();
		 * calender.set(Calendar.MINUTE, 0); calender.set(Calendar.MILLISECOND, 0);
		 * calender.set(Calendar.HOUR_OF_DAY, 0); calender.set(Calendar.SECOND, 0);
		 */
        
        //and p.issueDate < ?3
        final StringBuilder queryAppender = new StringBuilder(
                "select p from OpinionPoll p where p.orgId = ?1 and p.chekkerflag =?2 and isDeleted =?3 and p.validityDate > ?4 and p.issueDate < ?4 ");
        
        queryAppender
                .append(" order by p.pnId desc");
        final Query query = createQuery(queryAppender.toString());
        query.setParameter(1, organisation);
        query.setParameter(2, MainetConstants.IsDeleted.DELETE);
        query.setParameter(3, MainetConstants.IsDeleted.NOT_DELETE);
        query.setParameter(4, new Date()); 
        
        final List<OpinionPoll> opinionPoll = query.getResultList();

        return opinionPoll;

    }
    
    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.IPublicNoticesDAO#getPublicNotices()
     */
    @Override
    public List<OpinionPoll> getAdminOpinionPollsByValidityAndIssueDate(final Organisation organisation, String flag,Date validityDate,Date issueDate) {

		/*
		 * final Calendar calender = Calendar.getInstance();
		 * calender.set(Calendar.MINUTE, 0); calender.set(Calendar.MILLISECOND, 0);
		 * calender.set(Calendar.HOUR_OF_DAY, 0); calender.set(Calendar.SECOND, 0);
		 */
        
        //and p.issueDate < ?3
        final StringBuilder queryAppender = new StringBuilder(
                "select p from OpinionPoll p where p.orgId = ?1 and p.chekkerflag =?2 and isDeleted =?3 and p.validityDate >= ?4  ");
        
        queryAppender
                .append(" order by p.pnId desc");
        final Query query = createQuery(queryAppender.toString());
        query.setParameter(1, organisation);
        query.setParameter(2, MainetConstants.IsDeleted.DELETE);
        query.setParameter(3, MainetConstants.IsDeleted.NOT_DELETE);
        query.setParameter(4, issueDate);
        //query.setParameter(5, validityDate);
        
        final List<OpinionPoll> opinionPoll = query.getResultList();

        return opinionPoll;

    }
    
}
