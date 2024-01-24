package com.abm.mainet.cms.ui.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cms.domain.Feedback;
import com.abm.mainet.cms.service.IFeedBackService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.util.Utility;

/**
 * @author swapnil.shirke
 */
@Component
@Scope(value = "session")
public class AdminFeedbackModel extends AbstractFormModel implements Serializable {

    private static final long serialVersionUID = -9005109526318205729L;

    private static final Logger LOG = Logger.getLogger(AdminFeedbackModel.class);
    
    private Feedback feedback;

    private List<Feedback> feedbackList;

    @Autowired
    private IFeedBackService iFeedbackService;

    /**
     * @return the feedback
     */
    public Feedback getFeedback() {
        return feedback;
    }

    /**
     * @param feedback the feedback to set
     */
    public void setFeedback(final Feedback feedback) {
        this.feedback = feedback;
    }

    /**
     * @return the feedbackList
     */
    public List<Feedback> getFeedbackList() {
        return feedbackList;
    }

    /**
     * @param feedbackList the feedbackList to set
     */
    public void setFeedbackList(final List<Feedback> feedbackList) {
        this.feedbackList = feedbackList;
    }

    /**
     * @return List objects of entity {@link: Feedback}
     */
    public List<Feedback> generateFeedbackList() {
    	List<Feedback> list = iFeedbackService.getAllFeedBack();
    	List<Feedback> responselist = new ArrayList<Feedback>();
    	for (int i = 0; i < list.size(); i++) {
    		Feedback feedback = list.get(i);
    		if(feedback.getAttPath() != null && feedback.getAttPath() != "") {
    			feedback.setAttPath(Utility.getImageDetails(feedback.getAttPath()));
    		}
    		responselist.add(feedback);
		}
    	LOG.info("FeedBack List Size Inside AdminFeedbackModel::generateFeedbackList--> "+responselist.size());
        return responselist;
    }

}
