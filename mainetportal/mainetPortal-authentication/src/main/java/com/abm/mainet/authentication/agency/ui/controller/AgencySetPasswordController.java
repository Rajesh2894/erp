package com.abm.mainet.authentication.agency.ui.controller;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.authentication.agency.ui.model.AgencySetPasswordModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
//import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.util.UserSession;

/**
 *
 * @author Vivek.Kumar
 *
 */

@Controller
@RequestMapping("/AgencySetPassword.html")
public class AgencySetPasswordController extends AbstractFormController<AgencySetPasswordModel> {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(final HttpServletRequest request) {

        return new ModelAndView(getViewName(), MainetConstants.FORM_NAME, getModel());
    }

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody String setPassword(final HttpServletRequest request) throws IllegalAccessException, InvocationTargetException {
        bindModel(request);
        final AgencySetPasswordModel model = getModel();
        String result = null;

       // try {
            final String mobileNo = UserSession.getCurrent().getMobileNoToValidate();
            final Employee validAgency = model.getAgencyByMobile(mobileNo);
            if (validAgency != null) {
                if ((validAgency.getAutMob() == null) || validAgency.getAutMob().equalsIgnoreCase(MainetConstants.UNAUTH)) {
                    if (model.setPassword(validAgency)) {
                        result = MainetConstants.MENU.TRUE;
                    } else {
                        result = getApplicationSession().getMessage("password.failed");
                    }
                } else {
                    result = getApplicationSession().getMessage("mobile.number.already.registered");
                }
            } else {
                result = getApplicationSession().getMessage("password.failed");
            }
        /*} catch (final Exception ex) {
            throw new FrameworkException("Error Occurred while calling AgencySetPasswordController.setPassword()", ex);
        }*/
        return result;
    }

}
