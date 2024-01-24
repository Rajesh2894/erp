package com.abm.mainet.legal.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.legal.ui.model.ActAndRuleMasterModel;

@Controller
@RequestMapping("ActAndRuleMaster.html")
public class ActAndRuleMasterController extends AbstractFormController<ActAndRuleMasterModel> {

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);

        return defaultResult();
    }

    @RequestMapping(method = { RequestMethod.POST }, params = MainetConstants.Legal.ADD_ACTANDRULE_MASTER)
    public ModelAndView addActAndRuleMaster(final HttpServletRequest httpServletRequest) {

        return new ModelAndView(MainetConstants.Legal.ACTANDRULE_MASTER_FORM, MainetConstants.FORM_NAME, this.getModel());
    }

}
