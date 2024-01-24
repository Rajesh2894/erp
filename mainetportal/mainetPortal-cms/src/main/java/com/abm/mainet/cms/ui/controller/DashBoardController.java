package com.abm.mainet.cms.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cms.ui.model.DashBoardModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractController;

@Controller
@RequestMapping(value = { "/DashBoard.html", "/Welfare.html", "/Administration.html", "/ArtCulture.html", "/HumanResource.html",
        "/Infrastructure.html", "/Agriculture.html" })
public class DashBoardController extends AbstractController<DashBoardModel> {

    @RequestMapping
    public ModelAndView index(final HttpServletRequest request) {
        this.sessionCleanup(request);

        if (request.getRequestURI().contains("DashBoard.html")) {
            return new ModelAndView("dashboard", MainetConstants.FORM_NAME, this.getModel());
        }
        if (request.getRequestURI().contains("Welfare.html")) {
            return new ModelAndView("SocialWelfare", MainetConstants.FORM_NAME, this.getModel());
        }
        if (request.getRequestURI().contains("Administration.html")) {
            return new ModelAndView("AdministrationFinance", MainetConstants.FORM_NAME, this.getModel());
        }
        if (request.getRequestURI().contains("ArtCulture.html")) {
            return new ModelAndView("ArtCulture", MainetConstants.FORM_NAME, this.getModel());
        }
        if (request.getRequestURI().contains("HumanResource.html")) {
            return new ModelAndView("HumanResource", MainetConstants.FORM_NAME, this.getModel());
        }
        if (request.getRequestURI().contains("Infrastructure.html")) {
            return new ModelAndView("InfraStructure", MainetConstants.FORM_NAME, this.getModel());
        }
        if (request.getRequestURI().contains("Agriculture.html")) {
            return new ModelAndView("Agriculture", MainetConstants.FORM_NAME, this.getModel());
        }

        return new ModelAndView("dashboard", MainetConstants.FORM_NAME, this.getModel());

    }

    @RequestMapping(params = "page")
    public ModelAndView page(@RequestParam String scheme, final HttpServletRequest request) {

        return new ModelAndView(scheme, MainetConstants.FORM_NAME, this.getModel());
    }
    
    @RequestMapping(params = "report")
    public ModelAndView report(@RequestParam String reportname, final HttpServletRequest request) {

    	request.setAttribute("reportName",reportname);
        return new ModelAndView("Frame", MainetConstants.FORM_NAME, this.getModel());
    }

}
