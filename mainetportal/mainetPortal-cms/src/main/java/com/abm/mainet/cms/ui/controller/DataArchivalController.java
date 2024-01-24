package com.abm.mainet.cms.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.abm.mainet.cms.ui.model.DataArchivalModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractController;

@Controller
@RequestMapping(value = "/DataArchival.html")
public class DataArchivalController extends AbstractController<DataArchivalModel> {

    @RequestMapping(params = "archivedData")
    public ModelAndView getArchivedData(final HttpServletRequest request,
            final RedirectAttributes redirectAttributes) {
        getModel().getPublicNotice();
        return new ModelAndView("dataArchival", MainetConstants.FORM_NAME, this.getModel());
    }
}
