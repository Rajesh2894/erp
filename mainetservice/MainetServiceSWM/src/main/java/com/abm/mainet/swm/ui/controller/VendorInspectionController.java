package com.abm.mainet.swm.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.swm.ui.model.VendorInspectionModel;

@Controller
@RequestMapping("/VendorInspection.html")
public class VendorInspectionController extends AbstractFormController<VendorInspectionModel> {
    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        this.getModel().setCommonHelpDocs("VendorInspection.html");
        return index();
    }

    @ResponseBody
    @RequestMapping(params = "Add", method = RequestMethod.POST)
    public ModelAndView addVendorInspectionn(final HttpServletRequest request) {
        this.getModel().setSaveMode(MainetConstants.WorksManagement.ADD);
        return new ModelAndView("addVendorInspection/Form", MainetConstants.FORM_NAME, this.getModel());
    }

    @ResponseBody
    @RequestMapping(params = "edit", method = RequestMethod.POST)
    public ModelAndView editVendorInspection(final HttpServletRequest request) {
        sessionCleanup(request);
        this.getModel().setSaveMode(MainetConstants.WorksManagement.EDIT);
        return new ModelAndView("editVendorInspection/Form", MainetConstants.FORM_NAME, this.getModel());

    }

    @ResponseBody
    @RequestMapping(params = "view", method = RequestMethod.POST)
    public ModelAndView viewVendorInspection(final HttpServletRequest request) {
        sessionCleanup(request);
        this.getModel().setSaveMode(MainetConstants.WorksManagement.VIEW);
        return new ModelAndView("viewVendorInspection/Form", MainetConstants.FORM_NAME, this.getModel());
    }

    @ResponseBody
    @RequestMapping(params = "search", method = RequestMethod.POST)
    public ModelAndView searchVendorInspection(final HttpServletRequest request) {
        sessionCleanup(request);
        return new ModelAndView("VendorySummary", MainetConstants.FORM_NAME, this.getModel());
    }

    @ResponseBody
    @RequestMapping(params = "delete", method = RequestMethod.POST)
    public ModelAndView deleteVendorInspection(final HttpServletRequest request) {
        return new ModelAndView("VendorSummary", MainetConstants.FORM_NAME, this.getModel());

    }

}
