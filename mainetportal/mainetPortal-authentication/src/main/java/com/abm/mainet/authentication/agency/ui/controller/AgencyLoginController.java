package com.abm.mainet.authentication.agency.ui.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.authentication.agency.ui.model.AgencyLoginModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.dto.TPAgencyReqDTO;
import com.abm.mainet.common.dto.TPAgencyResDTO;
//import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.PasswordManager;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.integration.ws.JersyCall;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/AgencyLogin.html")
public class AgencyLoginController extends AbstractFormController<AgencyLoginModel> implements Serializable {

    private static final long serialVersionUID = 5680500108856663122L;

    @Autowired
    private IEmployeeService iEmployeeService;

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView index(final HttpServletRequest request, final HttpServletResponse response) {
        if (isRestrictClickJacking()) {
            response.addHeader("X-FRAME-OPTIONS", MainetConstants.DENY);
        }
        sessionCleanup(request);

        return new ModelAndView("AgencyLogin", MainetConstants.FORM_NAME, new AgencyLoginModel());
    }

    @RequestMapping(method = RequestMethod.POST, params = "login")
    public @ResponseBody String authenticatAgencyEmployee(final HttpServletRequest request) throws JsonParseException, JsonMappingException, IOException {
        String result = MainetConstants.BLANK;
        final BindingResult bindingResult = bindModel(request);
        request.getSession(false);

        final AgencyLoginModel model = getModel();
        final Employee loggedEmployee = model.validateAgency();
        if (request.getSession().getAttribute("captcha").equals(model.getCaptchaSessionLoginValue())) {

            if (!bindingResult.hasErrors()) {
                if (loggedEmployee != null) {
                    if ((null != loggedEmployee.getLockUnlock())
                            && loggedEmployee.getLockUnlock().equals(MainetConstants.FlagL)) {
                        return "accountLock";
                    }
                    if(PasswordManager.isPasswordExpired(loggedEmployee)){
    					return "passwordExpired";
    				}
                    final UserSession userSession = UserSession.getCurrent();
                    if (userSession.getOrganisation() == null) {
                        userSession.setOrganisation(loggedEmployee.getOrganisation());
                    }

                    final boolean isOTPVerifed = model.isOTPVerificationDone(loggedEmployee);

                    if (isOTPVerifed) {
                        model.setUserSession(loggedEmployee);
                        loggedEmployee.setEmppiservername(Utility.getClientIpAddress(request));
                        loggedEmployee.setEmpisecuritykey(request.getRequestedSessionId());

                        final TPAgencyReqDTO authStatusReqDto = new TPAgencyReqDTO();
                        authStatusReqDto.setEmpId(loggedEmployee.getEmpId());
                        authStatusReqDto.setOrgId(userSession.getOrganisation().getOrgid());
                        //try {
                            @SuppressWarnings("unchecked")
                            final LinkedHashMap<Long, Object> authStatusResDto = (LinkedHashMap<Long, Object>) JersyCall
                                    .callRestTemplateClient(authStatusReqDto, ServiceEndpoints.JercyCallURL.AGENCY_AUTH_STATUS);
                            final String authStatusJsonString = new JSONObject(authStatusResDto).toString();

                            final TPAgencyResDTO responseDto = new ObjectMapper().readValue(authStatusJsonString,
                                    TPAgencyResDTO.class);

                            if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(responseDto.getStatus())) {

                                loggedEmployee.setAuthStatus(responseDto.getAuthStatus());
                                loggedEmployee.setIsUploaded(MainetConstants.IsUploaded.UPLOADED);

                            }

                        /*} catch (final Exception ex) {
                            throw new FrameworkException(
                                    "Error Occurred while making Jersy call:AgencyLoginController.authenticatAgencyEmployee ",
                                    ex);
                        }*/

                        iEmployeeService.setAgencyEmployeeLoggedInFlag(loggedEmployee);
                        userSession.setEmployee(loggedEmployee);
                        result = model.redirectSuccess();
                    } else {
                        model.sendOTPAgain(loggedEmployee);
                        result = model.redirectToOTP();
                        userSession.setMobileNoToValidate(loggedEmployee.getEmpmobno());
                        userSession.setEmplType(loggedEmployee.getEmplType());
                    }
                } else {
                    result = getApplicationSession().getMessage("agency.login.authenticationfailed");
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
        final AgencyLoginModel model = getModel();
        return model.getEncryptData();
    }

    private boolean isRestrictClickJacking() {
        Boolean restrictClickJacking = false;
        final String restrictClickJackingCheck = ApplicationSession.getInstance()
                .getMessage(MainetConstants.SECURITY_FLAG.CLICK_JACKING_CHECK);
        if ((restrictClickJackingCheck != null) && restrictClickJackingCheck.equals(MainetConstants.AUTH)) {
            restrictClickJacking = true;
        }
        return restrictClickJacking;
    }
}