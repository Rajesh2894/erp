package com.abm.mainet.cms.ui.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cms.ui.model.AccessibilityModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;

// ckeditor controller write different method
@Controller
@RequestMapping(value = { "/Accessibility.html","/browser.html" })
public class AccessibilityController extends AbstractFormController<AccessibilityModel> {

    private static String ACCESSIBILITY = "accessibility";
    private static String REDIRECT_PATH = "redirect:/Accessibility.html";
    private static String ACCESSIBILITY_PATH = "Accessibility.html";
    private static String ACCESSIBILITY_LANDING_PATH = "AccessibilityLanding.html";
    private static Character FLAG_M = 'M';

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final HttpServletRequest request, final HttpServletResponse response) {

        sessionCleanup(request);

        // check usersession object befor login and after login these parameter is available
        if ((getModel().getContrastscheme() != null) && getModel().getContrastscheme().equalsIgnoreCase("HC")
                && request.getRequestURI().contains(ACCESSIBILITY_PATH)) {
            final Cookie cookie = new Cookie(ACCESSIBILITY, MainetConstants.Common_Constant.YES);
            request.setAttribute(ACCESSIBILITY, MainetConstants.RnLCommon.Y);
            response.addCookie(cookie);
            return new ModelAndView(REDIRECT_PATH);
        }
        if ((getModel().getContrastscheme() != null) && getModel().getContrastscheme().equalsIgnoreCase("SC")
                && request.getRequestURI().contains(ACCESSIBILITY_PATH)) {
            final Cookie cookie = new Cookie(ACCESSIBILITY, MainetConstants.FlagM);
            request.setAttribute(ACCESSIBILITY, FLAG_M);
            response.addCookie(cookie);
            return new ModelAndView(REDIRECT_PATH);
        }
        if ((getModel().getContrastscheme() != null) && getModel().getContrastscheme().equalsIgnoreCase("HC")
                && request.getRequestURI().contains(ACCESSIBILITY_PATH)) {
            final Cookie cookie = new Cookie(ACCESSIBILITY, MainetConstants.Common_Constant.YES);
            request.setAttribute(ACCESSIBILITY, MainetConstants.RnLCommon.Y);
            response.addCookie(cookie);

            return new ModelAndView(REDIRECT_PATH);
        }
        if ((getModel().getContrastscheme() != null) && getModel().getContrastscheme().equalsIgnoreCase("SC")
                && request.getRequestURI().contains(ACCESSIBILITY_PATH)) {
            final Cookie cookie = new Cookie(ACCESSIBILITY, MainetConstants.FlagM);
            request.setAttribute(ACCESSIBILITY, FLAG_M);
            response.addCookie(cookie);
            return new ModelAndView(REDIRECT_PATH);
        }

        if ((getModel().getContrastscheme() != null) && getModel().getContrastscheme().equalsIgnoreCase("HC")
                && request.getRequestURI().contains(ACCESSIBILITY_LANDING_PATH)) {
            final Cookie cookie = new Cookie(ACCESSIBILITY, MainetConstants.Common_Constant.YES);
            request.setAttribute(ACCESSIBILITY, MainetConstants.RnLCommon.Y);
            response.addCookie(cookie);

            return new ModelAndView(REDIRECT_PATH);
        }
        if ((getModel().getContrastscheme() != null) && getModel().getContrastscheme().equalsIgnoreCase("SC")
                && request.getRequestURI().contains(ACCESSIBILITY_LANDING_PATH)) {
            final Cookie cookie = new Cookie(ACCESSIBILITY, MainetConstants.FlagM);
            request.setAttribute(ACCESSIBILITY, FLAG_M);
            response.addCookie(cookie);
            return new ModelAndView(REDIRECT_PATH);
        }
        final Employee employee = UserSession.getCurrent().getEmployee();

        if (request.getRequestURI().contains("Privacypolicylanding.html")) {
            return new ModelAndView("landingPrivacyPolicy", "command", getModel());
        }

        if (request.getRequestURI().contains("Privacypolicy.html")) {
            if ((employee.getLoggedIn() != null) && employee.getLoggedIn().equalsIgnoreCase("N")
                    && (employee.getEmploginname() != null) && employee.getEmploginname()
                            .equalsIgnoreCase(ApplicationSession.getInstance().getMessage("citizen.noUser.loginName"))) {
                return new ModelAndView("PrivacyPolicy", "command", getModel());
            } else {
                return new ModelAndView("PrivacyPolicyb", "command", getModel());
            }
        }

        if (request.getRequestURI().contains("Termsconditionslanding.html")) {
            return new ModelAndView("landingTermsCondition", "command", getModel());
        }

        if (request.getRequestURI().contains("Termsconditions.html")) {
            if ((employee.getLoggedIn() != null) && employee.getLoggedIn().equalsIgnoreCase("N")
                    && (employee.getEmploginname() != null) && employee.getEmploginname()
                            .equalsIgnoreCase(ApplicationSession.getInstance().getMessage("citizen.noUser.loginName"))) {

                return new ModelAndView("TermsCondition", "command", getModel());
            } else {
                return new ModelAndView("TermsConditionb", "command", getModel());
            }
        }

        if (request.getRequestURI().contains("Refundcancellationlanding.html")) {
            return new ModelAndView("landingRefundCancellation", "command", getModel());
        }

        if (request.getRequestURI().contains("Refundcancellation.html")) {
            if ((employee.getLoggedIn() != null) && employee.getLoggedIn().equalsIgnoreCase("N")
                    && (employee.getEmploginname() != null) && employee.getEmploginname()
                            .equalsIgnoreCase(ApplicationSession.getInstance().getMessage("citizen.noUser.loginName"))) {
                return new ModelAndView("RefundCancellation", "command", getModel());
            } else {
                return new ModelAndView("RefundCancellationb", "command", getModel());
            }

        }
        if (request.getRequestURI().contains("AccessiBility.html")) {
            return new ModelAndView("AccessiBility", "command", getModel());

        }
        if (request.getRequestURI().contains("AccessibilityLanding.html")) {
            return new ModelAndView("AccessibilityLanding", "command", getModel());

        }
        if (request.getRequestURI().contains("browser.html")) {
            return new ModelAndView("browser", "command", getModel());
        }

        if (request.getRequestURI().contains("browser.html")) {
            if ((employee.getLoggedIn() != null) && employee.getLoggedIn().equalsIgnoreCase("N")
                    && (employee.getEmploginname() != null) && employee.getEmploginname()
                            .equalsIgnoreCase(ApplicationSession.getInstance().getMessage("citizen.noUser.loginName"))) {
                return new ModelAndView("browser", "command", getModel());
            } else {
                return new ModelAndView("browser", "command", getModel());
            }

        }
        if (request.getRequestURI().contains("ScreenReaderlanding.html")) {
            return new ModelAndView("ScreenReaderlanding", "command", getModel());
        }

        if (request.getRequestURI().contains("ScreenReader.html")) {
            if ((employee.getLoggedIn() != null) && employee.getLoggedIn().equalsIgnoreCase("N")
                    && (employee.getEmploginname() != null) && employee.getEmploginname()
                            .equalsIgnoreCase(ApplicationSession.getInstance().getMessage("citizen.noUser.loginName"))) {
                return new ModelAndView("ScreenReader", "command", getModel());
            } else {
                return new ModelAndView("ScreenReaderb", "command", getModel());
            }
        } else {
            return new ModelAndView("Accessibility", "command", getModel());
        }
      
     
    
    }

}
