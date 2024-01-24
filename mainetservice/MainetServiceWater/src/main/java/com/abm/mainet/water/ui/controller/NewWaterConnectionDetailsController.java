package com.abm.mainet.water.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.water.ui.model.NewWaterConnectionDetailsModel;

@Controller
@RequestMapping("/NewWaterConnectionDetails.html")
public class NewWaterConnectionDetailsController extends AbstractFormController<NewWaterConnectionDetailsModel> {

    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        bindModel(httpServletRequest);
        return super.index();

    }

}
