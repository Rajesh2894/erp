package com.abm.mainet.cms.ui.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cms.ui.model.CitizenAboutUsModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.UserSession;

// ckeditor controller write different method
@Controller
@RequestMapping(value = { "/Accessibility.html", "/Privacypolicy.html", "/Privacypolicylanding.html", "/Termsconditions.html",
        "/Termsconditionslanding.html", "/Refundcancellation.html", "/Refundcancellationlanding.html", "/AccessiBility.html",
        "/AccessibilityLanding.html", "/ScreenReader.html", "/ScreenReaderlanding.html", "/KeyInitiative.html", "/Content.html",
        "/Websitepolicy.html", "/help.html", "/webInfo.html", "/HyperlinkPolicy.html","/browser.html","/KrutidevToUnicode.html" })
public class AccessibilityController extends AbstractFormController<CitizenAboutUsModel> {

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final HttpServletRequest request, final HttpServletResponse response) {

        sessionCleanup(request);
        bindModel(request);

        if ((getModel().getContrastscheme() != null) && getModel().getContrastscheme().equalsIgnoreCase("HC")
                && request.getRequestURI().contains("Accessibility.html")) {
            final Cookie cookie = new Cookie("accessibility", "Y");
            request.setAttribute("accessibility", 'Y');
            response.addCookie(cookie);

            return new ModelAndView("redirect:/Accessibility.html");
        }
        if ((getModel().getContrastscheme() != null) && getModel().getContrastscheme().equalsIgnoreCase("SC")
                && request.getRequestURI().contains("Accessibility.html")) {
            final Cookie cookie = new Cookie("accessibility", "M");
            request.setAttribute("accessibility", 'M');
            response.addCookie(cookie);
            return new ModelAndView("redirect:/Accessibility.html");
        }
        if ((getModel().getContrastscheme() != null) && getModel().getContrastscheme().equalsIgnoreCase("HC")
                && request.getRequestURI().contains("AccessiBility.html")) {
            final Cookie cookie = new Cookie("accessibility", "Y");
            request.setAttribute("accessibility", 'Y');
            response.addCookie(cookie);

            return new ModelAndView("redirect:/AccessiBility.html");
        }
        if ((getModel().getContrastscheme() != null) && getModel().getContrastscheme().equalsIgnoreCase("SC")
                && request.getRequestURI().contains("AccessiBility.html")) {
            final Cookie cookie = new Cookie("accessibility", "M");
            request.setAttribute("accessibility", 'M');
            response.addCookie(cookie);
            return new ModelAndView("redirect:/AccessiBility.html");
        }

        if ((getModel().getContrastscheme() != null) && getModel().getContrastscheme().equalsIgnoreCase("HC")
                && request.getRequestURI().contains("AccessibilityLanding.html")) {
            final Cookie cookie = new Cookie("accessibility", "Y");
            request.setAttribute("accessibility", 'Y');
            response.addCookie(cookie);

            return new ModelAndView("redirect:/AccessibilityLanding.html");
        }
        if ((getModel().getContrastscheme() != null) && getModel().getContrastscheme().equalsIgnoreCase("SC")
                && request.getRequestURI().contains("AccessibilityLanding.html")) {
            final Cookie cookie = new Cookie("accessibility", "M");
            request.setAttribute("accessibility", 'M');
            response.addCookie(cookie);
            return new ModelAndView("redirect:/AccessibilityLanding.html");
        }
        final Employee employee = UserSession.getCurrent().getEmployee();

        if (request.getRequestURI().contains("Privacypolicylanding.html")) {
            return new ModelAndView("landingPrivacyPolicy", MainetConstants.FORM_NAME, getModel());
        }

        if (request.getRequestURI().contains("Privacypolicy.html")) {
        	if (null != employee && null != employee.getEmploginname() && employee.getEmploginname()
                    .equalsIgnoreCase(ApplicationSession.getInstance().getMessage("citizen.noUser.loginName"))) {
                return new ModelAndView("PrivacyPolicy", MainetConstants.FORM_NAME, getModel());
            } else if (null != employee && null != employee.getLoggedIn()
                    && employee.getLoggedIn().equalsIgnoreCase(MainetConstants.UNAUTH)) {  // SITE MAP FOR UN-AUTHORIZED LOGGED-IN
                                                                                           // USER
                return new ModelAndView("PrivacyPolicy", MainetConstants.FORM_NAME, getModel());
            } else {
                return new ModelAndView("PrivacyPolicyb", MainetConstants.FORM_NAME, getModel());
            }
        }

        if (request.getRequestURI().contains("Termsconditionslanding.html")) {
            return new ModelAndView("landingTermsCondition", MainetConstants.FORM_NAME, getModel());
        }

        if (request.getRequestURI().contains("Termsconditions.html")) {
        	if (null != employee && null != employee.getEmploginname() && employee.getEmploginname()
                    .equalsIgnoreCase(ApplicationSession.getInstance().getMessage("citizen.noUser.loginName"))) {
                return new ModelAndView("TermsCondition", MainetConstants.FORM_NAME, getModel());
            } else if (null != employee && null != employee.getLoggedIn()
                    && employee.getLoggedIn().equalsIgnoreCase(MainetConstants.UNAUTH)) {  // SITE MAP FOR UN-AUTHORIZED LOGGED-IN
                                                                                           // USER
                return new ModelAndView("TermsCondition", MainetConstants.FORM_NAME, getModel());
            } else {
                return new ModelAndView("TermsConditionb", MainetConstants.FORM_NAME, getModel());
            }
        }

        if (request.getRequestURI().contains("Refundcancellationlanding.html")) {
            return new ModelAndView("landingRefundCancellation", MainetConstants.FORM_NAME, getModel());
        }

        if (request.getRequestURI().contains("Refundcancellation.html")) {
        	if (null != employee && null != employee.getEmploginname() && employee.getEmploginname()
                    .equalsIgnoreCase(ApplicationSession.getInstance().getMessage("citizen.noUser.loginName"))) {
                return new ModelAndView("RefundCancellation", MainetConstants.FORM_NAME, getModel());
            } else if (null != employee && null != employee.getLoggedIn()
                    && employee.getLoggedIn().equalsIgnoreCase(MainetConstants.UNAUTH)) {  // SITE MAP FOR UN-AUTHORIZED LOGGED-IN
                                                                                           // USER
                return new ModelAndView("RefundCancellation", MainetConstants.FORM_NAME, getModel());
            }  else {
                return new ModelAndView("RefundCancellationb", MainetConstants.FORM_NAME, getModel());
            }

        }
        if (request.getRequestURI().contains("AccessiBility.html")) {
            return new ModelAndView("AccessiBility", MainetConstants.FORM_NAME, getModel());

        }
        if (request.getRequestURI().contains("AccessibilityLanding.html")) {
            return new ModelAndView("AccessibilityLanding", MainetConstants.FORM_NAME, getModel());

        }

        if (request.getRequestURI().contains("ScreenReaderlanding.html")) {
            return new ModelAndView("ScreenReaderlanding", MainetConstants.FORM_NAME, getModel());
        }
        if (request.getRequestURI().contains("KrutidevToUnicode.html")) {
            return new ModelAndView("KrutidevToUnicode", MainetConstants.FORM_NAME, getModel());
        }
        if (request.getRequestURI().contains("KeyInitiative.html")) {
            return new ModelAndView("KeyInitiativeDetails", MainetConstants.FORM_NAME, getModel());
        }
        if (request.getRequestURI().contains("/Copyright.html")) {
        	if (null != employee && null != employee.getEmploginname() && employee.getEmploginname()
                    .equalsIgnoreCase(ApplicationSession.getInstance().getMessage("citizen.noUser.loginName"))) {
                return new ModelAndView("copyrightSuda", MainetConstants.FORM_NAME, getModel());
            } else if (null != employee && null != employee.getLoggedIn()
                    && employee.getLoggedIn().equalsIgnoreCase(MainetConstants.UNAUTH)) {  // SITE MAP FOR UN-AUTHORIZED LOGGED-IN
                                                                                           // USER
                return new ModelAndView("copyrightSuda", MainetConstants.FORM_NAME, getModel());
            }  else {
                return new ModelAndView("copyrightSudab", MainetConstants.FORM_NAME, getModel());
            }
        }

        if (request.getRequestURI().contains("/Websitepolicy.html")) {
        	if (null != employee && null != employee.getEmploginname() && employee.getEmploginname()
                    .equalsIgnoreCase(ApplicationSession.getInstance().getMessage("citizen.noUser.loginName"))) {
                return new ModelAndView("websitePolicy", MainetConstants.FORM_NAME, getModel());
            } else if (null != employee && null != employee.getLoggedIn()
                    && employee.getLoggedIn().equalsIgnoreCase(MainetConstants.UNAUTH)) {  // SITE MAP FOR UN-AUTHORIZED LOGGED-IN
                                                                                           // USER
                return new ModelAndView("websitePolicy", MainetConstants.FORM_NAME, getModel());
            } else {
                return new ModelAndView("websitePolicyb", MainetConstants.FORM_NAME, getModel());
            }
        }
        
        if (request.getRequestURI().contains("/browser.html")) {
        	if (null != employee && null != employee.getEmploginname() && employee.getEmploginname()
                    .equalsIgnoreCase(ApplicationSession.getInstance().getMessage("citizen.noUser.loginName"))) {
                return new ModelAndView("browser", MainetConstants.FORM_NAME, getModel());
            } else if (null != employee && null != employee.getLoggedIn()
                    && employee.getLoggedIn().equalsIgnoreCase(MainetConstants.UNAUTH)) {  // SITE MAP FOR UN-AUTHORIZED LOGGED-IN
                                                                                           // USER
                return new ModelAndView("browser", MainetConstants.FORM_NAME, getModel());
            } else {
                return new ModelAndView("browser", MainetConstants.FORM_NAME, getModel());
            }
        }
        if (request.getRequestURI().contains("/help.html")) {
        	if (null != employee && null != employee.getEmploginname() && employee.getEmploginname()
                    .equalsIgnoreCase(ApplicationSession.getInstance().getMessage("citizen.noUser.loginName"))) {
                return new ModelAndView("help", MainetConstants.FORM_NAME, getModel());
            } else if (null != employee && null != employee.getLoggedIn()
                    && employee.getLoggedIn().equalsIgnoreCase(MainetConstants.UNAUTH)) {  // SITE MAP FOR UN-AUTHORIZED LOGGED-IN
                                                                                           // USER
                return new ModelAndView("help", MainetConstants.FORM_NAME, getModel());
            }  else {
                return new ModelAndView("helpb", MainetConstants.FORM_NAME, getModel());
            }
        }

        if (request.getRequestURI().contains("/webInfo.html")) {
        	if (null != employee && null != employee.getEmploginname() && employee.getEmploginname()
                    .equalsIgnoreCase(ApplicationSession.getInstance().getMessage("citizen.noUser.loginName"))) {
                return new ModelAndView("webInfo", MainetConstants.FORM_NAME, getModel());
            } else if (null != employee && null != employee.getLoggedIn()
                    && employee.getLoggedIn().equalsIgnoreCase(MainetConstants.UNAUTH)) {  // SITE MAP FOR UN-AUTHORIZED LOGGED-IN
                                                                                           // USER
                return new ModelAndView("webInfo", MainetConstants.FORM_NAME, getModel());
            } else {
                return new ModelAndView("webInfob", MainetConstants.FORM_NAME, getModel());
            }
        }
        if (request.getRequestURI().contains("/HyperlinkPolicy.html")) {
        	if (null != employee && null != employee.getEmploginname() && employee.getEmploginname()
                    .equalsIgnoreCase(ApplicationSession.getInstance().getMessage("citizen.noUser.loginName"))) {
                return new ModelAndView("hyperlinkPolicy", MainetConstants.FORM_NAME, getModel());
            } else if (null != employee && null != employee.getLoggedIn()
                    && employee.getLoggedIn().equalsIgnoreCase(MainetConstants.UNAUTH)) {  // SITE MAP FOR UN-AUTHORIZED LOGGED-IN
                                                                                           // USER
                return new ModelAndView("hyperlinkPolicy", MainetConstants.FORM_NAME, getModel());
            } else {
                return new ModelAndView("hyperlinkPolicyb", MainetConstants.FORM_NAME, getModel());
            }
        }
        
        if (request.getRequestURI().contains("/browser.html")) {
        	if (null != employee && null != employee.getEmploginname() && employee.getEmploginname()
                    .equalsIgnoreCase(ApplicationSession.getInstance().getMessage("citizen.noUser.loginName"))) {
                return new ModelAndView("browser", MainetConstants.FORM_NAME, getModel());
            } else if (null != employee && null != employee.getLoggedIn()
                    && employee.getLoggedIn().equalsIgnoreCase(MainetConstants.UNAUTH)) {  // SITE MAP FOR UN-AUTHORIZED LOGGED-IN
                                                                                           // USER
                return new ModelAndView("browser", MainetConstants.FORM_NAME, getModel());
            } else {
                return new ModelAndView("browser", MainetConstants.FORM_NAME, getModel());
            }
        }

        if (request.getRequestURI().contains("ScreenReader.html")) {
        	if (null != employee && null != employee.getEmploginname() && employee.getEmploginname()
                    .equalsIgnoreCase(ApplicationSession.getInstance().getMessage("citizen.noUser.loginName"))) {
                return new ModelAndView("ScreenReader", MainetConstants.FORM_NAME, getModel());
            } else if (null != employee && null != employee.getLoggedIn()
                    && employee.getLoggedIn().equalsIgnoreCase(MainetConstants.UNAUTH)) {  // SITE MAP FOR UN-AUTHORIZED LOGGED-IN
                                                                                           // USER
                return new ModelAndView("ScreenReader", MainetConstants.FORM_NAME, getModel());
            }  else {
                return new ModelAndView("ScreenReaderb", MainetConstants.FORM_NAME, getModel());
            }
        } else {
            return new ModelAndView("Accessibility", MainetConstants.FORM_NAME, getModel());
        }
    }

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, params = "links")
    public ModelAndView details(@RequestParam String page) {

        ModelAndView mv = new ModelAndView("ReadMore", MainetConstants.FORM_NAME, getModel());
        String OrgName=null;
        		if(UserSession.getCurrent().getLanguageId()==MainetConstants.ENGLISH) {
        			OrgName=UserSession.getCurrent().getOrganisation().getONlsOrgname();	
        		}else {
        			OrgName=UserSession.getCurrent().getOrganisation().getONlsOrgnameMar();	
        		}
        mv.addObject("pageName", OrgName+" - "+page);
        return mv;

    }

}
