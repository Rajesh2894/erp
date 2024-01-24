package com.abm.mainet.property.ui.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.property.ui.model.SelfAssesmentNewModel;

@Controller
@RequestMapping("/PropertyAssessmentType.html")
public class PropertyAssessmentTypeController extends AbstractFormController<SelfAssesmentNewModel> {
    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(HttpServletRequest request) {
        this.sessionCleanup(request);
        SelfAssesmentNewModel model = this.getModel();

        getModel().setCommonHelpDocs("PropertyAssessmentType.html");

        return new ModelAndView("PropertyAssessmentType", MainetConstants.FORM_NAME, model);

    }

    @RequestMapping(params = "showChangeInAssessment", method = RequestMethod.POST)
    public ModelAndView showChangeInAssessment(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            @RequestParam(value = "assessmentType") String assessmentType) {
        this.getModel().bind(httpServletRequest);
        SelfAssesmentNewModel model = this.getModel();
        model.getProvisionalAssesmentMstDto().setAssesssmentCategory(assessmentType);
        return new ModelAndView("ChangeInAssessmentSearchForm", MainetConstants.FORM_NAME, model);

    }

    @RequestMapping(params = "showNoChangeInAssessment", method = RequestMethod.POST)
    public ModelAndView showNoChangeInAssessment(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            @RequestParam(value = "assessmentType") String assessmentType) {
        this.getModel().bind(httpServletRequest);
        SelfAssesmentNewModel model = this.getModel();
        model.getProvisionalAssesmentMstDto().setAssesssmentCategory(assessmentType);
        return new ModelAndView("NoChangeInAssessmentSearchForm", MainetConstants.FORM_NAME, model);

    }

    @RequestMapping(params = "backToMainPage", method = RequestMethod.POST)
    public ModelAndView back(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        this.sessionCleanup(httpServletRequest);
        getModel().bind(httpServletRequest);
        SelfAssesmentNewModel model = this.getModel();
        return new ModelAndView("BackToPropertyAssessmentType", MainetConstants.FORM_NAME, model);
    }

}