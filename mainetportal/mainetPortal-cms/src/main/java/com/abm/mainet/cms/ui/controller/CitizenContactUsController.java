
package com.abm.mainet.cms.ui.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cms.service.IEIPContactUsService;
import com.abm.mainet.cms.ui.model.CitizenContactUsModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.ui.controller.AbstractEntryFormController;
import com.abm.mainet.common.ui.view.JsonViewObject;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.dms.service.FileUploadServiceValidator;

@Controller
@RequestMapping("/CitizenContactUs.html")
public class CitizenContactUsController extends AbstractEntryFormController<CitizenContactUsModel> {

    @Autowired
    IEIPContactUsService eipContactUsService;

    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest request) {
        FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
        sessionCleanup(request);
        getModel().getContactUsListBy();
        getModel().getContactUsorg();
        getModel().getcheckLoginStatus();
        getModel().getContactUsOptionList();
        final Employee employee = UserSession.getCurrent().getEmployee();

        if (null != employee && null != employee.getEmploginname() && employee.getEmploginname()
                .equalsIgnoreCase(ApplicationSession.getInstance().getMessage("citizen.noUser.loginName"))) {
            return new ModelAndView(getViewName(), MainetConstants.FORM_NAME, getModel());
        } else if (null != employee && null != employee.getLoggedIn()
                && employee.getLoggedIn().equalsIgnoreCase(MainetConstants.UNAUTH)) {  // SITE MAP FOR UN-AUTHORIZED LOGGED-IN
                                                                                       // USER
            return new ModelAndView(getViewName(), MainetConstants.FORM_NAME, getModel());
        } else {
            return new ModelAndView("CitizenContactUsLogin", MainetConstants.FORM_NAME, getModel()); // SITE MAP FOR LOGGED-IN
                                                                                                     // USER
        }
    }

    @RequestMapping(params = "showPage", method = RequestMethod.GET)
    public ModelAndView employeeInfo(@RequestParam("name") final String name) {

        String viewPage = "EIPguidelines";
        if (name.equalsIgnoreCase("policy")) {
            viewPage = "PrivacyPolicy";
        } else if (name.equalsIgnoreCase("refund")) {
            viewPage = "RefundCancellation";
        } else if (name.equalsIgnoreCase("terms")) {
            viewPage = "TermsCondition";
        } else if (name.equalsIgnoreCase("guideLineForQuick")) {
            viewPage = "EIPguidelines";
        } else if (name.equalsIgnoreCase("guideLineForCitizen")) {
            viewPage = "Citizenguidelines";
        }
        return new ModelAndView(viewPage);

    }

    @Override
    @RequestMapping(params = "saveform", method = RequestMethod.POST)
    public ModelAndView saveform(final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);

        final CitizenContactUsModel model = getModel();
        
       // model.validateBean(model, CitizenContactUsValidator.class);
        String cookiesforaccessibility = null;
      
        final Cookie[] cookies = httpServletRequest.getCookies();

        for (final Cookie cookie : cookies) {
            if ((cookie.getName() != null) && cookie.getName().equalsIgnoreCase("accessibility")) {
                cookiesforaccessibility = cookie.getValue();
            }
        }

      
        	if (httpServletRequest.getSession().getAttribute("captcha") !=null && model.getCaptchaSessionLoginValue() !=null) {
	        if (httpServletRequest.getSession().getAttribute("captcha").equals(model.getCaptchaSessionLoginValue())
	                || (cookiesforaccessibility != null && cookiesforaccessibility.equalsIgnoreCase(MainetConstants.AUTH))) {
	
	        } else {
	
	             model.addValidationError(ApplicationSession.getInstance()
	                            .getMessage("citizen.login.reg.captha.valid.error"));
	        }
        }
        if (model.saveForm()) {
            return jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));
        }

        return defaultMyResult();
    }

    
    
}
