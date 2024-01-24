package com.abm.mainet.authentication.admin.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.authentication.admin.ui.model.AutherizationFailModel;
import com.abm.mainet.common.ui.controller.AbstractController;

/**
 *
 * @author Kavali.Kiran
 *
 */
@Controller
@RequestMapping("/AutherizationFail.html")
public class AutherizationFailController extends AbstractController<AutherizationFailModel> {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showStatusForm(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        return index();
    }
}
