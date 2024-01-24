/**
 *
 */
package com.abm.mainet.cms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.cms.dao.IFeedBackDAO;
import com.abm.mainet.cms.domain.Feedback;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;

/**
 * @author swapnil.shirke
 */
@Service
public class FeedBackService implements IFeedBackService {

    @Autowired
    private IFeedBackDAO ifeedBackDAO;

    @Override
    @Transactional(readOnly = true)
    public List<Feedback> getAllFeedBack() {
        return ifeedBackDAO.getAllFeedBack();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Feedback> getAllPublishFeedback(long orgId, String publishFlag) {
        return ifeedBackDAO.getAllPublishFeedback(orgId, publishFlag);
    }

    @Override
    @Transactional
    public boolean saveFeedback(final Feedback feedback) {
        feedback.updateAuditFields();
        feedback.setIsDeleted(MainetConstants.IsDeleted.NOT_DELETE);

        return ifeedBackDAO.saveOrUpdate(feedback,UserSession.getCurrent().getOrganisation());

    }

    @Override
    @Transactional(readOnly = true)
    public Feedback getFeedbackById(final long feedId) {
    	Feedback feedbacks = ifeedBackDAO.getFeedbackById(feedId);
      //#121808
    	 if(feedbacks != null && feedbacks.getAttPath()!=null && feedbacks.getAttPath()!=""){
    		 feedbacks.setAttPath(Utility.getImageDetails(feedbacks.getAttPath()));
	        }
        return feedbacks;
    }

    @Override
    @Transactional
    public boolean delete(final long rowId) {
        final Feedback feedback = getFeedbackById(rowId);
        feedback.setIsDeleted(MainetConstants.IsDeleted.DELETE);
        return ifeedBackDAO.saveOrUpdate(feedback,UserSession.getCurrent().getOrganisation());
    }

    @Override
    @Transactional
    public boolean updateFeedback(Feedback feedback) {
        Feedback entity = ifeedBackDAO.findById(feedback.getFeedId());
        entity.updateAuditFields();
        entity.setActiveStatus(feedback.getActiveStatus());
        entity.setFeedBackAnswar(feedback.getFeedBackAnswar());
        return ifeedBackDAO.update(entity);
    }

}
