package com.abm.mainet.property.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.property.ui.model.PropertySelfAssessmentModel;


@Controller
@RequestMapping("/propertyTaxDashBoardController.html")
public class PropertySelfAssessmentController extends AbstractFormController<PropertySelfAssessmentModel> {

	

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(HttpServletRequest request) {
		this.sessionCleanup(request);
		return new ModelAndView("ServiceDashboardAuthenticationForm", "command", this.getModel());
	}

	@ResponseBody
	@RequestMapping(params = "SelfAssesment", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView formForCreate(HttpServletRequest request) {

		return new ModelAndView("SelfAssessmenTermsForm", "command", this.getModel());

	}

	@ResponseBody
	@RequestMapping(params = "ChangeAssesment", method = RequestMethod.GET)
	public ModelAndView formChangeAssesment(HttpServletRequest request) {

		return new ModelAndView("ChangeAssessmentForm", MainetConstants.FORM_NAME, this.getModel());

	}

	@ResponseBody
	@RequestMapping(params = "NoChangeAssessment", method = RequestMethod.GET)
	public ModelAndView formNoChangeAssessment(HttpServletRequest request) {

		return new ModelAndView("NoChangeAssessmentForm", MainetConstants.FORM_NAME, this.getModel());

	}

	@ResponseBody
	@RequestMapping(params = "ChangeAssesmentOTP", method = RequestMethod.GET)
	public ModelAndView formChangeAssesmentOtp(HttpServletRequest request) {

		return new ModelAndView("ChangeAssesmentOTPForm", MainetConstants.FORM_NAME, this.getModel());

	}

	@ResponseBody
	@RequestMapping(params = "NoChangeAssesmentOTP", method = RequestMethod.GET)
	public ModelAndView formNOChangeAssesmentOtp(HttpServletRequest request) {

		return new ModelAndView("NOChangeAssesmentOTPForm", MainetConstants.FORM_NAME, this.getModel());

	}


	@ResponseBody
	@RequestMapping(params = "SubmitChangeAsses", method = RequestMethod.GET)
	public ModelAndView SubmitChangeAssesForm(HttpServletRequest request) {

		return new ModelAndView("SubmitChangeAssesForm", MainetConstants.FORM_NAME, this.getModel());

	}

	
	@ResponseBody
	@RequestMapping(params = "NoSubmitChangeAsses", method = RequestMethod.GET)
	public ModelAndView SubmitNoChangeAssesForm(HttpServletRequest request) {

		return new ModelAndView("NoChangeSubmitChangeAssesForm", MainetConstants.FORM_NAME, this.getModel());

	}

}
