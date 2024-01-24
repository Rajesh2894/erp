package com.abm.mainet.authentication.citizen.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.authentication.citizen.ui.model.AutherizationModel;
import com.abm.mainet.common.ui.controller.AbstractController;

/**
 *
 * @author Kavali.Kiran
 *
 */
@Controller
@RequestMapping("/Autherization.html")
public class AutherizationController extends AbstractController<AutherizationModel> {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showStatusForm(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        return index();
    }

    @RequestMapping(params = "getRandomKey", method = RequestMethod.POST)
    public @ResponseBody String getRandomKey(final HttpServletRequest request) {
        return null;
    }

}
