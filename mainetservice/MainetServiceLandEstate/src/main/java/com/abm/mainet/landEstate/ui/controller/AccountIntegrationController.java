package com.abm.mainet.landEstate.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.landEstate.ui.model.AccountIntegrationModel;

@Controller
@RequestMapping("AccountIntegration.html")
public class AccountIntegrationController extends AbstractFormController<AccountIntegrationModel> {

    @RequestMapping(params = "showDetails", method = RequestMethod.POST)
    public ModelAndView executeChangeOfOwner(final HttpServletRequest request,
            @RequestParam(value = "actualTaskId", required = false) final Long actualTaskId,
            @RequestParam("taskId") final Long taskId,
            @RequestParam("appNo") final Long applicationId) {

        sessionCleanup(request);
        getModel().bind(request);
        try {
            // Account Integration code set here
            System.out.println("data is");
        } catch (final Exception ex) {
            return defaultExceptionFormView();
        }

        return new ModelAndView("TEST_FORM", MainetConstants.CommonConstants.COMMAND, getModel());

    }

}
