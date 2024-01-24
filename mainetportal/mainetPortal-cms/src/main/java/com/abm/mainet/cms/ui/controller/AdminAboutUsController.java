package com.abm.mainet.cms.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.abm.mainet.cms.ui.model.AdminAboutUsModel;
import com.abm.mainet.cms.ui.validator.AdminAboutUsFormValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.ui.view.JsonViewObject;

@Controller
@RequestMapping("/AdminAboutUs.html")
public class AdminAboutUsController extends AbstractFormController<AdminAboutUsModel> {

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final HttpServletRequest request) {
        sessionCleanup(request);
        getModel().setModel();
        getModel().setCommonHelpDocs("AdminAboutUs.html");
        return new ModelAndView(getViewName(), MainetConstants.FORM_NAME, getModel());
    }

    @RequestMapping(method = RequestMethod.POST, params = "save")
    public ModelAndView saveAboutUS(final HttpServletRequest request, final RedirectAttributes redirectAttributes) {

        final BindingResult bindingResult = bindModel(request);
        final AdminAboutUsModel model = getModel();
        model.validateBean(model.getAdminAboutUs(), AdminAboutUsFormValidator.class);

        if (!bindingResult.hasErrors()) {
            if (model.saveAboutUs()) {
                return jsonResult(JsonViewObject.successResult(model
                        .getSuccessMessage()));
            } else {
                bindingResult.addError(new ObjectError(MainetConstants.BLANK,
                        getApplicationSession().getMessage("eip.admin.aboutUs.notSavedMsg")));
            }
        }

        return defaultMyResult();
    }

    @RequestMapping(method = RequestMethod.POST, params = "savecheker")
    public ModelAndView saveAboutUScheker(final HttpServletRequest request, final RedirectAttributes redirectAttributes) {

        final BindingResult bindingResult = bindModel(request);
        final AdminAboutUsModel model = getModel();
        model.validateBean(model.getAdminAboutUs(), AdminAboutUsFormValidator.class);

        if (!bindingResult.hasErrors()) {
            if (model.saveAboutUs()) {
                sessionCleanup(request);
                getModel().setModel();
                final ModelAndView modelAndView = new ModelAndView("redirect:/AdminAboutUs.html?AdminFaqCheker");
                redirectAttributes.addFlashAttribute("ABOUTUS_MSG",
                		model.getSuccessMessage());
                       // getApplicationSession().getMessage("eip.admin.aboutUs.savedMsg"));
                return modelAndView;
            } else {
                bindingResult.addError(new ObjectError(MainetConstants.BLANK,
                        getApplicationSession().getMessage("eip.admin.aboutUs.notSavedMsg")));
            }
        }

        return defaultResult();
    }
}
