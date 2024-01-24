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
import com.abm.mainet.cms.domain.OpinionPollOptionResponse;
import com.abm.mainet.cms.domain.PublicNotices;
import com.abm.mainet.cms.domain.PublicNoticesHistory;
import com.abm.mainet.cms.dto.OpinionPollDTO;
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
public class OpinionPollOptionResponseDAO extends AbstractDAO<OpinionPollOptionResponse> implements IOpinionPollOptionResponseDAO {
    private static final Logger LOG = Logger.getLogger(PublicNoticesDAO.class);

    @Autowired
    private AuditService auditService;

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.IPublicNoticesDAO#getPublicNotices()
     */
    @Override
    public List<OpinionPollOptionResponse> getOpinionPollOptionResponses(final Organisation organisation) {
        final Query query = createQuery(
                "select p from OpinionPollOptionResponse p where p.orgId = ?1 and p.isDeleted = ?2 and p.publishFlag = ?3 and p.validityDate > ?4");
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
        final List<OpinionPollOptionResponse> list = query.getResultList();
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
    public List<OpinionPollOptionResponse> getAdminOpinionPollOptionResponses(final Organisation organisation, String flag) {

        final StringBuilder queryAppender = new StringBuilder(
                "select p from OpinionPollOptionResponse p where p.orgId = ?1 and p.isDeleted = ?2");
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
        final List<OpinionPollOptionResponse> opinionPollOptionResponse = query.getResultList();

        return opinionPollOptionResponse;

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.IPublicNoticesDAO#getPublicNoticesByDeptId(long)
     */
    @Override
    public List<OpinionPollOptionResponse> getOpinionPollOptionResponsesByDeptId(final long deptId, final Organisation organisation) {
        final Query query = createQuery(
                "select p from OpinionPollOptionResponse p where p.orgId = ?1 and p.isDeleted = ?2 and p.department.dpDeptid = ?3 ");
        query.setParameter(1, organisation);
        query.setParameter(2, MainetConstants.IsDeleted.NOT_DELETE);
        query.setParameter(3, deptId);
        @SuppressWarnings("unchecked")
        final List<OpinionPollOptionResponse> list = query.getResultList();
        return list;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.IPublicNoticesDAO#saveOrUpdate(com.abm.mainet.eip.domain.core.PublicNotices)
     */
    @Override
    public boolean saveOrUpdate(final OpinionPollOptionResponse opinionPollOptionResponse) {
        try {
        	entityManager.persist(opinionPollOptionResponse);
            
            return true;
        } catch (final Exception e) {
            LOG.error(MainetConstants.ERROR_OCCURED_SAVEORUPDATE, e);

            return false;
        }
    }

   
    
    @Override
    public List<OpinionPollOptionResponse> getOpinionPollOptionResponsesByOpinioId(long pnId) {
        final Query query = createQuery("select p from OpinionPollOptionResponse p where p.pnId = ?1 ");
        query.setParameter(1, pnId);
        final List<OpinionPollOptionResponse> opinionPollOptionResponse = query.getResultList();
        return opinionPollOptionResponse;
    }

  
    public List<OpinionPollOptionResponse> getCitizenOpinionPollOptionResponsesByOpinionPollId(final Organisation organisation,final long pnId){
    	final Query query = createQuery(
                "select p from OpinionPollOptionResponse p where p.orgId = ?1 and p.isDeleted = ?2 and p.pnId = ?3 ");
        query.setParameter(1, organisation);
        query.setParameter(2, MainetConstants.IsDeleted.NOT_DELETE);
        query.setParameter(3, pnId);
        @SuppressWarnings("unchecked")
        final List<OpinionPollOptionResponse> list = query.getResultList();
        return list;
    }
    

	public List<OpinionPollDTO> getOpinionPolls(long pid) {
		List<OpinionPollDTO> opinionPollDTO = new ArrayList<OpinionPollDTO>();
		final Query query = createQuery("select count(*) as ocount,p.pOptionId as id,po.optionEn as eng,po.optionReg as reg from OpinionPollOptionResponse p,OpinionPollOption po where p.pOptionId = po.pOptionId and po.isDeleted != 'Y' and p.pnId = "+pid+" group by p.pOptionId");
		
		

		try {
			List<Object[]> results = query.getResultList();
	    	for (Object[] row : results) {
	    		OpinionPollDTO container = new OpinionPollDTO();
	    		container.setOcount((long) row[0]);
	    		container.setId((long) row[1]);
	    	    container.setEng((String) row[2]);
	    	    container.setReg((String) row[3]);

	    	    opinionPollDTO.add(container);
	    	}
		}catch(Exception e) {
			LOG.error(MainetConstants.ERROR_OCCURED_SAVEORUPDATE, e);
		}
		return opinionPollDTO;
	}
}
