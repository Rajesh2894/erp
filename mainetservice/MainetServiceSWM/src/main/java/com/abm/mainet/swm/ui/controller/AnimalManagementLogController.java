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
import com.abm.mainet.swm.ui.model.AnimalManagementLogModel;

@Controller
@RequestMapping("AnimalManagementLogReport.html")
public class AnimalManagementLogController extends AbstractFormController<AnimalManagementLogModel> {
    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        this.getModel().setCommonHelpDocs("AnimalManagementLogReport.html");
        return index();
    }

    @ResponseBody
    @RequestMapping(params = "animalManagementLogReport", method = RequestMethod.POST)
    public ModelAndView animalManagementReport(final HttpServletRequest request,
            @RequestParam("monthNo") Long monthNo) {
        sessionCleanup(request);
        AnimalManagementLogModel animalManagementLogModel = this.getModel();
        return new ModelAndView("animalManagementReportData", MainetConstants.FORM_NAME, animalManagementLogModel);
    }
}
