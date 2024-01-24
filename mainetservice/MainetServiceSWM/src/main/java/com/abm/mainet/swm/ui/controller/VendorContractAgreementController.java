package com.abm.mainet.swm.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.swm.ui.model.VendorContractAgreementModel;

@Controller
@RequestMapping("/VendorContractAgreement.html")
public class VendorContractAgreementController extends AbstractFormController<VendorContractAgreementModel> {
    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        return index();
    }

    @ResponseBody
    @RequestMapping(params = "Add", method = RequestMethod.POST)
    public ModelAndView addVendorContractAgreement(final HttpServletRequest request) {
        this.getModel().setSaveMode(MainetConstants.WorksManagement.ADD);
        return new ModelAndView("addVendorContractAgreement/Form", MainetConstants.FORM_NAME, this.getModel());
    }

    @ResponseBody
    @RequestMapping(params = "edit", method = RequestMethod.POST)
    public ModelAndView editVendorContractAgreement(final HttpServletRequest request) {
        sessionCleanup(request);
        this.getModel().setSaveMode(MainetConstants.WorksManagement.EDIT);
        return new ModelAndView("editVendorContractAgreement/Form", MainetConstants.FORM_NAME, this.getModel());

    }

    @ResponseBody
    @RequestMapping(params = "view", method = RequestMethod.POST)
    public ModelAndView viewVendorContractAgreement(final HttpServletRequest request) {
        sessionCleanup(request);
        this.getModel().setSaveMode(MainetConstants.WorksManagement.VIEW);
        return new ModelAndView("viewVendorContractAgreement/Form", MainetConstants.FORM_NAME, this.getModel());
    }

    @ResponseBody
    @RequestMapping(params = "search", method = RequestMethod.POST)
    public ModelAndView searchVendorContractAgreement(final HttpServletRequest request) {
        sessionCleanup(request);
        return new ModelAndView("VendorContractAgreementSummary", MainetConstants.FORM_NAME, this.getModel());
    }

    @ResponseBody
    @RequestMapping(params = "delete", method = RequestMethod.POST)
    public ModelAndView deleteVendorContractAgreement(final HttpServletRequest request) {
        return new ModelAndView("VendorContractAgreementSummary", MainetConstants.FORM_NAME, this.getModel());

    }

}
