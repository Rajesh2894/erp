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
import com.abm.mainet.dms.service.FileUploadServiceValidator;
import com.abm.mainet.property.ui.model.SelfAssesmentNewModel;

@Controller
@RequestMapping("/PropertyAssessmentType.html")
public class PropertyAssessmentTypeController extends AbstractFormController<SelfAssesmentNewModel> {
    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(HttpServletRequest request) {
        this.sessionCleanup(request);
        SelfAssesmentNewModel model = this.getModel();
        model.getProvisionalAssesmentMstDto().setAssesssmentCategory(MainetConstants.MENU.Y);
        return new ModelAndView("PropertyAssessmentType", MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(params = "accept", method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView search(HttpServletRequest request) {
        this.sessionCleanup(request);
        SelfAssesmentNewModel model = this.getModel();
        model.setMobdisabled(true);
        model.setOtpdisabled(true);
        FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
        return new ModelAndView("PropertyAssessmentTypeView", MainetConstants.FORM_NAME, this.getModel());
    }

    @RequestMapping(params = "showChangeInAssessment", method = RequestMethod.POST)
    public ModelAndView showChangeInAssessment(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            @RequestParam(value = "assessmentType") String assessmentType) {
        this.getModel().bind(httpServletRequest);
        SelfAssesmentNewModel model = this.getModel();
        getModel().setAssType(assessmentType);
        if ("NC".equals(assessmentType)) {
            model.setRedirectURL("NoChangeInAssessment.html");
        } else {
            model.setRedirectURL("ChangeInAssessmentForm.html");
        }
        // model.getProvisionalAssesmentMstDto().setAssesssmentCategory(assessmentType);
        return new ModelAndView("NoChangeInAssessmentSearch", MainetConstants.FORM_NAME, model);

    }

}