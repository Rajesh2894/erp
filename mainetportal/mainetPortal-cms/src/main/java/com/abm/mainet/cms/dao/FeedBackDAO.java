package com.abm.mainet.cms.dao;

import java.util.Collections;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.abm.mainet.cms.domain.Feedback;
import com.abm.mainet.cms.domain.FeedbackHistory;
import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;

/**
 * @author swapnil.shirke
 */
@Repository
public class FeedBackDAO extends AbstractDAO<Feedback> implements IFeedBackDAO {

    private static final Logger LOG = Logger.getLogger(FeedBackDAO.class);

    @Autowired
    private AuditService auditService;

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.IFeedBackDAO#saveOrUpdate(com.abm.mainet.eip.domain.core.Feedback)
     */

    @Override
    public boolean saveOrUpdate(final Feedback feedback,Organisation org) {
        try {

        	  final List<LookUp> innIdeaCategoryLookup = CommonMasterUtility.getLookUps(PrefixConstants.Prefix.IIC,
                      org);
            
        	  if(feedback != null && feedback.getCatagoryType() !=null) {
        		  if(innIdeaCategoryLookup !=null) {
          			innIdeaCategoryLookup.forEach(i->{
          				if(feedback.getCatagoryType().longValue() ==i.getLookUpId()) {
          					feedback.setCategoryTypeName(i.getLookUpDesc());
          				}
          			});
          			}
        	  }

            FeedbackHistory feedbackHistory = new FeedbackHistory();
            if (feedback.getFeedId() == 0L) {
                feedbackHistory.setStatus(MainetConstants.InsertMode.ADD.getStatus());

            } else {
                if (feedback.getIsDeleted().equalsIgnoreCase(MainetConstants.IsDeleted.DELETE)) {
                    feedbackHistory.setStatus(MainetConstants.InsertMode.DELETE.getStatus());
                } else {
                    feedbackHistory.setStatus(MainetConstants.InsertMode.UPDATE.getStatus());
                }

            }
            entityManager.persist(feedback);
            auditService.createHistory(feedback, feedbackHistory);

            return true;
        } catch (final Exception e) {
            LOG.error(MainetConstants.ERROR_OCCURED_SAVEORUPDATE, e);
            return false;
        }
    }

    @Override
    public boolean update(final Feedback feedback) {
        try {

            FeedbackHistory feedbackHistory = new FeedbackHistory();

            if (feedback.getIsDeleted().equalsIgnoreCase(MainetConstants.IsDeleted.DELETE)) {
                feedbackHistory.setStatus(MainetConstants.InsertMode.DELETE.getStatus());
            } else {
                feedbackHistory.setStatus(MainetConstants.InsertMode.UPDATE.getStatus());
            }

            entityManager.merge(feedback);
            auditService.createHistory(feedback, feedbackHistory);

            return true;
        } catch (final Exception e) {
            LOG.error(MainetConstants.ERROR_OCCURED_SAVEORUPDATE, e);
            return false;
        }
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.IFeedBackDAO#getAllFeedBack()
     */
    @Override
    public List<Feedback> getAllFeedBack() {

        final Query query = entityManager
                .createQuery("select f from Feedback f where f.orgId =?1 and f.isDeleted=?2 order by feedId desc");
        query.setParameter(1, UserSession.getCurrent().getOrganisation());
        query.setParameter(2, MainetConstants.IsDeleted.NOT_DELETE);
        @SuppressWarnings("unchecked")
        final List<Feedback> feedbacks = query.getResultList();
        LOG.info("FeedBack List Size Inside FeedBackDAO::getAllFeedBack--> "+feedbacks.size());
        return feedbacks;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.eip.dao.IFeedBackDAO#getFeedbackById(long)
     */
    @Override
    public Feedback getFeedbackById(final long feedId) {
        final Query query = entityManager.createQuery("select f from Feedback f where f.feedId = ?1");
        query.setParameter(1, feedId);
        @SuppressWarnings("unchecked")
        final List<Feedback> feedbacks = query.getResultList();
        if ((feedbacks == null) || feedbacks.isEmpty()) {
            return null;
        } else {
            return feedbacks.get(0);
        }

    }

    @Override
    public List<Feedback> getAllPublishFeedback(long orgId, String publishFlag) {
        final Query query = entityManager
                .createQuery(
                        "select f from Feedback f where f.orgId.orgid = :orgId and f.activeStatus = :publishFlag and f.isDeleted ='N' order by feedId desc");

        query.setParameter("publishFlag", publishFlag);
        query.setParameter("orgId", orgId);
        @SuppressWarnings("unchecked")
        final List<Feedback> feedbacks = query.getResultList();
        if ((feedbacks == null) || feedbacks.isEmpty()) {
            return Collections.emptyList();
        } else {
            return feedbacks;
        }

    }
}
