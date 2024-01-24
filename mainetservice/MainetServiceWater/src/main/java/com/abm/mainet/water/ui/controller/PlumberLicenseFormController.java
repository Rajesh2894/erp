package com.abm.mainet.water.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.water.ui.model.PlumberLicenseScrutinyModel;

/**
 * @author Arun.Chavda
 *
 */
@Controller
@RequestMapping("/PlumberLicenseForm.html")
public class PlumberLicenseFormController extends AbstractFormController<PlumberLicenseScrutinyModel> {

    private static Logger log = Logger.getLogger(PlumberLicenseFormController.class);

    @Override
    @RequestMapping(params = "saveform", method = RequestMethod.POST)
    public ModelAndView saveform(final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
        final PlumberLicenseScrutinyModel model = getModel();
        this.getModel().setCommonHelpDocs("PlumberLicenseForm.html");
        try {
            if (model.saveForm()) {
                return jsonResult(JsonViewObject.successResult(getApplicationSession().getMessage("water.plumberUpdate")));
            }
        } catch (final Exception ex) {
            log.error("Error Occured During Saving Plumber License Data", ex);
            return jsonResult(JsonViewObject.failureResult(ex));
        }
        return defaultMyResult();
    }
}
