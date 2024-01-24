package com.abm.mainet.authentication.admin.ui.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.authentication.admin.ui.model.AdminResetPasswordModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.ui.controller.AbstractFormController;

@Controller
@RequestMapping("/AdminResetPassword.html")
public class AdminResetPasswordController extends AbstractFormController<AdminResetPasswordModel> {
    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView index(final HttpServletRequest request) {
        sessionCleanup(request);
        return new ModelAndView("AdminResetValidateMobile", MainetConstants.FORM_NAME, getModel());
    }

    @RequestMapping(params = "IsRegisteredMobile", method = RequestMethod.POST)
    public @ResponseBody String isRegisteredEmployee(final HttpServletRequest request) {
        final BindingResult bindingResult = bindModel(request);
        final Cookie[] cookies = request.getCookies();
        final AdminResetPasswordModel model = getModel();
        String result = null;
        String cookiesforaccessibility = null;
        for (final Cookie cookie : cookies) {
            if ((cookie.getName() != null) && cookie.getName().equalsIgnoreCase("accessibility")) {
                cookiesforaccessibility = cookie.getValue();
            }
        }

        
        if ((request.getSession().getAttribute("captcha")!=null) && request.getSession().getAttribute("captcha").equals(model.getCaptchaSessionLoginValue())
                || (cookiesforaccessibility!=null && cookiesforaccessibility.equalsIgnoreCase(MainetConstants.AUTH))) {
        if (!bindingResult.hasErrors()) {
            if ((model.getMobileNumber() != null) && !model.getMobileNumber().equalsIgnoreCase(MainetConstants.BLANK)) {
                if (!model.ifRegisteredEmployeeThenSendOTP(model.getMobileNumber())) {
                    result = getApplicationSession().getMessage("eip.citizen.forgotPassword.isRegisteredMobile");
                } else {
                    result = MainetConstants.SUCCESS;
                    request.getSession().removeAttribute("captcha");
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
        } else {
            return MainetConstants.CAPTCHA_NOT_MATCHED;
        }
        
    }

    @RequestMapping(params = "OTPVerficationFrm", method = RequestMethod.POST)
    public ModelAndView getOTPVerficationForm(final HttpServletRequest request) {
        bindModel(request);
        return new ModelAndView("AdminOTPResetPassword", MainetConstants.FORM_NAME, getModel());
    }

    @RequestMapping(params = "ResendOTP", method = RequestMethod.POST)
    public @ResponseBody String resendOTP(final HttpServletRequest request) {

        bindModel(request);
        final AdminResetPasswordModel model = getModel();
        String result = null;

        final Employee citizen = model.getEmployeeByMobile();
        if (citizen != null) {
            final boolean isValidPeriod = model.oneTimePasswordStep2(citizen);

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
    public ModelAndView verficationOfOTP(final HttpServletRequest request) {
        bindModel(request);
        AdminResetPasswordModel model=getModel();
        model.setResult(model.verficationOfOTP());
        if(model.getResult().equals("success")) {
        	 return new ModelAndView("AdminResetPassword", MainetConstants.FORM_NAME, model);
        }else {
        	final ModelAndView mv =new ModelAndView("AdminOTPResetPasswordValidn", MainetConstants.FORM_NAME, model);
        	mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, model.getBindingResult());
        	return mv;
        }
    }

    @RequestMapping(params = "ResetPasswordFrm", method = RequestMethod.POST)
    public ModelAndView getResetPasswordFrm(final HttpServletRequest request) {
        bindModel(request);
        return new ModelAndView("AdminResetPassword", MainetConstants.FORM_NAME, getModel());
    }

    @RequestMapping(params = "doResetPassword", method = RequestMethod.POST)
    public @ResponseBody String setPassword(final HttpServletRequest request) {
        bindModel(request);
        final AdminResetPasswordModel model = getModel();
        String result = null;

        if ((model.getMobileNumber() != null) && !model.getMobileNumber().equalsIgnoreCase(MainetConstants.BLANK)) {
            final Employee employee = model.getEmployeeByMobile();

            if (employee != null) {
                if (model.setPassword(employee)) {
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


