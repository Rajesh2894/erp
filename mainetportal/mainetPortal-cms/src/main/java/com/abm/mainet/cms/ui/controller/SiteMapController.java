package com.abm.mainet.cms.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cms.ui.model.SiteMapModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.UserSession;

/**
 *
 * @author deepika.pimpale
 *
 */
@Controller
@RequestMapping("/sitemap.html")
public class SiteMapController extends AbstractFormController<SiteMapModel> {
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(final HttpServletRequest request) {

        sessionCleanup(request);
        final SiteMapModel model = getModel();
        final Employee employee = UserSession.getCurrent().getEmployee();
        // SITE MAP FOR NOT LOGGED-IN USER
        if (null != employee && null != employee.getEmploginname() && employee.getEmploginname()
                .equalsIgnoreCase(ApplicationSession.getInstance().getMessage("citizen.noUser.loginName"))) {
            return new ModelAndView(getViewName(), MainetConstants.FORM_NAME, model);
        } else if (null != employee && null != employee.getLoggedIn()
                && employee.getLoggedIn().equalsIgnoreCase(MainetConstants.UNAUTH)) {  // SITE MAP FOR UN-AUTHORIZED LOGGED-IN
                                                                                       // USER
            return new ModelAndView(getViewName(), MainetConstants.FORM_NAME, model);
        } else {
            return new ModelAndView("SiteMapLogin", MainetConstants.FORM_NAME, getModel()); // SITE MAP FOR LOGGED-IN USER
        }

    }

}
