package com.abm.mainet.buildingplan.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.buildingplan.service.INewLicenseFormService;
import com.abm.mainet.buildingplan.service.ISiteAffecService;
import com.abm.mainet.buildingplan.ui.model.LandDetailsModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;

@Controller
@RequestMapping("LandDetails.html")
public class LandDetailsController extends AbstractFormController<LandDetailsModel> {
	
	@Autowired
	ISiteAffecService siteAffecService;
	
	@Autowired
	INewLicenseFormService newLicenseFormService;

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		final Long appId = Long.valueOf(httpServletRequest.getParameter("applId"));
		final Long lableId = Long.valueOf(httpServletRequest.getParameter("labelId"));
		final Long level = Long.valueOf(httpServletRequest.getParameter("level"));
		final Long gmId = Long.valueOf(httpServletRequest.getParameter("gmId"));
		final Long taskId = Long.valueOf(httpServletRequest.getParameter("taskId"));
		this.getModel().setTaskId(taskId);
		this.getModel().setApplicationId(appId);
		this.getModel().setLevel(level);
		this.getModel().setSlLabelId(lableId);
		this.getModel().setGmId(gmId);
		this.getModel().setFieldFlag("NF");
		this.getModel().setScrnFlag("P");
		this.getModel().setLandDetailsMap(siteAffecService.getLandDetailsByAppId(appId, UserSession.getCurrent().getOrganisation().getOrgid()));
		
		return super.index();	
	}
	
	@RequestMapping(method = RequestMethod.POST, params = "LandFieldDetails")
	public ModelAndView landFieldDetails(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		final Long appId = Long.valueOf(httpServletRequest.getParameter("applId"));
		final Long lableId = Long.valueOf(httpServletRequest.getParameter("labelId"));
		final Long level = Long.valueOf(httpServletRequest.getParameter("level"));
		final Long gmId = Long.valueOf(httpServletRequest.getParameter("gmId"));
		final Long taskId = Long.valueOf(httpServletRequest.getParameter("taskId"));
		this.getModel().setTaskId(taskId);
		this.getModel().setApplicationId(appId);
		this.getModel().setLevel(level);
		this.getModel().setSlLabelId(lableId);
		this.getModel().setGmId(gmId);
		this.getModel().setFieldFlag("F");
		this.getModel().setScrnFlag("P");
		this.getModel().setLandDetailsMap(siteAffecService.getLandDetailsByAppId(appId, UserSession.getCurrent().getOrganisation().getOrgid()));
		return super.index();	
	}
	
	@RequestMapping(method = RequestMethod.POST, params = "LandFieldDetailsView")
	public ModelAndView landFieldDetailsView(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		final Long appId = Long.valueOf(httpServletRequest.getParameter("applId"));
		final Long lableId = Long.valueOf(httpServletRequest.getParameter("labelId"));
		final Long level = Long.valueOf(httpServletRequest.getParameter("level"));
		final Long gmId = Long.valueOf(httpServletRequest.getParameter("gmId"));
		final Long taskId = Long.valueOf(httpServletRequest.getParameter("taskId"));
		this.getModel().setTaskId(taskId);
		this.getModel().setApplicationId(appId);
		this.getModel().setLevel(level);
		this.getModel().setSlLabelId(lableId);
		this.getModel().setGmId(gmId);
		this.getModel().setFieldFlag("VF");
		this.getModel().setScrnFlag("P");
		this.getModel().setLicenseApplicationMasterDTO(newLicenseFormService.findByApplicationNoAndOrgId(appId, UserSession.getCurrent().getOrganisation().getOrgid()));
		return new ModelAndView("LandFieldDetailsView", MainetConstants.FORM_NAME, this.getModel());
	}

	@RequestMapping(method = RequestMethod.POST, params = "saveLicenseDetails")
	public ModelAndView saveLicenseData(final HttpServletRequest httpServletRequest) {
		bindModel(httpServletRequest);
		final LandDetailsModel model = getModel();
		if (model.saveForm()) {
			return jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));
		} else {
			return defaultMyResult();
		}

	}
	
	@RequestMapping(method = RequestMethod.POST, params = "FieldLevelLandDetailsView")
	public ModelAndView fieldLevelLandDetailsView(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		final Long appId = Long.valueOf(httpServletRequest.getParameter("applId"));
		final Long lableId = Long.valueOf(httpServletRequest.getParameter("labelId"));
		final Long level = Long.valueOf(httpServletRequest.getParameter("level"));
		final Long gmId = Long.valueOf(httpServletRequest.getParameter("gmId"));
		final Long taskId = Long.valueOf(httpServletRequest.getParameter("taskId"));
		this.getModel().setTaskId(taskId);
		this.getModel().setApplicationId(appId);
		this.getModel().setLevel(level);
		this.getModel().setSlLabelId(lableId);
		this.getModel().setGmId(gmId);
		this.getModel().setFieldFlag("LF");
		this.getModel().setScrnFlag("F");
		this.getModel().setLicenseApplicationMasterDTO(newLicenseFormService.findByApplicationNoAndOrgId(appId, UserSession.getCurrent().getOrganisation().getOrgid()));
		return new ModelAndView("LandFieldDetailsView", MainetConstants.FORM_NAME, this.getModel());
	}

}
