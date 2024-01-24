package com.abm.mainet.cms.ui.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cms.domain.Feedback;
import com.abm.mainet.cms.service.IFeedBackService;
import com.abm.mainet.cms.ui.validator.CitizenFeedbackFormValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.ui.model.AbstractEntryFormModel;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;

/**
 * @author swapnil.shirke
 */
@Component
@Scope("session")
public class CitizenFeedBackModel extends AbstractEntryFormModel<Feedback> implements Serializable {

    private static final long serialVersionUID = -6676102696709734993L;

    @Autowired
    private IFeedBackService iFeedBackService;

    private Feedback feedback;

    private String mode;

    private String captchaSessionLoginValue;

    @Override
    public boolean saveOrUpdateForm() {
        final Feedback feedback = getFeedback();
        setEntity(feedback);
        validateBean(getEntity(), CitizenFeedbackFormValidator.class);

        if (hasValidationErrors()) {

            return false;
        }

        iFeedBackService.saveFeedback(getEntity());

        return true;

    }

    @Override
    public void delete(final long rowId) {

    }

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
     * @return the mode
     */
    public String getMode() {
        return mode;
    }

    /**
     * @param mode the mode to set
     */
    public void setMode(final String mode) {
        this.mode = mode;
    }

    public String getCaptchaSessionLoginValue() {
        return captchaSessionLoginValue;
    }

    public void setCaptchaSessionLoginValue(String captchaSessionLoginValue) {
        this.captchaSessionLoginValue = captchaSessionLoginValue;
    }

    public Feedback getcheckLoginStatus() {
        final Employee emp = UserSession.getCurrent().getEmployee();
        final Feedback feedback = new Feedback();
        if (null != emp && null != emp.getLoggedIn() && emp.getLoggedIn().equals(MainetConstants.IsDeleted.DELETE)) {
            String fName = MainetConstants.BLANK, lName = MainetConstants.BLANK;
            if (emp.getEmpname() != null) {
                fName = emp.getEmpname();
            }
            if (emp.getEmpLName() != null) {
                lName = emp.getEmpLName();
            }
            feedback.setFdUserName(fName + MainetConstants.WHITE_SPACE + lName);
            feedback.setMobileNo(emp.getEmpmobno() == null ? MainetConstants.BLANK : emp.getEmpmobno());
            feedback.setEmailId(emp.getEmpemail() == null ? MainetConstants.BLANK : emp.getEmpemail());
        }

        return feedback;
    }
    public String getDirectry() {
    	SimpleDateFormat formatter=new SimpleDateFormat(MainetConstants.FORMAT_DDMMMYY);
        String date=formatter.format(new Date());
        final String directoryTree = UserSession.getCurrent().getOrganisation().getOrgid() + MainetConstants.FILE_PATH_SEPARATOR + date
                + MainetConstants.FILE_PATH_SEPARATOR + "EIPFEEDBACK" + MainetConstants.FILE_PATH_SEPARATOR
                + Utility.getTimestamp();
        return directoryTree;
    }
}
