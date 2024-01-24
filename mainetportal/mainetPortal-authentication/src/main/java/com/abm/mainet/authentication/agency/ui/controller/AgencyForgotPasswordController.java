package com.abm.mainet.authentication.agency.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.authentication.agency.ui.model.AgencyForgotPasswordModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
//import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.util.UserSession;

/**
 *
 * @author vivek.kumar
 *
 */

@Controller
@RequestMapping("/AgencyForgotPassword.html")
public class AgencyForgotPasswordController extends AbstractFormController<AgencyForgotPasswordModel> {

    private static final String CITIZEN_RESET_PASSWORD_FAILED = "eip.citizen.resetPassword.failed";

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(final HttpServletRequest request) {
        sessionCleanup(request);
        return new ModelAndView(getViewName(), MainetConstants.FORM_NAME, getModel());
    }

    @RequestMapping(params = "IsRegisteredMobile", method = RequestMethod.POST)
    public @ResponseBody String isRegisteredCitizen(final HttpServletRequest request,
            @RequestParam("checkOTPTypeValue") final String type) {
        final BindingResult bindingResult = bindModel(request);
        final AgencyForgotPasswordModel model = getModel();
        model.setMobileNumberType(type);
        String result = null;
        UserSession.getCurrent();
        if (!bindingResult.hasErrors()) {
            if ((model.getMobileNumber() != null) && !model.getMobileNumber().equalsIgnoreCase(MainetConstants.BLANK)) {
                if (!model.ifRegisteredAgencyThenSendOTP(model.getMobileNumber())) {
                    if (model.isAccountLock()) {
                        result = getApplicationSession().getMessage("citizen.reset.pass.account.lock");
                    } else {
                        result = getApplicationSession().getMessage("eip.citizen.forgotPassword.isRegisteredMobile");
                    }
                } else {
                    result = MainetConstants.SUCCESS;
                    UserSession.getCurrent().setEmplType(model.getAgencyEmployee().getEmplType());
                }
            } else {
                result = getApplicationSession().getMessage("eip.citizen.forgotPassword.mobileNoMandatory");
            }
        } else {

            for (final Object error : bindingResult.getAllErrors()) {
                if (error instanceof FieldError) {

                }
                if (error instanceof ObjectError) {

                }
            }
        }

        return result;
    }

    @RequestMapping(params = "OTPVerficationFrm", method = RequestMethod.POST)
    public ModelAndView getOTPVerficationForm(final HttpServletRequest request,
            @RequestParam("checkOTPTypeValue") final String type) {
        bindModel(request);
        getModel().setMobileNumberType(type);

        return new ModelAndView("AgencyOTPForgotPassword", MainetConstants.FORM_NAME, getModel());
    }

    @RequestMapping(params = "ResendOTP", method = RequestMethod.POST)
    public @ResponseBody String resendOTP(final HttpServletRequest request) {

        bindModel(request);
        final AgencyForgotPasswordModel model = getModel();
        String result = null;
        Employee agency = null;
        if (MainetConstants.MOBILE_NUMBER_IDENTIFICATION.REGISTERED_NUMBER.equals(model.getMobileNumberType())) {
            agency = model.getAgencyByMobile();
        } else {
            agency = UserSession.getCurrent().getEmployee();
        }

        if (agency != null) {
            final boolean isValidPeriod = model.oneTimePasswordStep2(agency);

            if (isValidPeriod) {
                result = MainetConstants.MENU.TRUE;
            } else {
                result = getApplicationSession().getMessage("eip.citizen.otp.send.failed");
            }
        } else {
            result = getApplicationSession().getMessage("Employee.mobileNumNotReg");
        }
        return result;
    }

    @RequestMapping(params = "verifyOTP", method = RequestMethod.POST)
    public @ResponseBody String verficationOfOTP(final HttpServletRequest request) {
        String result = MainetConstants.BLANK;
        bindModel(request);
        result = getModel().verficationOfOTP();
        return result;
    }

    @RequestMapping(params = "ResetPasswordFrm", method = RequestMethod.POST)
    public ModelAndView getResetPasswordFrm(final HttpServletRequest request) {
        bindModel(request);
        return new ModelAndView("AgencySetForgotPassword", MainetConstants.FORM_NAME, getModel());
    }

    @RequestMapping(params = "doResetPassword", method = RequestMethod.POST)
    public @ResponseBody String setPassword(final HttpServletRequest request) {
        bindModel(request);
        final AgencyForgotPasswordModel model = getModel();
        String result = null;

        //try {
            final Employee agency = model.getAgencyByMobile();
            if (agency != null) {
                if (model.setPassword(agency)) {
                    result = MainetConstants.MENU.TRUE;
                } else {
                    result = getApplicationSession().getMessage(CITIZEN_RESET_PASSWORD_FAILED);
                }
            } else {
                result = getApplicationSession().getMessage(CITIZEN_RESET_PASSWORD_FAILED);
            }
			/*
			 * } catch (final Exception exception) { throw new
			 * FrameworkException("Error getting while calling AgencyForgotPasswordController.setPassword()"
			 * , exception);
			 * 
			 * }
			 */

        return result;
    }

    @RequestMapping(params = "changeMobileNumber", method = RequestMethod.POST)
    public @ResponseBody String changeMobileNumber(final HttpServletRequest request,
            @RequestParam("checkOTPTypeValue") final String type) {
        final BindingResult bindingResult = bindModel(request);
        final AgencyForgotPasswordModel model = getModel();
        model.setMobileNumberType(type);

        String result = null;
        final Employee emp = UserSession.getCurrent().getEmployee();
        if (emp.getEmpGender().length() > 1) {
            emp.setEmpGender(model.getNonHierarchicalLookUpObject(new Long(emp.getEmpGender())).getLookUpCode());
        }
        if (!bindingResult.hasErrors()) {
            if ((model.getMobileNumber() != null) && !model.getMobileNumber().equalsIgnoreCase(MainetConstants.BLANK)) {
                if (!model.isUniqueMobileNumber(model.getMobileNumber())) {

                    result = getApplicationSession().getMessage("Employee.uniqueMobileNum");
                } else {
                    if (!model.oneTimePasswordStep2(emp)) {
                        result = getApplicationSession().getMessage("eip.citizen.forgotPassword.isRegisteredMobile");
                    } else {
                        result = MainetConstants.SUCCESS;
                    }
                }
            } else {
                result = getApplicationSession().getMessage("eip.citizen.forgotPassword.mobileNoMandatory");
            }
        } else {

            for (final Object error : bindingResult.getAllErrors()) {
                if (error instanceof FieldError) {

                }
                if (error instanceof ObjectError) {

                }
            }
        }

        return result;
    }

    @RequestMapping(params = "updateMobileNumber", method = RequestMethod.POST)
    public @ResponseBody String setMobileNumber(final HttpServletRequest request) {

        bindModel(request);
        final AgencyForgotPasswordModel model = getModel();
        String result = MainetConstants.BLANK;

        //try {
            if (model.setMobileNumber()) {
                result = MainetConstants.MENU.TRUE;
            } else {
                result = getApplicationSession().getMessage(CITIZEN_RESET_PASSWORD_FAILED);
            }

        /*} catch (final Exception exception) {
            throw new FrameworkException("Erroe getting while calling AgencyForgotPasswordController.setMobileNumber()",
                    exception);
        }*/

        return result;
    }

}
