package com.abm.mainet.authentication.agency.ui.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.authentication.agency.ui.model.AgencyOTPVerificationModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.UtilityService;

/**
 *
 * @author vivek.kumar
 *
 */

@Controller
@RequestMapping("/AgencyOTPVerification.html")
public class AgencyOTPVerificationController extends AbstractFormController<AgencyOTPVerificationModel> {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(final HttpServletRequest request) {
        sessionCleanup(request);
        bindModel(request);
        final AgencyOTPVerificationModel model = new AgencyOTPVerificationModel();
        final UserSession session = UserSession.getCurrent();
        model.setMobileNumber(session.getMobileNoToValidate());
        return new ModelAndView(getViewName(), MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody String validateMobile(final HttpServletRequest request) {
        bindModel(request);
        final AgencyOTPVerificationModel model = getModel();
        String result = null;
        final Employee agency = model.getAgencyByMobile();
        if (agency != null) {
            if ((agency.getAutMob() == null) || agency.getAutMob().trim().equalsIgnoreCase(MainetConstants.UNAUTH)) {
                Date validFrom = null;

                if (agency.getUpdatedDate() != null) {
                    validFrom = agency.getUpdatedDate();
                } else {
                    validFrom = agency.getOndate();
                }

                final boolean isValidPeriod = UtilityService.checkOTPValidityPeriod(validFrom,
                        MainetConstants.OTP_VALIDITITY_IN_MINS, new Date());

                if (isValidPeriod) {
                    if (model.validateMobileByOTP(agency.getEmploginname(), agency.getEmplType())) {
                        UserSession.getCurrent().setMobileNoToValidate(agency.getEmpmobno());
                        result = MainetConstants.MENU.TRUE;
                    }
                } else {
                    result = getApplicationSession().getMessage("agency.otp.verification.experied");
                }
            }
        }
        return result;
    }

    @RequestMapping(params = "ResendOTP", method = RequestMethod.POST)
    public @ResponseBody String resendOTP(final HttpServletRequest request) {

        bindModel(request);
        final AgencyOTPVerificationModel model = getModel();
        String result = null;

        final Employee agency = model.getAgencyByMobile();
        if (agency != null) {
            if ((agency.getAutMob() == null) || agency.getAutMob().trim().equalsIgnoreCase(MainetConstants.UNAUTH)) {
                final boolean isValidPeriod = model.resendOTP(agency);

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
