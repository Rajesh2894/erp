/**
 *
 */
package com.abm.mainet.authentication.citizen.ui.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.authentication.citizen.ui.model.CitizenOTPVerificationModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.UtilityService;

@Controller
@RequestMapping("/CitizenOTPVerification.html")
public class CitizenOTPVerificationController extends AbstractFormController<CitizenOTPVerificationModel> {
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(final HttpServletRequest request) {
        sessionCleanup(request);
        bindModel(request);
        final CitizenOTPVerificationModel model = new CitizenOTPVerificationModel();
        final UserSession session = UserSession.getCurrent();
        model.setMobileNumber(session.getMobileNoToValidate());
        return new ModelAndView(getViewName(), MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody String validateMobile(final HttpServletRequest request) {
        bindModel(request);
        final CitizenOTPVerificationModel model = getModel();
        String result = MainetConstants.BLANK;
        final Employee citizen = model.getCitizenByMobile();
        if (citizen != null) {
            if ((citizen.getAutMob() == null) || citizen.getAutMob().trim().equalsIgnoreCase(MainetConstants.UNAUTH)) {
                Date validFrom = null;

                if (citizen.getUpdatedDate() != null) {
                    validFrom = citizen.getUpdatedDate();
                } else {
                    validFrom = citizen.getOndate();
                }

                final boolean isValidPeriod = UtilityService.checkOTPValidityPeriod(validFrom,
                        MainetConstants.OTP_VALIDITITY_IN_MINS, new Date());

                if (isValidPeriod) {
                    if (model.validateMobileByOTP(citizen.getEmploginname(), citizen.getEmplType())) {
                        UserSession.getCurrent().setMobileNoToValidate(citizen.getEmpmobno());
                        result = MainetConstants.MENU.TRUE;
                    }
                } else {
                    result = getApplicationSession().getMessage("otp.expired");
                }
            }
        }
        return result;
    }

    @RequestMapping(params = "ResendOTP", method = RequestMethod.POST)
    public @ResponseBody String resendOTP(final HttpServletRequest request) {

        bindModel(request);
        final CitizenOTPVerificationModel model = getModel();
        String result = null;

        final Employee citizen = model.getCitizenByMobile();
        if (citizen != null) {
            if ((citizen.getAutMob() == null) || citizen.getAutMob().trim().equalsIgnoreCase(MainetConstants.UNAUTH)) {
                final boolean isValidPeriod = model.resendOTP(citizen);

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
