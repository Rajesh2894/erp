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
import com.abm.mainet.swm.ui.model.DesludgingModel;

@Controller
@RequestMapping("DesludgingReport.html")
public class DesludgingController extends AbstractFormController<DesludgingModel> {
    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        this.getModel().setCommonHelpDocs("DesludgingReport.html");
        return index();
    }

    @ResponseBody
    @RequestMapping(params = "desludgingReport", method = RequestMethod.POST)
    public ModelAndView desludgingReport(final HttpServletRequest request,
            @RequestParam("monthNo") Long monthNo) {
        sessionCleanup(request);
        DesludgingModel desludgingModel = this.getModel();
        return new ModelAndView("desludgingReportData", MainetConstants.FORM_NAME, desludgingModel);
    }
}
