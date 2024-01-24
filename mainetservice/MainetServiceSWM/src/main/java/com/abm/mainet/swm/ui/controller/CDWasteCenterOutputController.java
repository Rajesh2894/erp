package com.abm.mainet.swm.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.swm.ui.model.CDWasteCenterOutputModel;

@Controller
@RequestMapping("CDWasteCenterOutputReport.html")
public class CDWasteCenterOutputController extends AbstractFormController<CDWasteCenterOutputModel> {
    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        this.getModel().setCommonHelpDocs("CDWasteCenterOutputReport.html");
        return index();
    }

    @ResponseBody
    @RequestMapping(params = "cdWasteCenteroutPut", method = RequestMethod.POST)
    public ModelAndView cdWasteCenterOutPut(final HttpServletRequest request,
            @RequestParam("monthNo") Long monthNo) {
        sessionCleanup(request);
        CDWasteCenterOutputModel cdWasteCenterOutputModel = this.getModel();
        return new ModelAndView("cdWasteCenterOutPutReportData", MainetConstants.FORM_NAME, cdWasteCenterOutputModel);
    }
}
