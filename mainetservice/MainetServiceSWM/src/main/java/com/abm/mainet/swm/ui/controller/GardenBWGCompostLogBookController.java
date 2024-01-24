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
import com.abm.mainet.swm.ui.model.GardenBWGCompostLogBookModel;

@Controller
@RequestMapping("GardenBWGCompostLogBookReport.html")
public class GardenBWGCompostLogBookController extends AbstractFormController<GardenBWGCompostLogBookModel> {
    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        this.getModel().setCommonHelpDocs("GardenBWGCompostLogBookReport.html");
        return index();
    }

    @ResponseBody
    @RequestMapping(params = "gardenBWGReport", method = RequestMethod.POST)
    public ModelAndView gardenBWGReport(final HttpServletRequest request,
            @RequestParam("monthNo") Long monthNo) {
        sessionCleanup(request);
        GardenBWGCompostLogBookModel gardenBWGCompostLogBookModel = this.getModel();
        return new ModelAndView("gardenBWGReportData", MainetConstants.FORM_NAME, gardenBWGCompostLogBookModel);
    }
}
