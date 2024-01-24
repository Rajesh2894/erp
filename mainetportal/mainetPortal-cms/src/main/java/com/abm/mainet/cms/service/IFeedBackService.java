/**
 *
 */
package com.abm.mainet.cms.service;

import java.util.List;

import com.abm.mainet.cms.domain.Feedback;

/**
 * @author swapnil.shirke
 */
public interface IFeedBackService {
    public List<Feedback> getAllFeedBack();

    public boolean saveFeedback(Feedback feedback);

    public boolean updateFeedback(Feedback feedback);

    public boolean delete(long rowId);

    public Feedback getFeedbackById(long feedId);

    List<Feedback> getAllPublishFeedback(long orgId, String publishFlag);
}
