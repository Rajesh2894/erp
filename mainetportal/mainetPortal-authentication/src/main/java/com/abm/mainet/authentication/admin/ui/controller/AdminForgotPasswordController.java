
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
import com.aspose.slides.p2cbca448.get;

@Controller
@RequestMapping("/AdminForgotPassword.html")
public class AdminForgotPasswordController extends AbstractFormController<AdminForgotPasswordModel> {
    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView index(final HttpServletRequest request) {
        sessionCleanup(request);
        return new ModelAndView(getViewName(), MainetConstants.FORM_NAME, getModel());
    }

    @RequestMapping(params = "IsRegisteredMobile", method = RequestMethod.POST)
    public ModelAndView isRegisteredAdmin(final HttpServletRequest request) {
        final BindingResult bindingResult = bindModel(request);
        final AdminForgotPasswordModel model = getModel();
       // String result = null;

        if (!bindingResult.hasErrors()) {
            if ((model.getMobileNumber() != null) && !model.getMobileNumber().equalsIgnoreCase(MainetConstants.BLANK)) {
                if (!model.ifRegisteredAdminThenSendOTP(model.getMobileNumber())) {
                	model.addValidationError(getApplicationSession().getMessage("eip.admin.forgotPassword.isRegisteredMobile"));
                    //result = getApplicationSession().getMessage("eip.admin.forgotPassword.isRegisteredMobile");
                } else {
                    //result = MainetConstants.SUCCESS;
                    return new ModelAndView("AdminOTPForgotPassword", MainetConstants.FORM_NAME, getModel());
                }
            } else {
              //  result = getApplicationSession().getMessage("eip.admin.forgotPassword.mobileNoMandatory");
                model.addValidationError(getApplicationSession().getMessage("eip.admin.forgotPassword.mobileNoMandatory"));
            }
        }

        final ModelAndView mv = new ModelAndView("AdminForgotPasswordValidn", MainetConstants.FORM_NAME, model);
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, model.getBindingResult());
		return mv; 
    }

    @RequestMapping(params = "OTPVerficationFrm", method = RequestMethod.POST)
    public ModelAndView getOTPVerficationForm(final HttpServletRequest request) {
        bindModel(request);
        return new ModelAndView("AdminOTPForgotPassword", MainetConstants.FORM_NAME, getModel());
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
                result = MainetConstants.MENU.TRUE;
            } else {
                result = getApplicationSession().getMessage("otp.sending.error");
            }
        } else {
            result = getApplicationSession().getMessage("mobile.not.registered");
        }
        return result;
    }

    @RequestMapping(params = "verifyOTP", method = RequestMethod.POST)
    public ModelAndView verficationOfOTP(final HttpServletRequest request) {
        String result = MainetConstants.BLANK;
        bindModel(request);
        result = getModel().verficationOfOTP();
        if(result.equals("success")) {
        	 return new ModelAndView("AdminSetForgotPassword", MainetConstants.FORM_NAME, getModel());
        }else {
        	getModel().addValidationError(result);
        }
        final ModelAndView mv = new ModelAndView("AdminOTPForgotPasswordValidn", MainetConstants.FORM_NAME, getModel());
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv; 

    }

    @RequestMapping(params = "ResetPasswordFrm", method = RequestMethod.POST)
    public ModelAndView getResetPasswordFrm(final HttpServletRequest request) {
        bindModel(request);
        return new ModelAndView("AdminSetForgotPassword", MainetConstants.FORM_NAME, getModel());
    }

    @RequestMapping(params = "doResetPassword", method = RequestMethod.POST)
    public @ResponseBody String setPassword(final HttpServletRequest request) {
        bindModel(request);
        final AdminForgotPasswordModel model = getModel();
        String result = null;

        final Employee admin = model.getAdminByMobile();
        if (admin != null) {
            if (model.setPassword(admin)) {
                result = MainetConstants.MENU.TRUE;
            } else {
                result = getApplicationSession().getMessage("password.failed");
            }
        } else {
            result = getApplicationSession().getMessage("password.failed");
        }

        return result;
    }

}
