package com.abm.mainet.cfc.checklist.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.checklist.ui.model.ChecklistSearchModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractSearchFormController;

@Controller
@RequestMapping(MainetConstants.WINDOWS_SLASH + MainetConstants.FORM_URL.CHECKLIST_SEARCH)
public class ChecklistSearchController extends AbstractSearchFormController<ChecklistSearchModel> {

    @RequestMapping(method = RequestMethod.GET, params = "clean")
    public ModelAndView clean(final HttpServletRequest httpServletRequest) {
        getModel().setApplicationId(null);
        getModel().setServiceId(null);
        getModel().setApplicantName(MainetConstants.BLANK);
        getModel().setAppStatus(MainetConstants.BLANK);
        getModel().setFromDate(null);
        getModel().setToDate(null);
        return index();
    }

}
