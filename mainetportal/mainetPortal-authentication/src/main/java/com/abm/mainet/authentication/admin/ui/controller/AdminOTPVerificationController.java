package com.abm.mainet.authentication.admin.ui.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.authentication.admin.ui.model.AdminOTPVerificationModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.UtilityService;

/**
 *
 * @author Vivek.Kumar
 *
 */

@Controller
@RequestMapping("/AdminOTPVerification.html")
public class AdminOTPVerificationController extends AbstractFormController<AdminOTPVerificationModel> {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(final HttpServletRequest request) {
        sessionCleanup(request);
        bindModel(request);
        final AdminOTPVerificationModel model = new AdminOTPVerificationModel();
        final UserSession session = UserSession.getCurrent();
        model.setMobileNumber(session.getMobileNoToValidate());
        return new ModelAndView(getViewName(), MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody String validateMobile(final HttpServletRequest request) {
        bindModel(request);
        final AdminOTPVerificationModel model = getModel();
        String result = null;
        final Employee admin = model.getAdminByMobile();
        if (admin != null) {
            if ((admin.getAutMob() == null) || admin.getAutMob().trim().equalsIgnoreCase(MainetConstants.NO)) {
                Date validFrom = null;

                if (admin.getUpdatedDate() != null) {
                    validFrom = admin.getUpdatedDate();
                } else {
                    validFrom = admin.getOndate();
                }

                UtilityService.checkOTPValidityPeriod(validFrom, MainetConstants.OTP_VALIDITITY_IN_MINS, new Date());

                if (model.validateMobileByOTP(admin.getEmploginname(), admin.getEmplType())) {
                    UserSession.getCurrent().setMobileNoToValidate(admin.getEmpmobno());
                    result = MainetConstants.MENU.TRUE;
                }
            }
        }
        return result;
    }

    @RequestMapping(params = "ResendOTP", method = RequestMethod.POST)
    public @ResponseBody String resendOTP(final HttpServletRequest request) {

        bindModel(request);
        final AdminOTPVerificationModel model = getModel();
        String result = null;

        final Employee admin = model.getAdminByMobile();
        if (admin != null) {
            if ((admin.getAutMob() == null) || admin.getAutMob().trim().equalsIgnoreCase(MainetConstants.NO)) {
                final boolean isValidPeriod = model.resendOTP(admin);

                if (isValidPeriod) {
                    result = MainetConstants.MENU.TRUE;
                } else {
                    result = getApplicationSession().getMessage("otp.sending.error");
                }
            } else {
                result = getApplicationSession().getMessage("mobile.already.verified");
            }
        } else {
            result = getApplicationSession().getMessage("mobile.not.registered");
        }
        return result;
    }

}
