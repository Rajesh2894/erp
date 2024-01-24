package com.abm.mainet.cms.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cms.ui.model.AdminFAQModel;
import com.abm.mainet.cms.ui.validator.AdminFAQVlidator;
import com.abm.mainet.common.ui.controller.AbstractEntryFormController;
import com.abm.mainet.common.ui.view.JsonViewObject;

/**
 * @author rajendra.bhujbal
 *
 */
@Controller
@RequestMapping("/AdminFAQ.html")
public class AdminFAQController extends AbstractEntryFormController<AdminFAQModel> {

    @Override
    @RequestMapping(params = "save", method = RequestMethod.POST)
    public ModelAndView saveForm(
            final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
        final AdminFAQModel model = getModel();
        model.validateBean(model.getEntity(), AdminFAQVlidator.class);

        if (!model.hasValidationErrors()) {

            if (model.saveOrUpdateForm()) {
                return jsonResult(JsonViewObject
                        .successResult(model
                                .getSuccessMessage()));
            }

        } else {
            return defaultResult();
        }

        httpServletRequest.getRequestURI();
        return defaultResult();
    }

}
