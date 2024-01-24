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
import com.abm.mainet.swm.ui.model.ICTCDWasteModel;

@Controller
@RequestMapping("ICTCDWasteReport.html")
public class ICTCDWasteController extends AbstractFormController<ICTCDWasteModel> {

    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        this.getModel().setCommonHelpDocs("ICTCDWasteReport.html");
        return index();
    }

    @ResponseBody
    @RequestMapping(params = "ictcdWaste", method = RequestMethod.POST)
    public ModelAndView cdWasteReportData(final HttpServletRequest request,
            @RequestParam("monthNo") Long monthNo) {
        sessionCleanup(request);
        ICTCDWasteModel cdWasteModel = this.getModel();
        return new ModelAndView("ictcdWasteReportData", MainetConstants.FORM_NAME, cdWasteModel);
    }
}
