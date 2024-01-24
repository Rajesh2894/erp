package com.abm.mainet.property.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.property.ui.model.SpecialNoticeGenerationModel;

@Controller
@RequestMapping("/PropertyAssessmentList.html")
public class PropertyAssessmentListController extends AbstractFormController<SpecialNoticeGenerationModel> {

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(HttpServletRequest request) throws Exception {
        this.sessionCleanup(request);
        getModel().bind(request);
        SpecialNoticeGenerationModel model = this.getModel();
        model.getSpecialNotGenSearchDto().setSpecNotSearchType("SM");
        model.setCommonHelpDocs("PropertyAssessmentList.html");
        return new ModelAndView("PropertyAssessmentList", MainetConstants.FORM_NAME, model);
    }
}