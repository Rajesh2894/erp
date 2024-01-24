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

import com.abm.mainet.cms.domain.OpinionPoll;
import com.abm.mainet.cms.domain.OpinionPollHistory;
import com.abm.mainet.cms.domain.OpinionPollOption;
import com.abm.mainet.cms.domain.OpinionPollOptionHistory;
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
public class OpinionPollOptionDAO extends AbstractDAO<OpinionPollOption> implements IOpinionPollOptionDAO {
    private static final Logger LOG = Logger.getLogger(OpinionPollOptionDAO.class);

    @Autowired
    private AuditService auditService;

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.IPublicNoticesDAO#getPublicNotices()
     */
    @Override
    public List<OpinionPollOption> getOpinionPollOptions(final Organisation organisation) {
        final Query query = createQuery(
                "select p from OpinionPollOption p where p.orgId = ?1 and p.isDeleted = ?2 and p.publishFlag = ?3 and p.validityDate > ?4");
        query.setParameter(1, organisation);
        query.setParameter(2, MainetConstants.IsDeleted.NOT_DELETE);
        query.setParameter(3, MainetConstants.Common_Constant.NUMBER.ONE);
        final Calendar calender = Calendar.getInstance();
        calender.set(Calendar.MINUTE, 0);
        calender.set(Calendar.MILLISECOND, 0);
        calender.set(Calendar.HOUR_OF_DAY, 0);
        calender.set(Calendar.SECOND, 0);
        query.setParameter(4, calender.getTime());
        @SuppressWarnings("unchecked")
        final List<OpinionPollOption> list = query.getResultList();
        return list;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.IPublicNoticesDAO#getPublicNotices()
     */
    @Override
    public List<OpinionPollOption> getAdminOpinionPollOptions(final Organisation organisation, String flag) {

        final StringBuilder queryAppender = new StringBuilder(
                "select p from OpinionPollOption p where p.orgId = ?1 and p.isDeleted = ?2");
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
        final List<OpinionPollOption> opinionPollOption = query.getResultList();

        return opinionPollOption;

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.IPublicNoticesDAO#getPublicNoticesByDeptId(long)
     */
    @Override
    public List<OpinionPollOption> getOpinionPollOptionsByDeptId(final long deptId, final Organisation organisation) {
        final Query query = createQuery(
                "select p from OpinionPollOption p where p.orgId = ?1 and p.isDeleted = ?2 and p.department.dpDeptid = ?3 ");
        query.setParameter(1, organisation);
        query.setParameter(2, MainetConstants.IsDeleted.NOT_DELETE);
        query.setParameter(3, deptId);
        @SuppressWarnings("unchecked")
        final List<OpinionPollOption> list = query.getResultList();
        return list;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.IPublicNoticesDAO#saveOrUpdate(com.abm.mainet.eip.domain.core.PublicNotices)
     */
    @Override
    public boolean saveOrUpdate(final OpinionPollOption opinionPollOption) {
        try {

        	OpinionPollOptionHistory opinionPollOptionHistory = new OpinionPollOptionHistory();

            if (opinionPollOption.getPnId() == 0) {
                entityManager.persist(opinionPollOption);
            } else {
                if (opinionPollOption.getChekkerflag1()!=null && !opinionPollOption.getChekkerflag1().isEmpty() && opinionPollOption.getChekkerflag1().equalsIgnoreCase("Y")) {
                    entityManager.merge(opinionPollOption);
                } else {
                    entityManager.merge(opinionPollOption);
                }
            }
            auditService.createHistory(opinionPollOption, opinionPollOptionHistory);
            return true;
        } catch (final Exception e) {
            LOG.error(MainetConstants.ERROR_OCCURED_SAVEORUPDATE, e);

            return false;
        }
    }
    
    /*
     * Delete All Options 
     */
    @Override
    public boolean deleteAll(final long pnId,final long pOptionId) {
        try {
        	Query q = entityManager.createQuery("DELETE FROM OpinionPollOption ye WHERE ye.pnId ="+pnId+" and ye.pOptionId="+pOptionId);
            //q.setParameter("pnId", pnId);
            q.executeUpdate();
        	return true;
        } catch (final Exception e) {
            LOG.error(MainetConstants.ERROR_OCCURED_SAVEORUPDATE, e);

            return false;
        }
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.IPublicNoticesDAO#getOpinionPollOptionById(long)
     */
    @Override
    public OpinionPollOption getOpinionPollOptionById(final long pnId) {
    	try {
    		final Query query = createQuery("select p from OpinionPollOption p where p.pOptionId = ?1 ");
            query.setParameter(1, pnId);
            final OpinionPollOption opinionPollOption = (OpinionPollOption) query.getSingleResult();
            //Hibernate.initialize(opinionPoll.getDepartment());
            return opinionPollOption;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
        
    }

    @Override
    public List<OpinionPollOptionHistory> getGuestOpinionPollOptions(final Organisation organisation) {
        List<OpinionPollOptionHistory> opinionPollOptionHistories = new ArrayList<>();
        final Query query = createQuery(
                "select p.pnId from OpinionPollOption p where p.orgId = ?1 and p.isDeleted = ?2 and p.publishFlag = ?3 and p.validityDate >= ?4");
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
                    "select h from OpinionPollOptionHistory h where h.chekkerflag='Y' and h.pnId in ?1 and (h.pnId,h.updatedDate) in (select eh.pnId,max(eh.updatedDate) from OpinionPollHistory eh where eh.chekkerflag='Y' and eh.pnId in ?1 group by eh.pnId) order by h.issueDate desc");
            query2.setParameter(1, noticeIdList);
            opinionPollOptionHistories = query2.getResultList();
        }

        return opinionPollOptionHistories;
    }
    
    @Override
    public boolean checkSequence(final long sequenceNo,Long orgId) {
    	  final Query query = createQuery("select p.pnId from OpinionPollOption p where p.orgId.orgid = ?1 and p.isDeleted = ?2 and p.seqNo >= ?3");
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
    
    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.IPublicNoticesDAO#getOpinionPollOptionsByOpinionPollId(long)
     */
    @Override
    public List<OpinionPollOption> getOpinionPollOptionsByOpinionPollId(final long opinionPollId) {
        final Query query = createQuery(
                "select p from OpinionPollOption p where p.pnId = ?1 and p.isDeleted = 'N'");
        query.setParameter(1, opinionPollId);
        
        @SuppressWarnings("unchecked")
        final List<OpinionPollOption> list = query.getResultList();
        return list;
    }
}
