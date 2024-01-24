/**
 *
 */
package com.abm.mainet.authentication.citizen.ui.controller;

import java.util.Date;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.authentication.citizen.ui.model.CitizenLoginModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.HttpHelper;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;

@Controller
@RequestMapping("/CitizenLogin.html")
public class CitizenLoginController extends AbstractFormController<CitizenLoginModel> {
	private static final Logger LOGGER = LoggerFactory.getLogger(CitizenLoginController.class);
    @Autowired
    private IOrganisationService iOrganisationService;
    @Autowired
    private IEmployeeService iEmployeeService;

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView index(final HttpServletRequest request, final HttpServletResponse response,
            @RequestParam("quickflag") final String quickPayFlag) {

        if (isRestrictClickJacking()) {
            response.addHeader("X-FRAME-OPTIONS", "DENY");
        }
        sessionCleanup(request);

        final UserSession session = UserSession.getCurrent();
        session.setQuickPayFlag("Y");
        session.setUserAgent(request.getHeader("User-Agent"));
        final CitizenLoginModel citizenLoginModel = new CitizenLoginModel();
        String key = UUID.randomUUID().toString();
		String substringKey = key.substring(key.length() - 17, key.length() - 1);
		session.setUniqueKeyId(substringKey);

        return new ModelAndView("CitizenLogin", MainetConstants.FORM_NAME, citizenLoginModel);
    }

    @RequestMapping(params = { "quickflag1", "redirecturl1" }, method = RequestMethod.GET)
    public ModelAndView indexredirecturl(final HttpServletRequest request, final HttpServletResponse response,
            @RequestParam("quickflag1") final String quickPayFlag, @RequestParam("redirecturl1") final String redirecturl) {

        if (isRestrictClickJacking()) {
            response.addHeader("X-FRAME-OPTIONS", "DENY");
        }
        sessionCleanup(request);

        final UserSession session = UserSession.getCurrent();
        if (getModel().getOrgid() != null) {
            session.setOrganisation(iOrganisationService.getOrganisationById(getModel().getOrgid()));
        }
        session.setQuickPayFlag("Y");
        session.setRedirecturl(redirecturl);

        return new ModelAndView("CitizenLogin", MainetConstants.FORM_NAME, getModel());
    }

    @RequestMapping(method = RequestMethod.POST, params = "login")
    public @ResponseBody String authenticatCitizenEmployee(final HttpServletRequest request) {
    	String redirectServiceURL = (String) request.getSession().getAttribute("serviceURL");
        String result = MainetConstants.BLANK;
        final BindingResult bindingResult = bindModel(request);
        final Cookie[] cookies = request.getCookies();

        final CitizenLoginModel model = getModel();
        String cookiesforaccessibility = null;
        for (final Cookie cookie : cookies) {
            if ((cookie.getName() != null) && cookie.getName().equalsIgnoreCase("accessibility")) {
                cookiesforaccessibility = cookie.getValue();
            }
        }

        if (request.getSession().getAttribute("captcha").equals(model.getCaptchaSessionLoginValue())
                || (cookiesforaccessibility != null && cookiesforaccessibility.equalsIgnoreCase(MainetConstants.AUTH))) {

            if (!bindingResult.hasErrors()) {
				/*
				 * if(!Utility.passwordLengthValidationCheck(model.getCitizenEmployee().
				 * getEmppassword()).isEmpty()) { return "longPassDenailOfService"; }
				 */
                Employee loggedEmployee = null;
                try {
                	loggedEmployee = model.validateCitizenLogin();
					 UserSession.getCurrent().setUniqueKeyId("");
				} catch (Exception e) {
					if(e.getMessage().equalsIgnoreCase("Password Wrong")) {
						return "Password Wrong";
					}
					// TODO: handle exception
				}
                		
                if (loggedEmployee != null) {
                    if ((null != loggedEmployee.getLockUnlock())
                            && loggedEmployee.getLockUnlock().equals(MainetConstants.NEC.ADVOCATE)) {
                        return "accountLock";
                    }
                    if (null != loggedEmployee.getEmpexpiredt() && new Date().after(loggedEmployee.getEmpexpiredt())) {
                    	return "passwordExpired";
            		}
                    if (model.isCitizenEmployee(loggedEmployee)) {
                        final boolean isOTPVerifed = model.isOTPVerificationDone(loggedEmployee);

                        if (isOTPVerifed) {
                            final UserSession userSession = UserSession.getCurrent();
                            	
                            final LookUp lookup = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.COI, MainetConstants.ENV,
                    				UserSession.getCurrent().getOrganisation());
                    		if (lookup != null) {
                    			LOGGER.info("Getting OrgId from Child Ulb Prefix is : "+getModel().getOrgid());
                    			userSession.setOrganisation(iOrganisationService.getOrganisationById(Utility.getOrgId()));
                    		}                             
                    		else if (getModel().getOrgid() != null) {
                            	LOGGER.info("Getting OrgId from Model is : "+getModel().getOrgid());
                                userSession.setOrganisation(iOrganisationService.getOrganisationById(getModel().getOrgid()));
                            }
                            else {
                            	LOGGER.info("Getting OrgId from Child Ulb Prefix is : "+getModel().getOrgid());
                            	 userSession.setOrganisation(iOrganisationService.getOrganisationById(Utility.getOrgId()));
                            }
                            loggedEmployee.setEmpisecuritykey(request.getRequestedSessionId());
                            iEmployeeService.setEmployeeLoggedInFlag(loggedEmployee);
                            userSession.setEmployee(loggedEmployee);
                            model.accessListAndMenuForCitizen(loggedEmployee);
                          //User Story #108649
                          try {
                        	 boolean flag= model.saveEmployeeSession();
                        	 if(flag) {
                        		 LOGGER.info(" Successfully save  employee session data at the time of citizen login"); 
                        	 }
                          }
                          catch (Exception e) {
                        	  LOGGER.error("Exception occured at the time of saving employee session data ");
						}
                           
                            if (request.getSession().getAttribute("serviceURL") != null) {
                                result = "redirect:" + ((String) request.getSession().getAttribute("serviceURL")).trim();
                            } else {
                                result = model.redirectSuccess();
                            }

                        } else {
                            model.sendOTPAgain(loggedEmployee);
                            result = model.redirectToOTP();
                            UserSession.getCurrent().setMobileNoToValidate(loggedEmployee.getEmpmobno());
                        }
                    }
                }else {
                	return "User Not Exist";
                }
            }
            return result;
        } else {
            return MainetConstants.CAPTCHA_NOT_MATCHED;
        }

    }

    @RequestMapping(method = RequestMethod.POST, params = "passEncrypt")
    public @ResponseBody String getEncrypted(final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);

        final CitizenLoginModel model = getModel();
        return model.getEncryptData();
    }

    @RequestMapping(method = RequestMethod.POST, params = "org")
    public void setorganisation(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse) {
        bindModel(httpServletRequest);
        final String organisationId = httpServletRequest.getParameter("orgId");
        final int langId = HttpHelper.getSessionLanguageId(httpServletRequest);
        final long orgId = Long.valueOf(organisationId);
        final Organisation organisation = iOrganisationService.getOrganisationById(orgId);
        HttpHelper.updateSessionOrg(httpServletRequest, httpServletResponse, organisation, langId);

    }

    private boolean isRestrictClickJacking() {
        Boolean restrictClickJacking = false;
        final String restrictClickJackingCheck = ApplicationSession.getInstance()
                .getMessage(MainetConstants.SECURITY_FLAG.CLICK_JACKING_CHECK);
        if ((restrictClickJackingCheck != null) && restrictClickJackingCheck.equals("Y")) {
            restrictClickJacking = true;
        }
        return restrictClickJacking;
    }
    
    @RequestMapping(method = RequestMethod.POST, params = "getCaptchaValue")
    public @ResponseBody String getCaptchaValue(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    	return httpServletRequest.getSession().getAttribute("captcha").toString();	
    }
}