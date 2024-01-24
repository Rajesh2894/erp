package com.abm.mainet.property.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.property.ui.model.PublicNoticeModel;

@Controller
@RequestMapping("/PublicNotice.html")
public class PublicNoticeController extends AbstractFormController<PublicNoticeModel> {

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(HttpServletRequest request) throws Exception {
        this.sessionCleanup(request);
        getModel().bind(request);
        return defaultResult();

    }
}