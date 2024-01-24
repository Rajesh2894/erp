package com.abm.mainet.cfc.objection.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.objection.dto.ObjectionDetailsDto;
import com.abm.mainet.cfc.objection.service.IObjectionDetailsService;
import com.abm.mainet.cfc.objection.ui.model.ObjectionDetailsModel;
import com.abm.mainet.common.ui.controller.AbstractFormController;

@Controller
@RequestMapping("/HearingInspectionDateLetterPrinting.html")
public class HearingInspectionDateLetterPrintingController extends AbstractFormController<ObjectionDetailsModel> {

	@Autowired
	private IObjectionDetailsService iObjectionService;
    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        getModel().bind(httpServletRequest);
        this.getModel().setCommonHelpDocs("HearingInspectionDateLetterPrinting.html");
        //Defect #128856
        this.getModel().setDepartments(iObjectionService.getDepartmentList(new ObjectionDetailsDto()));
        return defaultResult();

    }
}