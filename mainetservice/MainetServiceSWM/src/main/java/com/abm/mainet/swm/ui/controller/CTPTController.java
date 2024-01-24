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
import com.abm.mainet.swm.ui.model.CTPTModel;

@Controller
@RequestMapping("CTPTNewReport.html")
public class CTPTController extends AbstractFormController<CTPTModel> {
    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        this.getModel().setCommonHelpDocs("CTPTNewReport.html");
        return index();
    }

    @ResponseBody
    @RequestMapping(params = "ctptData", method = RequestMethod.POST)
    public ModelAndView ctptReportData(final HttpServletRequest request,
            @RequestParam("monthNo") Long monthNo) {
        sessionCleanup(request);
        CTPTModel ctptModel = this.getModel();
        return new ModelAndView("ctptReportData", MainetConstants.FORM_NAME, ctptModel);
    }
}
