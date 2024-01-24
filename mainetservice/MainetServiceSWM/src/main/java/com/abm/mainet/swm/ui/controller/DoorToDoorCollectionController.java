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
import com.abm.mainet.swm.ui.model.DoorToDoorCollectionModel;

@Controller
@RequestMapping("DoorToDoorCollectionReport.html")
public class DoorToDoorCollectionController extends AbstractFormController<DoorToDoorCollectionModel> {

    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        this.getModel().setCommonHelpDocs("DoorToDoorCollectionReport.html");
        return index();
    }

    @ResponseBody
    @RequestMapping(params = "doorTodoor", method = RequestMethod.POST)
    public ModelAndView vehiclelogbookmainpage(final HttpServletRequest request,
            @RequestParam("monthNo") Long monthNo) {
        sessionCleanup(request);
        DoorToDoorCollectionModel doorToDoor = this.getModel();
        return new ModelAndView("DoorToDoorReportData", MainetConstants.FORM_NAME, doorToDoor);
    }
}
