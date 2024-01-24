package com.abm.mainet.authentication.citizen.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.authentication.citizen.ui.model.CitizenSetPasswordModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.util.UserSession;

@Controller
@RequestMapping("/CitizenSetPassword.html")
public class CitizenSetPasswordController extends AbstractFormController<CitizenSetPasswordModel> {
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(final HttpServletRequest request) {
        return new ModelAndView(getViewName(), MainetConstants.FORM_NAME, getModel());
    }

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody String setPassword(final HttpServletRequest request) {
        bindModel(request);
        final CitizenSetPasswordModel model = getModel();
        String result = null;

        final String mobileNo = UserSession.getCurrent().getMobileNoToValidate();
        final Employee citizen = model.getCitizenByMobile(mobileNo);
        if (citizen != null) {
            if ((citizen.getAutMob() == null) || citizen.getAutMob().equalsIgnoreCase(MainetConstants.UNAUTH)) {
                if (model.setPassword(mobileNo)) {
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

        return result;
    }
}
