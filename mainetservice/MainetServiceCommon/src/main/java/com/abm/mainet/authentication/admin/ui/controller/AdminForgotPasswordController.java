package com.abm.mainet.authentication.admin.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.authentication.admin.ui.model.AdminForgotPasswordModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.ui.controller.AbstractFormController;

@Controller
@RequestMapping("/AdminForgotPassword.html")
public class AdminForgotPasswordController extends AbstractFormController<AdminForgotPasswordModel> {
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(final HttpServletRequest request) {
        sessionCleanup(request);
        return new ModelAndView(getViewName(), MainetConstants.FORM_NAME, getModel());
    }

    @RequestMapping(params = "IsRegisteredMobile", method = RequestMethod.POST)
    public @ResponseBody String isRegisteredAdmin(final HttpServletRequest request) {
        final BindingResult bindingResult = bindModel(request);
        final AdminForgotPasswordModel model = getModel();
        String result = null;

        if (!bindingResult.hasErrors()) {
            if ((model.getMobileNumber() != null) && !model.getMobileNumber().equalsIgnoreCase("")) {
                if (!model.ifRegisteredAdminThenSendOTP(model.getMobileNumber())) {
                    /*result = getApplicationSession().getMessage(MainetConstants.AdminForgotpassword.ADMIN_FORGOT_PASS_MOBILE);*/
                	if (model.isAccountLock()) {
						result = getApplicationSession().getMessage(	"citizen.reset.pass.account.lock");
					} else {
						result = getApplicationSession().getMessage(	"eip.citizen.forgotPassword.isRegisteredMobile");
					}
                } else {
                    result = MainetConstants.COMMON_STATUS.SUCCESS;
                }
            } else {
                result = getApplicationSession().getMessage(MainetConstants.AdminForgotpassword.ADMIN_MOBILE_MANDATORY);
            }
        }
        return result;
    }

    @RequestMapping(params = "OTPVerficationFrm", method = RequestMethod.POST)
    public ModelAndView getOTPVerficationForm(final HttpServletRequest request) {
        bindModel(request);
        return new ModelAndView(MainetConstants.AdminForgotpassword.ADMIN_OTP_FORGOT_PASS, MainetConstants.FORM_NAME, getModel());
    }

    @RequestMapping(params = "ResendOTP", method = RequestMethod.POST)
    public @ResponseBody String resendOTP(final HttpServletRequest request) {

        bindModel(request);
        final AdminForgotPasswordModel model = getModel();
        String result = null;

        final Employee admin = model.getAdminByMobile();
        if (admin != null) {
            final boolean isValidPeriod = model.oneTimePasswordStep2(admin);

            if (isValidPeriod) {
                result = MainetConstants.TRUE;
            } else {
                result = getApplicationSession().getMessage(MainetConstants.AdminForgotpassword.ADMIN_OTP_FAIL);
            }
        } else {
            result = getApplicationSession().getMessage(MainetConstants.AdminForgotpassword.ADMIN_MOBILE_REG);
        }
        return result;
    }

    @RequestMapping(params = "verifyOTP", method = RequestMethod.POST)
    public @ResponseBody String verficationOfOTP(final HttpServletRequest request) {
        String result = "";
        bindModel(request);
        result = getModel().verficationOfOTP();
        return result;
    }

    @RequestMapping(params = "ResetPasswordFrm", method = RequestMethod.POST)
    public ModelAndView getResetPasswordFrm(final HttpServletRequest request) {
        bindModel(request);
        return new ModelAndView(MainetConstants.AdminForgotpassword.ADMIN_SET_FORGOT_PASS, MainetConstants.FORM_NAME, getModel());
    }

    @RequestMapping(params = "doResetPassword", method = RequestMethod.POST)
    public @ResponseBody String setPassword(final HttpServletRequest request) {
        bindModel(request);
        final AdminForgotPasswordModel model = getModel();
        String result = null;

        try {
            final Employee admin = model.getAdminByMobile();
            if (admin != null) {
                if (model.setPassword(admin)) {
                    result = MainetConstants.TRUE;
                } else {
                    result = getApplicationSession().getMessage(MainetConstants.AdminForgotpassword.ADMIN_PASS_SET_FAIL);
                }
            } else {
                result = getApplicationSession().getMessage(MainetConstants.AdminForgotpassword.ADMIN_PASS_SET_FAIL);
            }
        } catch (final Exception exception) {
            result = getApplicationSession().getMessage(MainetConstants.AdminForgotpassword.ADMIN_PASS_SET_FAIL);
        }

        return result;
    }

}
