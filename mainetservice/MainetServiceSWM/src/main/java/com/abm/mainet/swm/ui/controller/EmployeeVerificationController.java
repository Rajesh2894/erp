package com.abm.mainet.swm.ui.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.swm.ui.model.EmployeeVerificationModel;

@Controller
@RequestMapping("/EmployeeInspection.html")
public class EmployeeVerificationController extends AbstractFormController<EmployeeVerificationModel> {
    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        return index();
    }

    @ResponseBody
    @RequestMapping(params = "Add", method = RequestMethod.POST)
    public ModelAndView addEmployeeVerification(final HttpServletRequest request) {
        return new ModelAndView("addEmployeeVerification", MainetConstants.FORM_NAME, this.getModel());
    }

    @ResponseBody
    @RequestMapping(params = "edit", method = RequestMethod.POST)
    public ModelAndView editEmployeeVerification(final HttpServletRequest request) {
        sessionCleanup(request);
        return new ModelAndView("edit/Form", MainetConstants.FORM_NAME, this.getModel());

    }

    @ResponseBody
    @RequestMapping(params = "view", method = RequestMethod.POST)
    public ModelAndView viewEmployeeVerification(final HttpServletRequest request) {
        return new ModelAndView("view/Form", MainetConstants.FORM_NAME, this.getModel());
    }

    @ResponseBody
    @RequestMapping(params = "search", method = RequestMethod.POST)
    public ModelAndView searchEmployeeVerification(final HttpServletRequest request) {
        sessionCleanup(request);
        return new ModelAndView("EmployeeVerificationSummary", MainetConstants.FORM_NAME, this.getModel());

    }

    @ResponseBody
    @RequestMapping(params = "delete", method = RequestMethod.POST)
    public ModelAndView deleteEmployeeVerification(final HttpServletRequest request) {
        return new ModelAndView("EmployeeVerificationSummary", MainetConstants.FORM_NAME, this.getModel());

    }

}
