package com.abm.mainet.cms.ui.model;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cms.domain.Feedback;
import com.abm.mainet.cms.service.IFeedBackService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.ui.model.AbstractEntryFormModel;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;

/**
 * @author swapnil.shirke
 */
@Component
@Scope(value = "session")
public class AdminFeedbackFormModel extends AbstractEntryFormModel<Feedback> implements Serializable {

    private static final long serialVersionUID = -1952078241484775468L;

    @Autowired
    private IFeedBackService iFeedbackService;

    private String feedBackDetails;

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.ui.model.AbstractEntryFormModel#delete(long) Delete record from Grid (Admin Feedback Grid)
     */
    @Override
    public void delete(final long rowId) {
        iFeedbackService.delete(rowId);
    }

    @Override
    public void editForm(final long rowId) {
        setFeedBackDetails(MainetConstants.BLANK);
        Feedback feedBack = iFeedbackService.getFeedbackById(rowId);
        final List<LookUp> innIdeaCategoryLookup = CommonMasterUtility.getLookUps(PrefixConstants.Prefix.IIC,
                UserSession.getCurrent().getOrganisation());
        if(feedBack !=null) {
        	if(feedBack.getCatagoryType() != null &&  innIdeaCategoryLookup!=null) {
      			
        	innIdeaCategoryLookup.forEach(i->{
  				if(feedBack.getCatagoryType().longValue() ==i.getLookUpId()) {
  					feedBack.setCategoryTypeName(i.getLookUpDesc());
  				}
  			});
        	}
        
        }
      
        setEntity(feedBack);

    }

    public String getFeedBackDetails() {
        return feedBackDetails;
    }

    public void setFeedBackDetails(String feedBackDetails) {
        this.feedBackDetails = feedBackDetails;
    }
}
