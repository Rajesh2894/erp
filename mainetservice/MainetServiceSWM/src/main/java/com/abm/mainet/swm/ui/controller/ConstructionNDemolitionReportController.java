package com.abm.mainet.swm.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.swm.service.IWasteCollectorService;
import com.abm.mainet.swm.ui.model.ConstructionNDemolitionReportModel;

@Controller
@RequestMapping("/ConstructionAndDemolitionReport.html")
public class ConstructionNDemolitionReportController extends AbstractFormController<ConstructionNDemolitionReportModel> {
    
    @Autowired
    private IWasteCollectorService iWasteCollectorService;
    
    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);     
        this.getModel().setCommonHelpDocs("ConstructionAndDemolitionReport.html");
        return index();
    }

}
