package com.abm.mainet.cms.ui.controller;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cms.ui.model.HelpDocModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.service.ICommonHelpDocsService;
import com.abm.mainet.common.ui.controller.AbstractEntryFormController;
import com.abm.mainet.common.ui.view.JsonViewObject;
import com.abm.mainet.common.util.LookUp;

@Controller
@RequestMapping("/HelpDoc.html")
public class HelpDocController extends AbstractEntryFormController<HelpDocModel> implements Serializable {

    private static final long serialVersionUID = 464706622071964829L;
    @Autowired
    private ICommonHelpDocsService iHelpDocService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        return super.index();
    }

    @Override
    @RequestMapping(params = "edit", method = RequestMethod.POST)
    public ModelAndView editForm(@RequestParam final long rowId, final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);

        if (getModel().doAuthorization(rowId)) {
            getModel().editForm(rowId);
        } else {
            getModel().addValidationError("Not authorized user!");
        }

        return new ModelAndView("HelpDocEdit", MainetConstants.FORM_NAME, getModel());

    }

    @Override
    @RequestMapping(params = "saveform", method = RequestMethod.POST)
    public ModelAndView saveform(final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);

        final HelpDocModel model = getModel();

        if (model.saveForm()) {
            return jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));
        }

        if (model.getModeOfType().equalsIgnoreCase(MainetConstants.MENU.A)) {
            return defaultMyResult();
        }
        return new ModelAndView("HelpDocEdit", MainetConstants.FORM_NAME, getModel());
    }

    @RequestMapping(params = "complete", method = RequestMethod.GET)
    public ModelAndView Resubmission(final HttpServletRequest httpServletRequest) {

        sessionCleanup(httpServletRequest);
        return new ModelAndView("CommonHelpDocs", MainetConstants.FORM_NAME, getModel());
    }

    @RequestMapping(params = "serviceList")
    public @ResponseBody List<LookUp> getServiceList(@RequestParam("deptcode") final String deptcode) {
        return iHelpDocService.getServiceList(deptcode);
    }
}
