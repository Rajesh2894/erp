package com.abm.mainet.authentication.citizen.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author Kavali.Kiran
 *
 */
@Controller
@RequestMapping("/AccessDenied.html")
public class AccessDeniedController {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView accessDenied(HttpServletRequest httpServletRequest) {
        return new ModelAndView("AccessDenied");

    }

}
