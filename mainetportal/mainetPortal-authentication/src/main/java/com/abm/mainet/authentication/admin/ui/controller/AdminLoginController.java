package com.abm.mainet.authentication.admin.ui.controller;

import java.util.Date;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.authentication.admin.ui.model.AdminLoginModel;
import com.abm.mainet.authentication.citizen.ui.model.CitizenLoginModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.IEntitlementService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;

@Controller
@RequestMapping("/AdminLogin.html")
public class AdminLoginController extends
        AbstractFormController<AdminLoginModel> {
	
	private static final Logger LOGGER = Logger.getLogger(AdminLoginController.class);

    @Autowired
    private IEmployeeService iEmployeeService;
    
    @Autowired
    private IOrganisationService iOrganisationService;

    @Autowired
    private IEntitlementService iEntitlementService;
    
    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView index(final HttpServletRequest request,
            final HttpServletResponse response) {
        if (isRestrictClickJacking()) {
            response.addHeader("X-FRAME-OPTIONS", MainetConstants.DENY);
        }
        sessionCleanup(request);
        UserSession.getCurrent().setUserAgent(request.getHeader("User-Agent"));
        final AdminLoginModel adminLoginModel = getModel();
        String key = UUID.randomUUID().toString();
		String substringKey = key.substring(key.length() - 17, key.length() - 1);
		UserSession.getCurrent().setUniqueKeyId(substringKey);

        return new ModelAndView("AdminLogin", MainetConstants.FORM_NAME, adminLoginModel);
    }

    @RequestMapping(method = RequestMethod.POST, params = "login")
    public @ResponseBody String authenticatAdminEmployee(final HttpServletRequest request) {
        final BindingResult bindingResult = bindModel(request);
        final Cookie[] cookies = request.getCookies();
        String result = null;
        final AdminLoginModel model = getModel();
        final Employee adminEmployee = model.getAdminEmployee();
        String cookiesforaccessibility = null;
        for (final Cookie cookie : cookies) {
            if ((cookie.getName() != null) && cookie.getName().equalsIgnoreCase("accessibility")) {
                cookiesforaccessibility = cookie.getValue();
            }
        }

        if (request.getSession().getAttribute("captcha").equals(model.getCaptchaSessionLoginValue())
                || (cookiesforaccessibility!=null && cookiesforaccessibility.equalsIgnoreCase(MainetConstants.AUTH))) {

        if (!bindingResult.hasErrors()) {
        	
				/*
				 * if(!Utility.passwordLengthValidationCheck(adminEmployee.getEmppassword()).
				 * isEmpty()) { return "longPassDenailOfService"; }
				 */

                final Employee loggedEmployee = model.validateEmployee(
                        adminEmployee.getEmploginname(),
                        adminEmployee.getEmppassword());
				 UserSession.getCurrent().setUniqueKeyId("");
                final Organisation organisation = UserSession.getCurrent()
                        .getOrganisation();
                if (loggedEmployee != null) {
                    if (organisation == null) {
                        UserSession.getCurrent().setOrganisation(
                                loggedEmployee.getOrganisation());
                    }
                    if ((null != loggedEmployee.getLockUnlock())
                            && loggedEmployee.getLockUnlock().equals(MainetConstants.NEC.ADVOCATE)) {
                        return "accountLock";
                    }
                    if (null != loggedEmployee.getEmpexpiredt() && new Date().after(loggedEmployee.getEmpexpiredt())) {
                    	return "passwordExpired";
            		}
                  
                    if((null !=loggedEmployee.getLoggedIn()) && loggedEmployee.getLoggedIn().equals(MainetConstants.Common_Constant.FREE)) {
                    	return "FirstLogin";
                    }
                    if (!model.isCitizenEmployee(loggedEmployee)
                            && !model.isAgencyEmployee(loggedEmployee)) {
                        final boolean isOTPVerifed = model
                                .isOTPVerificationDone(loggedEmployee);

                        if (isOTPVerifed) {

                            loggedEmployee.setEmppiservername(Utility
                                    .getClientIpAddress(request));
                            loggedEmployee.setEmpisecuritykey(request
                                    .getRequestedSessionId());
                            model.setUserSession(loggedEmployee);
                            iEmployeeService
                                    .setEmployeeLoggedInFlag(loggedEmployee);
                            model.accessListAndMenuForAdmin(loggedEmployee);
                            String groupCode = iEntitlementService.getGroupCodeById(UserSession.getCurrent().getEmployee().getGmid(),
                                    UserSession.getCurrent().getOrganisation().getOrgid());
                            
                            logger.info("Organisation Id before updating is "+UserSession.getCurrent().getOrganisation().getOrgid());
                            logger.info("GroupCode is "+groupCode);
                            /////
                            final UserSession session = UserSession.getCurrent();
            				if (loggedEmployee != null) {        					
            					if (StringUtils.isNotEmpty(groupCode) && MainetConstants.MENU.AGENCY.equalsIgnoreCase(groupCode)) {
            						LookUp lookup = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.COI,
            								MainetConstants.ENV, UserSession.getCurrent().getOrganisation());
            						if (lookup != null) {
            							session.setOrganisation(iOrganisationService.getOrganisationById(Utility.getOrgId()));
            							logger.info("Organisation Id after updating is "+session.getOrganisation().getOrgid());
            						}
            					}
            				}
            				/////
                           
                            if (UserSession.getCurrent().getOrganisation()!=null && UserSession.getCurrent().getOrganisation().getDefaultStatus()!=null && !UserSession.getCurrent().getOrganisation().getDefaultStatus().isEmpty()){
                            	if(UserSession.getCurrent().getOrganisation().getDefaultStatus().equalsIgnoreCase("Y") && groupCode.equals(MainetConstants.HELPDESK_GROUP)) {
                                getModel().setShowDashboard("N");
                                return "redirect:HelpDesk.html?helpDesk";
                            } 
                            }
                            result = model.redirectTo(loggedEmployee);

                        } else {
                            UserSession.getCurrent().setMobileNoToValidate(loggedEmployee.getEmpmobno());
                            result = "AdminOTPVerification.html";
                        }
                    } else {
                        return MainetConstants.BLANK;
                    }

                } else {
                    return MainetConstants.BLANK;
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
        final AdminLoginModel model = getModel();
        return model.getEncryptData();
    }

    public boolean isRestrictClickJacking() {
        Boolean restrictClickJacking = false;
        final String restrictClickJackingCheck = ApplicationSession.getInstance()
                .getMessage(MainetConstants.SECURITY_FLAG.CLICK_JACKING_CHECK);
        if ((restrictClickJackingCheck != null)
                && restrictClickJackingCheck.equals(MainetConstants.YES)) {
            restrictClickJacking = true;
        }
        return restrictClickJacking;
    }
    
    @RequestMapping(method = RequestMethod.POST, params = "getCaptchaValue")
    public @ResponseBody String getCaptchaValue(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    	return httpServletRequest.getSession().getAttribute("captcha").toString();	
    }
}
