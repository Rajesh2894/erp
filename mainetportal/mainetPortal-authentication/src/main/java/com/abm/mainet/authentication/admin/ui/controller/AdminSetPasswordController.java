package com.abm.mainet.authentication.admin.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.authentication.admin.ui.model.AdminSetPasswordModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.util.UserSession;

/**
 *
 * @author Vivek.Kumar
 *
 */

@Controller
@RequestMapping("/AdminSetPassword.html")
public class AdminSetPasswordController extends AbstractFormController<AdminSetPasswordModel> {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(final HttpServletRequest request) {

        return new ModelAndView(getViewName(), MainetConstants.FORM_NAME, getModel());
    }

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody String setPassword(final HttpServletRequest request) {
        bindModel(request);
        final AdminSetPasswordModel model = getModel();
        String result = null;

        final String mobileNo = UserSession.getCurrent().getMobileNoToValidate();
        final Employee admin = model.getAdminByMobile(mobileNo);
        if (admin != null) {
            if ((admin.getAutMob() == null) || admin.getAutMob().equalsIgnoreCase(MainetConstants.NO)) {
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
