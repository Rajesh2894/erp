package com.abm.mainet.buildingplan.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.buildingplan.service.ISiteAffecService;
import com.abm.mainet.buildingplan.ui.model.LicenseGrantedModel;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.ui.controller.AbstractFormController;

@Controller
@RequestMapping("LicenseGranted.html")
public class LicenseGrantedController extends AbstractFormController<LicenseGrantedModel> {

	@Autowired
	ISiteAffecService siteAffecService;

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
		this.getModel().setListLicenseDto(siteAffecService.getLicenseDetailsByApplicationId(appId));
		return super.index();
	}

	@RequestMapping(method = RequestMethod.POST, params = "saveLicenseData")
	public ModelAndView saveLicenseData(final HttpServletRequest httpServletRequest) {
		bindModel(httpServletRequest);
		final LicenseGrantedModel model = getModel();
		if (model.saveForm()) {
			return jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));
		} else {
			return defaultMyResult();
		}

	}

}
