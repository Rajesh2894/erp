package com.abm.mainet.authentication.agency.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.authentication.agency.ui.model.AgencyDOCVerificationModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractEntryFormController;
import com.abm.mainet.common.util.UserSession;

/**
 *
 * @author vivek.kumar
 *
 */

@Controller
@RequestMapping("/AgencyDOCVerification.html")
public class AgencyDOCVerificationController extends
        AbstractEntryFormController<AgencyDOCVerificationModel> {


    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(final HttpServletRequest request) {

        sessionCleanup(request);

        getModel().setEntity(UserSession.getCurrent().getEmployee());
        getModel().getEntity().getCfcAttachments().clear();

        final String loggedInUserType = getModel().getLoggedInUserType();

        if (MainetConstants.NEC.ARCHITECT.equals(loggedInUserType)
                || MainetConstants.NEC.ENGINEER.equals(loggedInUserType)
                || MainetConstants.NEC.SUPERVISOR.equals(loggedInUserType)
                || MainetConstants.NEC.TOWN_PLANNER.equals(loggedInUserType)
                || MainetConstants.NEC.STRUCTURAL_ENGINEER
                        .equals(loggedInUserType)
                || MainetConstants.NEC.BUILDER.equals(loggedInUserType)) {
            return new ModelAndView("redirect:AgencyRegistrationRedirect.html");
        }

        return super.index();
    }

}
