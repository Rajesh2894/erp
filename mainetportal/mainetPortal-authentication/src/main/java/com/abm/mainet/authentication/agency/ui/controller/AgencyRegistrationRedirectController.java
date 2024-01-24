package com.abm.mainet.authentication.agency.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.authentication.agency.ui.model.AgencyRegistrationRedirectModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.util.UserSession;

@Controller
@RequestMapping("/AgencyRegistrationRedirect.html")
public class AgencyRegistrationRedirectController extends AbstractFormController<AgencyRegistrationRedirectModel> {

    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        bindModel(httpServletRequest);
        UserSession.getCurrent().getEmployee().getEmpId();

        ModelAndView mv = null;
        mv = new ModelAndView("AgencyRegistrationRedirect", MainetConstants.FORM_NAME, getModel());
        mv.addObject(MainetConstants.FORM_NAME, getModel());
        return mv;
    }

    @RequestMapping(method = RequestMethod.POST, params = "getCheckListAndCharges")
    public ModelAndView doGetApplicableCheckListAndCharges(final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
        final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        ModelAndView modelAndView = null;
        final AgencyRegistrationRedirectModel model = getModel();
        model.findApplicableCheckListAndCharges(getModel().getServiceId(), orgId);
        modelAndView = new ModelAndView("AgencyRegistrationRedirectValidn", MainetConstants.FORM_NAME, model);
        if (model.getBindingResult() != null) {
            modelAndView.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
        }
        return modelAndView;
    }

}
