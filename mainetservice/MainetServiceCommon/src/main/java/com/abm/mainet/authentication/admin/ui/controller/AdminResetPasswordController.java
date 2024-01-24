package com.abm.mainet.authentication.admin.ui.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.authentication.admin.ui.model.AdminResetPasswordModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;

@Controller
@RequestMapping("/AdminResetPassword.html")
public class AdminResetPasswordController extends AbstractFormController<AdminResetPasswordModel> {
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(final HttpServletRequest request) {
        sessionCleanup(request);
        return new ModelAndView(MainetConstants.AdminForgotpassword.ADMIN_SET_VALDIITOR_MOBILE, MainetConstants.FORM_NAME,
                getModel());
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
            if ((model.getMobileNumber() != null) && !model.getMobileNumber().equalsIgnoreCase("")) {
                if (!model.ifRegisteredEmployeeThenSendOTP(model.getMobileNumber())) {
                    /* result = getApplicationSession().getMessage(MainetConstants.AdminForgotpassword.ADMIN_REGISTRED_MOBILE); */
                    if (model.isAccountLock()) {
                        result = getApplicationSession().getMessage("citizen.reset.pass.account.lock");
                    }else if(model.isTemplateAvailable() == false) {
                    	result = getApplicationSession().getMessage("eip.citizen.forgotPassword.isTemplateMissing");
                    }else {
                        result = getApplicationSession().getMessage("eip.citizen.forgotPassword.isRegisteredMobileEmail");
                    }
                } else {
                    result = MainetConstants.COMMON_STATUS.SUCCESS;
                    request.getSession().removeAttribute("captcha");
                }
            } else {
                result = getApplicationSession()
                        .getMessage(MainetConstants.AdminForgotpassword.ADMIN_FORGOT_PASS_MOBILE_MANDTORY);
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
        return new ModelAndView(MainetConstants.AdminForgotpassword.ADMIN_OTP_RESET, MainetConstants.FORM_NAME, getModel());
    }

    @RequestMapping(params = "ResendOTP", method = RequestMethod.POST)
    public @ResponseBody String resendOTP(final HttpServletRequest request) {

        bindModel(request);
        final AdminResetPasswordModel model = getModel();
        String result = null;

        final List<Employee> empList = model.getEmployeeByMobile();
        if (empList != null && !empList.isEmpty()) {
            final boolean isValidPeriod = model.oneTimePasswordStep2(empList);

            if (isValidPeriod) {
                result = MainetConstants.TRUE;
            } else {
                result = getApplicationSession().getMessage(MainetConstants.AdminForgotpassword.ADMIN_CITIZEN_OTP_SEND_FAIL);
            }
        } else {
            result = getApplicationSession().getMessage(MainetConstants.AdminForgotpassword.EMPLOYEE_MOBILE_NUM_NOT_REG);
        }
        return result;
    }

    @RequestMapping(params = "verifyOTP", method = RequestMethod.POST)
    public ModelAndView verficationOfOTP(final HttpServletRequest request) {
        bindModel(request);
       AdminResetPasswordModel model = getModel();
       model.setResult(model.verficationOfOTP());
       if(model.getResult().equals("success")) {
           return new ModelAndView(MainetConstants.AdminForgotpassword.ADMIN_RESET_PASSWORD, MainetConstants.FORM_NAME, model);

       }else {
    	   final ModelAndView mv = new ModelAndView(MainetConstants.AdminForgotpassword.ADMIN_OTP_RESET_VALIDN, MainetConstants.FORM_NAME, model);
			mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, model.getBindingResult());
			return mv;
       }
        
    }

    @RequestMapping(params = "ResetPasswordFrm", method = RequestMethod.POST)
    public ModelAndView getResetPasswordFrm(final HttpServletRequest request) {
        bindModel(request);
        return new ModelAndView(MainetConstants.AdminForgotpassword.ADMIN_RESET_PASSWORD, MainetConstants.FORM_NAME, getModel());
    }

    @RequestMapping(params = "doResetPassword", method = RequestMethod.POST)
    public @ResponseBody String setPassword(final HttpServletRequest request) {
        bindModel(request);
        final AdminResetPasswordModel model = getModel();
        String result = null;

        try {
            if ((model.getMobileNumber() != null) && !model.getMobileNumber().equalsIgnoreCase("")) {
                final List<Employee> employeeList = model.getEmployeeByMobile();
                if (employeeList != null && !employeeList.isEmpty()) {
                    if (model.setPassword(employeeList)) {
                        result = MainetConstants.TRUE;
                    } else {
                        result = getApplicationSession()
                                .getMessage(MainetConstants.AdminForgotpassword.CITIZEN_RESET_PASSWORD_FAILED);
                    }
                } else {
                    result = getApplicationSession().getMessage(MainetConstants.AdminForgotpassword.CITIZEN_INVALID_USER);
                }
            } else {
                result = getApplicationSession().getMessage(MainetConstants.AdminForgotpassword.CITIZEN_INVALID_USER);
            }
        } catch (final Exception exception) {
            result = getApplicationSession().getMessage(MainetConstants.AdminForgotpassword.CITIZEN_RESET_PASSWORD_FAILED);
        }

        return result;
    }
    @RequestMapping(method = RequestMethod.GET, params = "captcha")
    public void GlobalPolicy(final HttpServletRequest httpServletRequest, final HttpServletResponse response,
            @RequestParam final String id) {

        final ApplicationSession session = ApplicationSession.getInstance();
        final int width = 150;
        final int height = 50;
        char[] alphNum;
        try {
            final BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            final Graphics2D g2d = bufferedImage.createGraphics();
            final Font font = new Font("Georgia", Font.BOLD, 20);
            g2d.setFont(font);
            final RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHints(rh);
            final GradientPaint gp = new GradientPaint(0, 0,
                    Color.LIGHT_GRAY, 0, height / 2, Color.white, true);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, width, height);
            g2d.setColor(new Color(51, 51, 51));
            if (session.getMessage("captcha.enable").equalsIgnoreCase(MainetConstants.MENU.FALSE)) {
                alphNum = MainetConstants.NO_CAPTCHA_GENERATE_VALUE.toCharArray();
            } else {
                alphNum = MainetConstants.CAPTCHA_GENERATE_VALUE.toCharArray();
            }
            final Random rnd = new Random();
            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 4; i++) {
                sb.append(alphNum[rnd.nextInt(alphNum.length)]);
            }
            final String generatedCaptcha = sb.toString();
            httpServletRequest.getSession().setAttribute("captcha", generatedCaptcha);
            final char[] charArray = generatedCaptcha.toCharArray();
            int x = 0;
            int y = 0;
            for (int i = 0; i < charArray.length; i++) {
                x += 10 + (Math.abs(rnd.nextInt()) % 15);
                y = 20 + (Math.abs(rnd.nextInt()) % 20);
                g2d.drawChars(charArray, i, 1, x, y);
            }
            g2d.dispose();
            response.setContentType(MainetConstants.CONTENT_TYPE_PNG);
            final OutputStream os = response.getOutputStream();
            ImageIO.write(bufferedImage, MainetConstants.PNG, os);
            os.close();

        } catch (final IOException e) {
            logger.error(e);
        }
    }
    
}
