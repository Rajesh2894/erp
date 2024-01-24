/**
 *
 */
package com.abm.mainet.authentication.citizen.ui.controller;

import javax.servlet.http.Cookie;
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

import com.abm.mainet.authentication.citizen.ui.model.CitizenForgotPasswordModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.ui.model.MenuRoleEntitlement;
import com.abm.mainet.common.util.UserSession;

@Controller
@RequestMapping("/CitizenForgotPassword.html")
public class CitizenForgotPasswordController extends AbstractFormController<CitizenForgotPasswordModel> {
    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView index(final HttpServletRequest request) {
        sessionCleanup(request);
        return new ModelAndView(getViewName(), MainetConstants.FORM_NAME, getModel());
    }

    @RequestMapping(params = "IsRegisteredMobile", method = RequestMethod.POST)
    public ModelAndView isRegisteredCitizen(final HttpServletRequest request,
            @RequestParam("checkOTPTypeValue") final String type) {
        final BindingResult bindingResult = bindModel(request);
        final Cookie[] cookies = request.getCookies();
        final CitizenForgotPasswordModel model = getModel();
        getModel().setMobileNumberType(type);
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
                if (!model.ifRegisteredCitizenThenSendOTP(model.getMobileNumber())) {
                    if (model.isAccountLock()) {
                        model.addValidationError(getApplicationSession().getMessage("citizen.reset.pass.account.lock"));
                    } else {
                        model.addValidationError(getApplicationSession().getMessage("eip.citizen.forgotPassword.isRegisteredMobile"));
                    }
                } else {
                    result = MainetConstants.SUCCESS;
                    request.getSession().removeAttribute("captcha");
                    return new ModelAndView("CitizenOTPForgotPassword", MainetConstants.FORM_NAME, getModel());  
                }
            } else {
                model.addValidationError(getApplicationSession().getMessage("eip.citizen.forgotPassword.mobileNoMandatory"));
            }
        } else {

            for (final Object error : bindingResult.getAllErrors()) {
                if (error instanceof FieldError) {

                }
                if (error instanceof ObjectError) {

                }
            }
        }
        
        }else {
        	model.addValidationError(MainetConstants.CAPTCHA_NOT_MATCHED);
        }

       final ModelAndView mv = new ModelAndView("CitizenForgotPasswordValidn", MainetConstants.FORM_NAME, model);
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, model.getBindingResult());
		return mv;   
}

    @RequestMapping(params = "changeMobileNumber", method = RequestMethod.POST)
    public ModelAndView changeMobileNumber(final HttpServletRequest request,
            @RequestParam("checkOTPTypeValue") final String type) {
        final BindingResult bindingResult = bindModel(request);
        final CitizenForgotPasswordModel model = getModel();
        getModel().setMobileNumberType(type);
        final Cookie[] cookies = request.getCookies();
        String cookiesforaccessibility = null;
        String result = null;
        for (final Cookie cookie : cookies) {
            if ((cookie.getName() != null) && cookie.getName().equalsIgnoreCase("accessibility")) {
                cookiesforaccessibility = cookie.getValue();
            }
        }
        final Employee emp = UserSession.getCurrent().getEmployee();
        if (emp.getEmpGender() != null && emp.getEmpGender().length() > 1) {
            emp.setEmpGender(model.getNonHierarchicalLookUpObject(new Long(emp.getEmpGender())).getLookUpCode());
        }
        if ((request.getSession().getAttribute("captcha")!=null) && request.getSession().getAttribute("captcha").equals(model.getCaptchaSessionLoginValue())
                || (cookiesforaccessibility!=null && cookiesforaccessibility.equalsIgnoreCase(MainetConstants.AUTH))) {
        if (!bindingResult.hasErrors()) {
            if ((model.getMobileNumber() != null) && !model.getMobileNumber().equalsIgnoreCase(MainetConstants.BLANK)) {
                if (!model.isUniqueMobileNumber(model.getMobileNumber())) {
                    model.addValidationError( getApplicationSession().getMessage("Employee.uniqueMobileNum"));
                } else {
                    if (!model.oneTimePasswordStep2(emp)) {
                        model.addValidationError( getApplicationSession().getMessage("Employee.uniqueMobileNum"));
                    } else {
                    	request.getSession().removeAttribute("captcha");
                        return new ModelAndView("CitizenOTPForgotPassword", MainetConstants.FORM_NAME, getModel());  
                    }
                }
            } else {
                model.addValidationError( getApplicationSession().getMessage("Employee.uniqueMobileNum"));
            }
        } else {

            for (final Object error : bindingResult.getAllErrors()) {
                if (error instanceof FieldError) {
                }
                if (error instanceof ObjectError) {
                }
            }
        }
        }else {
        	model.addValidationError(MainetConstants.CAPTCHA_NOT_MATCHED);
        }

        final ModelAndView mv = new ModelAndView("CitizenForgotPasswordValidn", MainetConstants.FORM_NAME, model);
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, model.getBindingResult());
		return mv;
    }

    @RequestMapping(params = "OTPVerficationFrm", method = RequestMethod.POST)
    public ModelAndView getOTPVerficationForm(final HttpServletRequest request,
            @RequestParam("checkOTPTypeValue") final String type) {
        bindModel(request);
        if (type.equals("Nonregister")) {
        }
        getModel().setMobileNumberType(type);
        return new ModelAndView("CitizenOTPForgotPassword", MainetConstants.FORM_NAME, getModel());
    }

    @RequestMapping(params = "ResendOTP", method = RequestMethod.POST)
    public @ResponseBody String resendOTP(final HttpServletRequest request) {

        bindModel(request);
        final CitizenForgotPasswordModel model = getModel();
        String result = null;

        Employee citizen = null;
        if (model.getMobileNumberType().equals(MainetConstants.MOBILE_NUMBER_IDENTIFICATION.REGISTERED_NUMBER)) {
            citizen = model.getCitizenByMobile();
        } else {
            citizen = UserSession.getCurrent().getEmployee();
        }

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
    public Object verficationOfOTP(final HttpServletRequest request, @RequestParam("checkOTPTypeValue") final String type) {
        String result = MainetConstants.BLANK;
        bindModel(request);
        
        result = getModel().verficationOfOTP();
        if(result.equals("success")) {
        	if(type.equals("Register")) {
        		 return new ModelAndView("CitizenSetForgotPassword", MainetConstants.FORM_NAME, getModel()); 
        	}else {
        		if (getModel().setMobileNumber()) {
                    return new ModelAndView("redirect://CitizenHome.html?forgotOTPView");
                } else {
                    getModel().addValidationError(getApplicationSession().getMessage("eip.citizen.resetPassword.failed"));
                }
        	}
        
        }
        else {
        	getModel().addValidationError(result);
        }
        final ModelAndView mv = new ModelAndView("CitizenOTPForgotPasswordValidn", MainetConstants.FORM_NAME, getModel());
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;
    }

   
    
    @RequestMapping(params = "ResetPasswordFrm", method = RequestMethod.POST)
    public ModelAndView getResetPasswordFrm(final HttpServletRequest request) {
        bindModel(request);
        return new ModelAndView("CitizenSetForgotPassword", MainetConstants.FORM_NAME, getModel());
    }

    @RequestMapping(params = "doResetPassword", method = RequestMethod.POST)
    public @ResponseBody String setPassword(final HttpServletRequest request) {
        bindModel(request);
        final CitizenForgotPasswordModel model = getModel();
        String result = null;

        final Employee citizen = model.getCitizenByMobile();
        if (citizen != null) {
            if (model.setPassword(citizen)) {

                if (((MenuRoleEntitlement.getCurrentMenuRoleManager().getChildList() == null)
                        || MenuRoleEntitlement.getCurrentMenuRoleManager().getChildList().isEmpty())
                        && ((MenuRoleEntitlement.getCurrentMenuRoleManager().getParentList() == null)
                                || MenuRoleEntitlement.getCurrentMenuRoleManager().getParentList().isEmpty())) {
                    model.accessListAndMenuForCitizen(citizen);
                }
                result = MainetConstants.MENU.TRUE;
            } else {
                result = getApplicationSession().getMessage("eip.citizen.resetPassword.failed");
            }
        } else {
            result = getApplicationSession().getMessage("eip.citizen.resetPassword.failed");
        }

        return result;
    }

    @RequestMapping(params = "updateMobileNumber", produces = "application/json", method = RequestMethod.POST)
    public @ResponseBody String setMobileNumber(final HttpServletRequest request) {
        bindModel(request);
        final CitizenForgotPasswordModel model = getModel();

        String result = MainetConstants.BLANK;

        if (model.setMobileNumber()) {
            result = MainetConstants.MENU.TRUE;
        } else {
            result = getApplicationSession().getMessage("eip.citizen.resetPassword.failed");
        }

        return result;

    }
    
}
