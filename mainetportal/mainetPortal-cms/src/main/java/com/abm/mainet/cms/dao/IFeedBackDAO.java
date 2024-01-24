package com.abm.mainet.cms.dao;

import java.util.List;

import com.abm.mainet.cms.domain.Feedback;
import com.abm.mainet.common.dao.IAbstractDAO;
import com.abm.mainet.common.domain.Organisation;

public interface IFeedBackDAO extends IAbstractDAO<Feedback> {

    /**
     * For Saving Entity
     * @param feedback
     * @return
     */
    public abstract boolean saveOrUpdate(Feedback feedback,Organisation org);

    public abstract List<Feedback> getAllFeedBack();

    public abstract Feedback getFeedbackById(long feedId);

    boolean update(Feedback feedback);

    List<Feedback> getAllPublishFeedback(long orgId, String publishFlag);

}