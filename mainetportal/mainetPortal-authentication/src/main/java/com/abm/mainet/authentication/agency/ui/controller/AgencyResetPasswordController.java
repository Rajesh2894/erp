package com.abm.mainet.authentication.agency.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.authentication.agency.ui.model.AgencyResetPasswordModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.util.UserSession;

/**
 *
 * @author vivek.kumar
 *
 */

@Controller
@RequestMapping("/AgencyResetPassword.html")
public class AgencyResetPasswordController extends AbstractFormController<AgencyResetPasswordModel> {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(final HttpServletRequest request) {
        sessionCleanup(request);
        return new ModelAndView("AgencyResetValidateMobile", MainetConstants.FORM_NAME, getModel());
    }

    @RequestMapping(params = "IsRegisteredMobile", method = RequestMethod.POST)
    public @ResponseBody String isRegisteredAgency(final HttpServletRequest request) {
        final BindingResult bindingResult = bindModel(request);
        final AgencyResetPasswordModel model = getModel();
        String result = null;

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
    public ModelAndView getOTPVerficationForm(final HttpServletRequest request) {
        bindModel(request);
        final AgencyResetPasswordModel model = getModel();
        final Employee employee = model.getUserSession().getEmployee();
        if ((null != employee) && !employee.getEmploginname().equalsIgnoreCase("NOUSER")
                && employee.getLoggedIn().equalsIgnoreCase(MainetConstants.AUTH)) {
            model.oneTimePasswordStep2(employee);
            model.getUserSession().setEmplType(employee.getEmplType());
        }
        return new ModelAndView("AgencyOTPResetPassword", MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(params = "ResendOTP", method = RequestMethod.POST)
    public @ResponseBody String resendOTP(final HttpServletRequest request) {

        bindModel(request);
        final AgencyResetPasswordModel model = getModel();
        String result = null;

        final Employee agency = model.getAgencyByMobile();
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
        return new ModelAndView("AgencyResetPassword", MainetConstants.FORM_NAME, getModel());
    }

    @RequestMapping(params = "doResetPassword", method = RequestMethod.POST)
    public @ResponseBody String setPassword(final HttpServletRequest request) {
        bindModel(request);
        final AgencyResetPasswordModel model = getModel();
        String result = null;
        UserSession.getCurrent();

        if ((model.getMobileNumber() != null) && !model.getMobileNumber().equalsIgnoreCase(MainetConstants.BLANK)) {
            final Employee agency = model.getAgencyByMobile();

            if (agency != null) {
                if (model.setPassword(agency)) {
                    result = MainetConstants.MENU.TRUE;
                } else {
                    result = getApplicationSession().getMessage("eip.citizen.resetPassword.failed");
                }
            } else {
                result = getApplicationSession().getMessage("eip.citizen.invalid.user");
            }
        } else {
            result = getApplicationSession().getMessage("eip.citizen.invalid.user");
        }

        return result;
    }

}
