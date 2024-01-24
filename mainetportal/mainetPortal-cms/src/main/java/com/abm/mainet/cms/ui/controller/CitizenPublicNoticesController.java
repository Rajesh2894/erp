package com.abm.mainet.cms.ui.controller;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cms.ui.model.CitizenPublicNoticesModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.ui.controller.AbstractEntryFormController;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.UserSession;

/**
 * @author swapnil.shirke
 *
 */
@Controller
@RequestMapping("/CitizenPublicNotices.html")
public class CitizenPublicNoticesController extends AbstractEntryFormController<CitizenPublicNoticesModel>
        implements Serializable {

    private static final long serialVersionUID = -7676855812623261753L;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        getModel().getAllNotices();
        final Employee employee = UserSession.getCurrent().getEmployee();
        if (null != employee && null != employee.getLoggedIn()
                && employee.getLoggedIn().equalsIgnoreCase(MainetConstants.Common_Constant.NO) && employee.getEmploginname()
                        .equalsIgnoreCase(ApplicationSession.getInstance().getMessage("citizen.noUser.loginName"))) {
            return new ModelAndView(getViewName(), MainetConstants.FORM_NAME, getModel());
        } else {
            return new ModelAndView("CitizenPublicNoticesLogin", MainetConstants.FORM_NAME, getModel());
        }
    }

}
